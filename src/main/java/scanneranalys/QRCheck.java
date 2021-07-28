package scanneranalys;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import psb.com.mbanking.qr_scanner.qr_scanner.Bucket;

import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;

/**
 * <pre>
 * Немного информации о том, как устроены платежки.
 * Строка в QR-коде имеет следующий вид: STANDARD|KEY_1=VALUE_1|KEY_2=VALUE_2|...|KEY_N=VALUE_N|.
 * Причем ключ строго латиницей, а вот значение может быть и кириллицей, может и числами
 * Стандартов несколько, {@link #VERSION_11}, {@link #VERSION_13}, но префикс у них общий {@link #VERSION_1}.
 * </pre>
 * <pre>
 * Исключениям являются {@link #isNspkQr(String) NSPK}
 * </pre>
 * <pre>
 * По кодировкам используется следующии:
 * Для стандарта {@link #VERSION_11} - {@link #CP1252}
 * Для стандарта {@link #VERSION_13} - {@link #KOI8_R}
 * Иначе {@link #CP1251}
 * </pre>
 * <pre>
 * Но мы к сожалению живем не в идеальном мире, и вполне возможно,
 * что в одной строке первая пара будет в одной кодировке, а вторая во второй.
 * Поэтому подход следующий:
 * 1. Делим строчку на пары ключ-значение и работаем с каждой отдельной
 * 2. Применяем рекомендуемую кодировку и проверяем значение на валидность {@link #isStringGood(String)}
 * 3. Если строка не валидна - перебираем кодировки {@link #tryFix(String, String, String)}
 * 4. Если нам повезло и у нас есть строка от второго считывателя QR-кодов - пробуем еще и ее
 * </pre>
 * <pre>
 * Результатом всего этого действия новый json с теми же ключами и исправленными значениями
 * </pre>
 * <pre>
 * Официальная документация http://docs.cntd.ru/document/1200110981
 * </pre>
 */
public class QRCheck {

    private QRCheck() {
        // Приватный конструктор - класс имеет только статические методы
    }

    private static final String TAG = "qrChecker";
    private static final String VERSION_1 = "ST0001";
    private static final String VERSION_11 = "ST00011";
    private static final String VERSION_13 = "ST00013";

    // region Кодировки, которые нашли в QR-кодах
    private static final String CP1251 = "CP1251";
    private static final String CP1252 = "CP1252";
    private static final String KOI8_R = "KOI8_R";
    private static final String SHIFT_JIS = "SHIFT_JIS";

    private static final String[] ENCODINGS = new String[] {
            CP1251, CP1252, KOI8_R, SHIFT_JIS
    };
    // endregion

    /**
     * Рег. выражение, описывающее валидный текст.
     * <p>
     * Ограничиваемся латиницей, кириллицей и цифрами. А так же некоторыми небуквенными символами. В идеале занести список запрещенных символов в
     * него, но пока можно оставить и так
     * <p>
     * В идеале заменить на это "([^\\p{L}]|[a-zA-Zа-яА-Я])+", но не в рамках этого MR.
     */
    public static final String GOOD_REGEX = "[a-zA-Zа-яА-Я0-9\\W]+";

    /**
     * Список запрещенных символов. Если в значении есть что-то из них - значение считаетя не валидным
     */
    private static final String[] BAD_CHARS = new String[] {
            "\u001A", "\u007F", "\u00B9"
    };

    private static Gson gson = new Gson();

    /**
     * Принимаем строку из QR Reader'а и пытаемся её преобразовать в удобный для бэка формат
     *
     * @param barcodeData - строчка из QR кода формата STANDARD|KEY_1=VALUE_1|KEY_N=VALUE_N
     *
     * @return Возвращает json, представляющий собой массив {@link Bucket}.
     * <p>
     * {@code [{"key": "KEY_1", "value": "VALUE_1"}, {"key": "KEY_2", "value": "VALUE_2"}}
     * <p>
     * Либо же возвращаем {@code null}, если строка не является стандартом QR-кода для платежек
     *
     * @see Bucket
     */
    @Nullable
    public static String recognize(@NotNull String barcodeData) {
        return recognize(barcodeData, "");
    }

    /**
     * Принимаем строку из QR Reader'а и пытаемся её преобразовать в удобный для бэка формат.
     *
     * Если мы не смогли получить значение для какого-то ключа из {@code barcodeData}, пытаемся это сделать из {@code barcodeData2}
     *
     * @param barcodeData  - строчка из QR кода формата STANDARD|KEY_1=VALUE_1|KEY_N=VALUE_N
     * @param barcodeData2 - другая строчка из QR кода формата STANDARD|KEY_1=VALUE_1|KEY_N=VALUE_N
     *
     * @return Возвращает json, представляющий собой массив {@link Bucket}.
     * <p>
     * {@code [{"key": "KEY_1", "value": "VALUE_1"}, {"key": "KEY_2", "value": "VALUE_2"}}
     * <p>
     * Либо же возвращаем {@code null}, если строка не является стандартом QR-кода для платежек
     *
     * @see Bucket
     */
    @Nullable
    public static String recognize(@NotNull String barcodeData, @NotNull String barcodeData2) {
        if (barcodeData.isEmpty()) {
            barcodeData = barcodeData2;
            barcodeData2 = "";
        }
        if (barcodeData.isEmpty()) return null;
        if (isNspkQr(barcodeData)) return SbpUtil.recognize(barcodeData);
        if (!barcodeData.startsWith("ST")) return null;

        String preferredEncoding = CP1251;
        if (barcodeData.startsWith(VERSION_11)) {
            preferredEncoding = CP1252;
        }
        if (barcodeData.startsWith(VERSION_13)) {
            preferredEncoding = KOI8_R;
        }

        HashMap<String, Bucket> paramMap = new HashMap<>();
        String[] params = barcodeData.split("\\|");
        String[] params2 = barcodeData2.split("\\|");
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            String param2 = "";
            if (i < params2.length) {
                param2 = params2[i];
            }

            String[] parameter = param.split("=");
            String[] parameter2 = param2.split("=");

            if (parameter.length != 2) continue;
            String key = parameter[0];
            String value = parameter[1];
            String value2 = parameter2.length == 2 ? parameter2[1] : null;

            value = tryFix(value, value2, preferredEncoding);

            Bucket bucket = new Bucket(key, value);
            paramMap.put(key, bucket);
        }

        return gson.toJson(paramMap.values());
    }

    private static boolean isNspkQr(String qr) {
        return qr.toLowerCase().startsWith("\"https://qr.nspk.ru\"") || qr.toLowerCase().startsWith("\"http://qr.nspk.ru\"");
    }

    /**
     * 1. Берем исходную строку - проверяем ее на валидность
     * <p>
     * 2. Если строка не валидная - проверяем ее на валидность с рекомендуемой кодировкой
     * <p>
     * 3. Если строка все еще не валидная - перебираем кодировки и возможно проверяем параллельно альтернативное значени
     *
     * @param v                 Значение, которую требуется преобразовать
     * @param v2                Альтернативное значение из другого считывателя
     * @param preferredEncoding Предпочитаемая кодировка (выбирается стандартом)
     *
     * @return Строка, либо подходящая под валидные условия, либо в предпочитаемой кодировке
     */
    private static String tryFix(String v, @Nullable String v2, String preferredEncoding) {
        if (isStringGood(v)) {
            return v;
        }
        String value = fromEncoding(v, preferredEncoding);
        if (isStringGood(value)) {
            return value;
        }
        for (String encoding : ENCODINGS) {
            String newValue = fromEncoding(v, encoding);
            if (isStringGood(newValue)) {
                return newValue;
            }
            if (v2 == null) continue;
            newValue = fromEncoding(v2, encoding);
            if (isStringGood(newValue)) {
                return newValue;
            }
        }
        return value;
    }

    /**
     * @param value строка для проверки
     *
     * @return true, если строка проходит регулярное выражение и не содержит запрещенных символов
     */
    private static boolean isStringGood(String value) {
        if (!value.matches(GOOD_REGEX)) return false;
        for (String badChar : BAD_CHARS) {
            if (value.contains(badChar)) return false;
        }
        return true;
    }

    /**
     * @param data     строка для преобразования
     * @param encoding кодировка, из которой хотим преобразовать
     *
     * @return строка с кодировкой CP1251
     */
    private static String fromEncoding(String data, String encoding) {
        try {
            return new String(data.getBytes(Charset.forName(encoding)), Charset.forName("CP1251"));
        } catch(UnsupportedCharsetException e) {
            return data;
        }
    }
}

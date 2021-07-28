package scanneranalys;

import com.google.gson.Gson;
import psb.com.mbanking.qr_scanner.qr_scanner.Bucket;

import java.util.HashMap;

public class SbpUtil {
    private static final String KEY_NSPK = "qr_nspk";
    private static final String BANK_REGEX_SCHEMA = "^bank100000000010";
    private static final String HTTPS_SCHEMA = "https";

    private SbpUtil() {
    }

    public static String recognize(String data) {
        HashMap<String, Bucket> paramMap = new HashMap<>();

        //для правильной орбработки ссылок вида bank100000000010://qr.nspk.ru/AS100001ORTF4GAF80KPJ53K186D9A3G?type=01&bank=100000000007&crc=0C8A
        data = data.replaceAll(BANK_REGEX_SCHEMA, HTTPS_SCHEMA);

        Bucket bucket = new Bucket(KEY_NSPK, data);
        paramMap.put(bucket.getKey(), bucket);

        Gson gson = new Gson();
        return gson.toJson(paramMap.values());
    }
}

package utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static utils.UtilsKt.empty;

public class AstroUtils {

    public static final double TWILIGHT = 90.8333333333333;   // 90°50' (Сумерки)
    public static final double CIVIL_TWILIGHT = 96.0;         // 96°    (Гражданские сумерки)
    public static final double NAUTICAL_TWILIGHT = 102.0;     // 102°   (Навигационные сумерки)
    public static final double ASTRONOMICAL_TWILIGHT = 108.0; // 108°   (Астрономические сумерки)

    private static double Cos(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    private static double Acos(double rad) {
        return Math.toDegrees(Math.acos(rad));
    }

    private static double Sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    private static double Asin(double rad) {
        return Math.toDegrees(Math.asin(rad));
    }

    private static double Tan(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    private static double Atan(double rad) {
        return Math.toDegrees(Math.atan(rad));
    }

    private static double Atan2(double rad1, double rad2) {
        return Math.toDegrees(Math.atan2(rad1, rad2));
    }

    private static double Sqrt(double num) {
        return Math.sqrt(num);
    }

    private static double Exp(double num, double exp) {
        return Math.pow(num, exp);
    }

    private static double Abs(double num) {
        return Math.abs(num);
    }

    private static String getDateTime(long milliseconds, String format) {
        return getDateTime(new Date(milliseconds), format);
    }

    private static String getDateTime(Date date, String format) {
        try {
            format = (format == null || empty(format)) ? "dd MMM yyyy HH:mm:ss.sss" : format;
            return (new SimpleDateFormat(format, Locale.getDefault())).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int dayOfYear(long epochMillis) {
        String strDay = getDateTime(epochMillis, "D");
        if (strDay != null) {
            return Integer.parseInt(strDay);
        }
        return -1;
    }

    private static double correctAngle(double angle, int num) {
        while (angle >= num) {
            angle -= num;
        }
        return angle;
    }

    private static double round(double val, int scale) {
        return new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static String getHHmm(double Hh) {
        int sign = Hh > 0 ? 1 : -1;
        int H = (int) Math.abs(Hh);
        double y = (Math.abs(Hh) - H) * 60;
        int M = (int) y;
        return sign * H + ":" + pad(M);
    }

    private static String pad(int number) {
        if (number >= 10) {
            return String.valueOf(number);
        } else {
            return "0" + number;
        }
    }

    public static String getSunRise(long dateTime, double Lat, double Lon, double localZone) {
        return getSunsetRise(dateTime, Lat, Lon, true, localZone);
    }

    public static String getSunSet(long dateTime, double Lat, double Lon, double localZone) {
        return getSunsetRise(dateTime, Lat, Lon, false, localZone);
    }

    /**
     * Восход/заход солнца
     *
     * @param localZone
     * @param dateTime дата
     * @param Lat      широта
     * @param Lon      долгота
     * @return время захода/восхода
     */
    private static String getSunsetRise(long dateTime, double Lat, double Lon, boolean rise, double localZone) {
        // 1. first calculate the day of the year
        double N = dayOfYear(dateTime);
        //2. convert the longitude to hour value and calculate an approximate time
        double LngHour = Lon / 15;
        double t;
        t = rise ? N + ((6 - LngHour) / 24) : N + ((18 - LngHour) / 24);
        // 3. calculate the Sun's mean anomaly
        double M = (0.9856 * t) - 3.289;
        // 4. calculate the Sun's true longitude
        //    [Note throughout the arguments of the trig functions
        //    (sin, tan) are in degrees. It will likely be necessary to
        //    convert to radians. eg sin(170.626 deg) =sin(170.626*pi/180
        //    radians)=0.16287]
        double L = M + (1.916 * Sin(M)) + (0.020 * Sin(2 * M)) + 282.634;
        // NOTE: L potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
        L = correctAngle(L, 360);
        // 5a. calculate the Sun's right ascension
        double RA = (Atan(0.91764 * Tan(L)));
        // NOTE: RA potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
        RA = correctAngle(RA, 360);
        // 5b. right ascension value needs to be in the same quadrant as L
        double Lquadrant = round((L / 90), 0) * 90;
        double RAquadrant = round((RA / 90), 0) * 90;
        RA = RA + (Lquadrant - RAquadrant);
        // 5c. right ascension value needs to be converted into hours
        RA = RA / 15;
        // 6. calculate the Sun's declination
        double sinDec = 0.39782 * Sin(L);
        double cosDec = Cos(Asin(sinDec));
        // 7a. calculate the Sun's localZone hour angle
        double HCos = (Cos(AstroUtils.TWILIGHT) - (sinDec * Sin(Lat))) / (cosDec * Cos(Lat));
        if (HCos > 1) {
            return "+";
        }
        if ((HCos < -1)) {
            return "-";
        }
        // 7b. finish calculating H and convert into hourstwRise
        // if  rising time is desired:
        // H := 360 - RadToDeg( ArcCos( HCos ) );
        // if setting time is desired:
        // H := RadToDeg( ArcCos( HCos ) );
        double H = rise ? 360 - Acos(HCos) : Acos(HCos);
        H = H / 15;
        // 8. calculate localZone mean time of rising/setting
        double LocalT = H + RA - (0.06571 * t) - 6.622;
        // 9. adjust back to UTC
        double UT = LocalT - LngHour;
        // NOTE: UT potentially needs to be adjusted into the range [0,24) by adding/subtracting 24
        UT = correctAngle(UT, 24);
        double result = UT + localZone;
//        String UTC = getHHmm(UT);
        return getHHmm(result);
    }
}

package utils;

import java.util.Date;

public class CheckCardVerification {

    CheckCardVerification() { }

    // Проверяем станцию назначения на 11111 (станции назначения нет)
    public boolean isCheckExitStation(int code_station_exit){
        String  code_str = Integer.toString(code_station_exit);
        String numbers = code_str.substring(Math.max(0, code_str.length() - 5));
        return numbers.equals("11111");
    }

    /*
     * Проверяет время нахождения с моментом входа
     * */
    public boolean isCheckIntersectionTimeStation(long intersection_time){
        final Date date = new Date();
        final long l = System.currentTimeMillis();
        long diff_time =  date.getTime() - intersection_time ;
        return diff_time < 18000000;
    }
}

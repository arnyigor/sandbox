package utils;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import interfaces.Testable;
import rx.CompletableTests;

public class JavaRunnerTestable implements Testable {
    private static final SimpleDateFormat dd_MM_yyyy_HH_mm = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static final Calendar calendar = GregorianCalendar.getInstance();

    public static void runKotlin() {
        CompletableTests rxCompletableTests = new CompletableTests();
        rxCompletableTests.runTest(null);
    }

    @Override
    public void runTest(@Nullable String[] args) {
        System.out.println(getFullFileUrl());
    }

    public static void decode() {
        byte[] income = new byte[]{
                6, 0, 0, 0,
                96, 43, 103, 6,
                -22, -106, 0, 49,
                -113, -46, -117, 32
        };
        byte[] outCome = new byte[]{
                6, 0, 0, 0,
                96, 1, 19, 6,
                -22, -110, 5, -96,
                -57, -25, -91, 66
        };

        final int TypeTrain = BytesUtils.getValue(outCome, 0, 4);
        final int TypeTicket = BytesUtils.getValue(outCome, 4, 4);
        final int TypePassMark = BytesUtils.getValue(outCome, 8, 8);
        final int StationCodeDeparture = BytesUtils.getValue(outCome, 16, 20) + 2000000;
        final int StationCodeArrivingTrain = BytesUtils.getValue(outCome, 36, 20) + 2000000;
        final int time = BytesUtils.getValue(outCome, 56, 24);
        final int deviceNUm = BytesUtils.getValue(outCome, 80, 16);
        final int imoto = BytesUtils.getValue(outCome, 96, 32);
        System.out.println("end");
    }

    public synchronized static String getDateddMMyyyyHHmm(Date datetime) {
        String curStringDate = null;
        try {
            curStringDate = dd_MM_yyyy_HH_mm.format(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curStringDate;
    }

    public static String convertIntersectionTime() {
        int intersectionTime = 453270;
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        final String time = getDateddMMyyyyHHmm(calendar.getTime());
        System.out.println("current time:" + time);
        final long l = TimeUnit.MINUTES.toMillis(intersectionTime);
        final long l2 = intersectionTime * 60 * 1000L;
        System.out.println("convert time: l:" + l + " l2:" + l2);
        final long intersectionInMs = l;
        final long currentTimeInMs = calendar.getTimeInMillis() + intersectionInMs;
        calendar.setTimeInMillis(currentTimeInMs);
        return getDateddMMyyyyHHmm(calendar.getTime());
    }


    public static void Run() {
        final CheckCardVerification verification = new CheckCardVerification();
        checkPass(verification, 0, 0, Calendar.HOUR);
        checkPass(verification, 1, 2011111, Calendar.MINUTE);
        checkPass(verification, 3, 2011111, Calendar.HOUR);
        checkPass(verification, 5, 2011111, Calendar.HOUR);
        checkPass(verification, 301, 2011111, Calendar.MINUTE);
        checkPass(verification, 5, 2000275, Calendar.HOUR);
        checkPass(verification, 6, 2011111, Calendar.HOUR);
        checkPass(verification, 6, 2000275, Calendar.HOUR);
    }

    private static void checkPass(CheckCardVerification verification, int hours, int code_station_exit, int type) {
        final boolean checkExitStation = verification.isCheckExitStation(code_station_exit);
        final Calendar instance = GregorianCalendar.getInstance();
        instance.add(type, hours);
        final boolean checkIntersectionTimeStation = verification.isCheckIntersectionTimeStation(instance.getTimeInMillis());
        System.out.println("Check time:" + instance.getTime());
        System.out.println(" code_station_exit:" + code_station_exit);
        System.out.println("is valid:" + (checkExitStation && checkIntersectionTimeStation));
        System.out.println("");
    }

    private static String getFullFileUrl() {
        String fileRelativeUrl = "rules_vacation_credit_online.html";
        String url = "https://10.214.24.148/TSC.iBanking.MSB.Web.IISHost/api/";
        return url.substring(0, url.indexOf("api/")) + fileRelativeUrl;
    }


    public static void checkImito() {
        final ImotoChecker imotoChecker = new ImotoChecker();
        imotoChecker.check();
    }
}

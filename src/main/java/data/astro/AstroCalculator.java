package data.astro;

import java.util.Calendar;
import java.util.GregorianCalendar;

import utils.AstroUtils;

public class AstroCalculator {
    public String calc(int date, double lat, double lon, double localZone) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, date);
        String sunRise = AstroUtils.getSunRise(
                calendar.getTimeInMillis(), lat, lon, localZone
        );
        String sunSet = AstroUtils.getSunSet(
                calendar.getTimeInMillis(), lat, lon, localZone
        );
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("calendar:").append(calendar.getTime());
        stringBuilder.append("\nsunRise:").append(sunRise);
        stringBuilder.append("\nsunSet:").append(sunSet);
        return stringBuilder.toString();
    }
}

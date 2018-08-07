package by.training.hotel.dao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdCreator {

    private static final String SHORT_DATE_PATTERN = "ddMMyyyy";

    public static long createLongIdByTodayDate(int startIndex){
        SimpleDateFormat dateFormat = new SimpleDateFormat(SHORT_DATE_PATTERN);
        String todayDate = dateFormat.format(new Date());
        return convertToLong(startIndex, todayDate);
    }

    private static long convertToLong(int startIndex, String todayDate){
        return Long.valueOf(startIndex + todayDate);
    }
}

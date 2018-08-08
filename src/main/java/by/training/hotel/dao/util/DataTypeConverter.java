package by.training.hotel.dao.util;

import org.joda.time.LocalDate;

public class DataTypeConverter {

    public static LocalDate sqlToLocalDate(java.sql.Date sqlDate){
        long time = sqlDate.getTime();
        return new LocalDate(time);
    }

    public static java.sql.Date localDateToSqlDate(LocalDate localDate){
        long time = localDate.toDate().getTime();
        return new java.sql.Date(time);
    }

    public static Integer stringToIntConvert(String strIntValue){
        try {
            return Integer.valueOf(strIntValue);
        } catch (NumberFormatException e){
            return null;
        }
    }
}

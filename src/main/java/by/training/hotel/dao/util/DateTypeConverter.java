package by.training.hotel.dao.util;

import org.joda.time.LocalDate;

public class DateTypeConverter {

    public static java.sql.Date utilToSqlDate(java.util.Date date){
        long time = date.getTime();
        return new java.sql.Date(time);
    }

    public static java.util.Date sqlToUtilDate(java.sql.Date date){
        long time = date.getTime();
        return new java.util.Date(time);
    }

    public static LocalDate sqlToLocalDate(java.sql.Date sqlDate){
        long time = sqlDate.getTime();
        return new LocalDate(time);
    }

    public static java.sql.Date localDateToSqlDate(LocalDate localDate){
        long time = localDate.toDate().getTime();
        return new java.sql.Date(time);
    }
}

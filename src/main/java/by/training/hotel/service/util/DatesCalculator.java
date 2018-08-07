package by.training.hotel.service.util;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;

public class DatesCalculator {

    public static int nightsCountCalculate(Date checkIn, Date checkOut){
        return nightsCountCalculate(new LocalDate(checkIn), new LocalDate(checkOut));
    }

    public static int nightsCountCalculate(LocalDate checkIn, LocalDate checkOut){
        return Days.daysBetween(checkIn, checkOut).getDays();
    }
}

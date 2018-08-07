package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.BookingStatus;
import org.joda.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeBookingCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute(ParameterName.USER_ID);

        String bookingId = request.getParameter(ParameterName.BOOKING_ID);
        String checkIn = request.getParameter(ParameterName.CHECK_IN);
        String checkOut = request.getParameter(ParameterName.CHECK_OUT);
        String adultCount = request.getParameter(ParameterName.ADULTS);
        String childCount = request.getParameter(ParameterName.CHILDREN);
        String invoiceId = request.getParameter(ParameterName.INVOICE_ID);
        String bookingStatus = request.getParameter(ParameterName.BOOKING_STATUS);
        String [] roomNumbers = request.getParameterValues(ParameterName.SELECTED_ROOMS);

        Booking bookingForChange = new Booking();

        bookingForChange.setBookingId(Long.valueOf(bookingId));
        bookingForChange.setCheckInDate(new LocalDate(checkIn));
        bookingForChange.setCheckOutDate(new LocalDate(checkOut));
        bookingForChange.setAdultCount(Integer.valueOf(adultCount));
        bookingForChange.setChildCount(Integer.valueOf(childCount));
        bookingForChange.setUserId(userId);
        bookingForChange.setInvoiceId(Long.valueOf(invoiceId));
        bookingForChange.setBookingStatus(BookingStatus.valueOf(bookingStatus.toUpperCase()));

        session.setAttribute(ParameterName.BOOKING_FOR_CHANGE, bookingForChange);
        session.setAttribute(ParameterName.SELECTED_ROOMS, roomNumbers);

        response.sendRedirect(UrlPattern.PERFORM_CHANGE_BOOKING);


    }
}

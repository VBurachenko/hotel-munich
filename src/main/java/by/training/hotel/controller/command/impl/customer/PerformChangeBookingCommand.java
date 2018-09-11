package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Booking;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PerformChangeBookingCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Booking bookingForChange = (Booking) session.getAttribute(ParameterName.BOOKING_FOR_CHANGE);
        String [] roomNumbers = (String[]) session.getAttribute(ParameterName.SELECTED_ROOMS);

        boolean isChanged = false;

        try{
            BookingService bookingService = serviceFactory.getBookingService();
            RoomService roomService = serviceFactory.getRoomService();
            for(String roomNumber : roomNumbers){

                roomService.attachSelectedRoomToBooking(bookingForChange, roomNumber);
            }
            isChanged = bookingService.updateBooking(bookingForChange);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
        if (isChanged){
            session.setAttribute(ParameterName.BOOKING_ID, bookingForChange.getBookingId());
            response.sendRedirect(UrlPattern.ATTACH_INVOICE_TO_BOOKING);
        } else {
            System.out.println("notChanged");
        }
    }
}

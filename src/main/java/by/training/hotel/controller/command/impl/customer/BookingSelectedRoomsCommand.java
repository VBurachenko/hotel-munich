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

public class BookingSelectedRoomsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String [] selectedRoomNumbers = request.getParameterValues(ParameterName.SELECTED_ROOMS);

        HttpSession session = request.getSession();

        Booking orderInCart = (Booking) session.getAttribute(ParameterName.ORDER_IN_CART);

        BookingService bookingService = serviceFactory.getBookingService();
        RoomService roomService = serviceFactory.getRoomService();

        Long addedBookingId = null;

        try {
            if (orderInCart != null) {
                orderInCart.getRoomsSet().clear();
                for (String roomNumber : selectedRoomNumbers) {
                    roomService.attachSelectedRoomToBooking(orderInCart, roomNumber);
                }
                addedBookingId = bookingService.addNewBooking(orderInCart);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (addedBookingId != null){
            session.setAttribute(ParameterName.BOOKING_ID, addedBookingId);
            response.sendRedirect(request.getContextPath() + UrlPattern.ATTACH_INVOICE_TO_BOOKING);
        } else {
            request.getRequestDispatcher(PageEnum.BOOKING_IMPOSSIBLE.getPath()).forward(request, response);
        }
    }
}

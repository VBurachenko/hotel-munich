package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PutInCartCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String roomNumber = request.getParameter(ParameterName.ROOM_NUMBER);

        SearchUnitDTO searchUnit = (SearchUnitDTO) session.getAttribute(ParameterName.SEARCH_UNIT);
        Booking orderInCart = (Booking) session.getAttribute(ParameterName.ORDER_IN_CART);
        Integer userId = (Integer) session.getAttribute(ParameterName.USER_ID);

        BookingService bookingService = serviceFactory.getBookingService();
        RoomService roomService = serviceFactory.getRoomService();

        try {
            if (orderInCart == null) {
                orderInCart = bookingService.prepareBookingToOrder(searchUnit, userId);
            }
            roomService.attachSelectedRoomToBooking(orderInCart, roomNumber);
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
        session.setAttribute(ParameterName.ORDER_IN_CART, orderInCart);
        request.getRequestDispatcher(PageEnum.CART_CONTENT.getPath()).forward(request, response);
    }
}

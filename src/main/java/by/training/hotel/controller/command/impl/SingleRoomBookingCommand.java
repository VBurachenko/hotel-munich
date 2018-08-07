package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
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

public class SingleRoomBookingCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strRoomNumber = request.getParameter(ParameterName.ROOM_NUMBER);

        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute(ParameterName.USER_ID);
        SearchUnitDTO searchUnit = (SearchUnitDTO) session.getAttribute(ParameterName.SEARCH_UNIT);

        BookingService bookingService = serviceFactory.getBookingService();
        RoomService roomService = serviceFactory.getRoomService();

        Long addedBookingId = null;

        try {
            Booking preparedBooking = bookingService.prepareBookingToOrder(searchUnit, userId);
            roomService.attachSelectedRoomToBooking(preparedBooking, strRoomNumber);
            addedBookingId = bookingService.addNewBooking(preparedBooking);
        } catch (ServiceException e) {
            LOGGER.error(e);
            e.printStackTrace();
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (addedBookingId != null){
            session.setAttribute(ParameterName.BOOKING_ID, addedBookingId);
            response.sendRedirect(UrlPattern.ATTACH_INVOICE_TO_BOOKING);
        } else {
            request.getRequestDispatcher(PageEnum.BOOKING_IMPOSSIBLE.getPath()).forward(request, response);
        }
    }
}

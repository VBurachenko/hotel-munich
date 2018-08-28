package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProvideBookingForViewCommand extends Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strBookingId = request.getParameter(ParameterName.BOOKING_ID);

        BookingService bookingService = serviceFactory.getBookingService();

        CommonDTO<Booking> bookingForView = null;
        try {
            bookingForView = bookingService.getBookingForView(strBookingId);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }

        if (bookingForView != null){
            request.setAttribute(ParameterName.BOOKINGS_FOR_VIEW, bookingForView);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.NO_SUCH_BOOKING_MESSAGE_CODE);
        }
        request.getRequestDispatcher(PageEnum.BOOKING_LIST.getPath()).forward(request, response);
    }
}

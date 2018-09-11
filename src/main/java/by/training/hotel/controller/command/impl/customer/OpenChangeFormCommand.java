package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Booking;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenChangeFormCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strBookingId = request.getParameter(ParameterName.BOOKING_ID);

        Booking bookingForChange = null;

        try {
            if (strBookingId != null && !strBookingId.isEmpty()){
                bookingForChange = serviceFactory.getBookingService().getBookingByBookingId(strBookingId);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (bookingForChange != null){
            request.setAttribute(ParameterName.BOOKING_FOR_CHANGE, bookingForChange);
            request.getRequestDispatcher(PageEnum.CHANGE_BOOKING.getPath()).forward(request, response);
        } else {
            redirectBackWithError(request, response);
        }
    }

    private void redirectBackWithError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ParameterName.CHANGE_BOOKING_ERROR, ParameterName.CHANGE_OF_BOOKING_MESSAGE_CODE);
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + UrlPattern.OPEN_USER_OFFICE);
    }
}

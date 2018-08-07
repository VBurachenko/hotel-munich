package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CompleteBookingCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        session.removeAttribute(ParameterName.ORDER_IN_CART);
        session.removeAttribute(ParameterName.SEARCH_UNIT);
        session.removeAttribute(ParameterName.PAGE);
        session.removeAttribute(ParameterName.ROOMS_FOR_DISPLAY);
        session.removeAttribute(ParameterName.BOOKING_ID);

        request.getRequestDispatcher(PageEnum.SELECT_PAYMENT_TYPE.getPath()).forward(request, response);
    }
}

package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SuccessPaymentCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        session.removeAttribute(ParameterName.INVOICE_FOR_BOOKING);
        session.removeAttribute(ParameterName.BOOKING_IN_PROCESS);
        session.removeAttribute(ParameterName.INVOICE_FOR_PAYMENT);
        session.removeAttribute(ParameterName.BOOKING_FOR_CHANGE);
        session.removeAttribute(ParameterName.SELECTED_ROOMS);

        request.getRequestDispatcher(PageEnum.SUCCESS_PAYMENT.getPath()).forward(request, response);
    }
}

package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SuccessBookingProcessingCommand extends Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        session.removeAttribute(ParameterName.BOOKING_IN_PROCESS);
        session.removeAttribute(ParameterName.INVOICE_FOR_BOOKING);

        request.getRequestDispatcher(PageEnum.SUCCESS_BOOKING_PROCESSING.getPath()).forward(request, response);
    }
}

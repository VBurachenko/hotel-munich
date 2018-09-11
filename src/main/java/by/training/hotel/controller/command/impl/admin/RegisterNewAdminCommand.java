package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterNewAdminCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(ParameterName.EMAIL);

        UserService userService = serviceFactory.getUserService();

        boolean adminRegistered = false;

        try {
            adminRegistered = userService.registerNewAdmin(email);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (adminRegistered){
            response.sendRedirect(UrlPattern.SUCCESS_ADMIN_REGISTRATION);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.ADMIN_WAS_NOT_REGISTERED_MESSAGE_CODE);
            request.getRequestDispatcher(PageEnum.USER_LIST.getPath()).forward(request, response);
        }
    }
}

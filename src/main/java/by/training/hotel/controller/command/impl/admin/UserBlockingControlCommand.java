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

public class UserBlockingControlCommand extends Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strUserId = request.getParameter(ParameterName.USER_ID);
        String strBlockDown = request.getParameter(ParameterName.BLOCK_DOWN);

        boolean userBlockChanged = false;

        try {
            if (!strBlockDown.isEmpty() && !strUserId.isEmpty()){
                UserService userService = serviceFactory.getUserService();
                userBlockChanged = userService.changeBlockUser(strUserId, strBlockDown);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (userBlockChanged){
            request.setAttribute(ParameterName.BLOCKED_USER_ID, strUserId);
            request.setAttribute(ParameterName.BLOCK_DOWN, strBlockDown);
            request.getRequestDispatcher(UrlPattern.LIST_USERS_SHOW).forward(request, response);
        } else {
            request.setAttribute(ParameterName.USER_OPERATION_MESSAGE, ParameterName.CHANGE_USER_BLOCK_ERROR_CODE);
            request.getRequestDispatcher(UrlPattern.LIST_USERS_SHOW).forward(request, response);
        }
    }
}
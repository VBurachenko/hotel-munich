package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.User;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserBlockingControlCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strUserId = request.getParameter(ParameterName.USER_ID);

        User userForBlockChange = null;

        try {
            if (!strUserId.isEmpty()){
                UserService userService = serviceFactory.getUserService();
                userForBlockChange = userService.getUserById(Integer.valueOf(strUserId));
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (userForBlockChange != null){
            request.setAttribute(ParameterName.USER_FOR_BLOCK, userForBlockChange);
            request.getRequestDispatcher(PageEnum.USER_BLOCKING_CONTROL.getPath()).forward(request, response);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.CHANGE_USER_BLOCK_ERROR_CODE);
            request.getRequestDispatcher(UrlPattern.LIST_USERS_VIEW).forward(request, response);
        }
    }
}
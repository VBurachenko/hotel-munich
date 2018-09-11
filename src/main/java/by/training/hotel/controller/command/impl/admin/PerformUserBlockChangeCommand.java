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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PerformUserBlockChangeCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strUserId = request.getParameter(ParameterName.USER_ID);
        String strBlocking = request.getParameter(ParameterName.BLOCKING);

        UserService userService = serviceFactory.getUserService();

        boolean blockUserChanged = false;

        try {
            blockUserChanged = userService.changeBlockUser(strUserId, strBlocking);
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (blockUserChanged){
            HttpSession session = request.getSession();
            session.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.USER_BLOCK_CHANGED_CODE);
            response.sendRedirect(UrlPattern.LIST_USERS_VIEW);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.CHANGE_USER_BLOCK_ERROR_CODE);
            request.getRequestDispatcher(PageEnum.USER_LIST.getPath()).forward(request, response);
        }
    }
}

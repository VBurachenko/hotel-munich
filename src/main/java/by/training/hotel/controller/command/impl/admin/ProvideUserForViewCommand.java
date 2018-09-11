package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.User;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProvideUserForViewCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String searchArtifact = request.getParameter(ParameterName.SEARCH_USER_ARTIFACT);

        UserService userService = serviceFactory.getUserService();

        CommonDTO<User> userForView = null;

        try {
            userForView = userService.getUserByIdOrEmailOrTelephoneNumber(searchArtifact);
        } catch (ServiceException e){
            LOGGER.error(e);
        }

        if (userForView != null){
            request.setAttribute(ParameterName.USERS_FOR_VIEW, userForView);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.NO_SUCH_USER_MESSAGE_CODE);
        }
        request.getRequestDispatcher(PageEnum.USER_LIST.getPath()).forward(request, response);
    }
}

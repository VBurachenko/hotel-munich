package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.dao.util.PasswordConverter;
import by.training.hotel.entity.User;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PerformLoginCommand implements Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);

        UserService userService = serviceFactory.getUserService();

        String urlPattern;

        try {

            String encryptedPass = PasswordConverter.convertToHash(password);
            User incomingUser = userService.authorizeUser(email, encryptedPass);

            if (incomingUser != null){
                session.setAttribute(ParameterName.USER_ID, incomingUser.getUserId());
                session.setAttribute(ParameterName.ROLE, incomingUser.getRole().toString());
                session.setAttribute(ParameterName.DISCOUNT, incomingUser.getDiscount());
                session.removeAttribute(ParameterName.OPERATION_MESSAGE);
                urlPattern = UrlPattern.OPEN_USER_OFFICE;
            } else {
                request.getSession().setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.WRONG_PASSWORD_OR_EMAIL_CODE);
                urlPattern = UrlPattern.LOGIN;
            }
            response.sendRedirect(request.getContextPath() + urlPattern);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
    }
}

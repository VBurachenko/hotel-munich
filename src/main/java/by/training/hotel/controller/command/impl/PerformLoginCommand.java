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

public class PerformLoginCommand extends Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);

        UserService userService = serviceFactory.getUserService();

        try {

            String encryptedPass = PasswordConverter.convertToHash(password);
            User incomingUser = userService.authorizeUser(email, encryptedPass);

            if (incomingUser != null){

                if (!incomingUser.getBlocked()){
                    HttpSession session = request.getSession();
                    session.setAttribute(ParameterName.USER_ID, incomingUser.getUserId());
                    session.setAttribute(ParameterName.ROLE, incomingUser.getRole().toString());
                    session.setAttribute(ParameterName.DISCOUNT, incomingUser.getDiscount());
                    response.sendRedirect(request.getContextPath() + UrlPattern.OPEN_USER_OFFICE);
                } else {
                    redirectWithBlock(request, response);
                }
            } else {
                redirectBackWithError(request, response);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
    }

    private void redirectBackWithError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ParameterName.LOGIN_ERROR, ParameterName.WRONG_PASSWORD_OR_EMAIL_CODE);
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + UrlPattern.LOGIN);
    }

    private void redirectWithBlock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ParameterName.LOGIN_ERROR, ParameterName.ACCOUNT_BLOCKED_CODE);
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + UrlPattern.LOGIN);
    }


}

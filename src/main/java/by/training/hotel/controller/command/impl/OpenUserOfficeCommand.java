package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.User;
import by.training.hotel.entity.UserRole;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OpenUserOfficeCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Integer currentUserId = (Integer) session.getAttribute(ParameterName.USER_ID);

        UserService userService = serviceFactory.getUserService();

        try {
            User currentUser = userService.getUserById(currentUserId);
            String urlPattern = null;
            if (currentUser != null){

                session.setAttribute(ParameterName.EMAIL, currentUser.getEmail());
                session.setAttribute(ParameterName.FIRST_NAME, currentUser.getName());
                session.setAttribute(ParameterName.LAST_NAME, currentUser.getSurname());
                session.setAttribute(ParameterName.BIRTHDAY, currentUser.getBirthday());
                session.setAttribute(ParameterName.TEL_NUMBER, currentUser.getTelNumber());
                session.setAttribute(ParameterName.GENDER_MALE, currentUser.getGenderMale());

                if (currentUser.getRole().equals(UserRole.CUSTOMER)){
                    urlPattern = UrlPattern.CUSTOMER_AUTHORIZED;
                } else if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getRole().equals(UserRole.MODER)){
                    urlPattern = UrlPattern.ADMIN_AUTHORIZED;
                }

            } else {
                urlPattern = UrlPattern.LOGIN;
            }
            response.sendRedirect(request.getContextPath() + urlPattern);

        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);

        }
    }
}

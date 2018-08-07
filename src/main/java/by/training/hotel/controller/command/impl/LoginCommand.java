package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(ParameterName.ROLE);
        String contextPath = request.getContextPath();

        if (role.equals(UserRole.GUEST.toString())) {
            request.getRequestDispatcher(PageEnum.LOGIN.getPath()).forward(request, response);
        } else {
            response.sendRedirect(contextPath + UrlPattern.OPEN_USER_OFFICE);
        }
    }
}

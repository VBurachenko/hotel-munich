package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public final class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute(ParameterName.ROLE, UserRole.GUEST.toString());
        request.getRequestDispatcher(PageEnum.HOME.getPath()).forward(request, response);
        session.invalidate();
    }
}

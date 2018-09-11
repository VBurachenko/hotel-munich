package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.training.hotel.controller.command.ParameterName.CURRENT_PAGE;
import static by.training.hotel.controller.command.ParameterName.LOCAL_LANG;
import static by.training.hotel.controller.command.ParameterName.SET_LANG;

public class LanguageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String languageName = request.getParameter(SET_LANG);
        String currentPage = request.getParameter(CURRENT_PAGE);

        String httpPort = ":\\p{Digit}+/";
        String [] url = currentPage.split(httpPort);

        HttpSession session = request.getSession();
        session.setAttribute(LOCAL_LANG, languageName);

        request.getRequestDispatcher(url[1]).forward(request, response);
    }
}

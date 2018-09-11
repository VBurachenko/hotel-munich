package by.training.hotel.controller;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class FrontController extends HttpServlet {

    private static final long serialVersionUID = -5997622293080547311L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CommandFactory factory = CommandFactory.getInstance();
        Command command = factory.getCommand(request);
        command.execute(request, response);

    }

}
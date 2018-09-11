package by.training.hotel.controller.command;

import by.training.hotel.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();

    Logger LOGGER = LogManager.getLogger();

    void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;
}

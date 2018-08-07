package by.training.hotel.controller.command;

import by.training.hotel.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {

    protected final static ServiceFactory serviceFactory = ServiceFactory.getInstance();

    protected static final Logger LOGGER = LogManager.getLogger();

    public abstract void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;
}

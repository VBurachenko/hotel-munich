package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.mapping.PageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomChangeSuccessCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(PageEnum.SUCCESS_ROOM_CHANGE.getPath()).forward(request, response);
    }
}

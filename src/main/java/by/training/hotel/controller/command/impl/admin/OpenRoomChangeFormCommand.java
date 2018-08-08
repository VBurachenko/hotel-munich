package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Room;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OpenRoomChangeFormCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String strRoomNumber = request.getParameter(ParameterName.ROOM_NUMBER);

        RoomService roomService = serviceFactory.getRoomService();

        Room roomForChange = null;

        try {
            roomForChange = roomService.getRoomByRoomNumber(strRoomNumber);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        String path;
        if (roomForChange != null){
            session.setAttribute(ParameterName.ROOM_FOR_CHANGE, roomForChange);
            path = PageEnum.CHANGE_ROOM_FORM.getPath();
        } else {
            request.setAttribute(ParameterName.ROOM_OPERATION_MESSAGE, ParameterName.ROOM_CHANGE_IMPOSSIBLE_MESSAGE_CODE);
            path = PageEnum.ROOM_LIST.getPath();
        }
        request.getRequestDispatcher(path).forward(request, response);
    }
}

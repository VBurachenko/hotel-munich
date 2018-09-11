package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProvideRoomForViewCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strRoomNumber = request.getParameter(ParameterName.ROOM_NUMBER);

        RoomService roomService = serviceFactory.getRoomService();

        CommonDTO<Room> roomForView = null;

        try {
            roomForView = roomService.getRoomForViewByRoomNumber(strRoomNumber);
        } catch (ServiceException e){
            LOGGER.error(e);
        }

        if (roomForView != null){
            request.setAttribute(ParameterName.ROOMS_FOR_VIEW, roomForView);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.NO_SUCH_ROOM_MESSAGE_CODE);
        }

        request.getRequestDispatcher(PageEnum.ROOM_LIST.getPath()).forward(request, response);
    }
}

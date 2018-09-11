package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomBlockingControlCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strRoomNumber = request.getParameter(ParameterName.ROOM_NUMBER);
        String strBlockDown = request.getParameter(ParameterName.BLOCK_DOWN);

        boolean roomAvailableStatusChanged = false;

        try{
            if (!strRoomNumber.isEmpty() && !strBlockDown.isEmpty()){
                RoomService roomService = serviceFactory.getRoomService();
                roomAvailableStatusChanged = roomService.changeRoomAvailableStatus(strRoomNumber, strBlockDown);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
        }

        if (roomAvailableStatusChanged){
            request.setAttribute(ParameterName.DISABLED_ROOM_NUMBER, strRoomNumber);
            request.setAttribute(ParameterName.BLOCK_DOWN, strBlockDown);

            request.getRequestDispatcher(UrlPattern.LIST_ROOMS_VIEW).forward(request, response);
        } else {
            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.CHANGE_ROOM_STATUS_ERROR_CODE);
        }

    }
}

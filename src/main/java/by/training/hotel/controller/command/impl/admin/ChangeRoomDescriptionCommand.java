package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeRoomDescriptionCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strRoomNumber = request.getParameter(ParameterName.ROOM_NUMBER);
        String strBerthCount = request.getParameter(ParameterName.BERTH_COUNT);
        String strComfortLevel = request.getParameter(ParameterName.COMFORT_LEVEL);
        String strPricePerNight = request.getParameter(ParameterName.PRICE_PER_NIGHT);
        String pictureLink = request.getParameter(ParameterName.PICTURE_LINK);
        String availableStatus = request.getParameter(ParameterName.AVAILABLE_STATUS);

        boolean roomUpdated = false;

        RoomService roomService = serviceFactory.getRoomService();

        try {
            roomUpdated = roomService.changeRoomDescription(strRoomNumber, strBerthCount, strComfortLevel,
                                                            strPricePerNight, pictureLink, availableStatus);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }

        System.out.println(roomUpdated);
        if (roomUpdated){
            response.sendRedirect(UrlPattern.ROOM_CHANGE_SUCCESS);
        } else {
            request.setAttribute(ParameterName.ROOM_OPERATION_MESSAGE, ParameterName.ROOM_CHANGE_IMPOSSIBLE_MESSAGE_CODE);
            request.getRequestDispatcher(PageEnum.ROOM_LIST.getPath()).forward(request, response);
        }

    }
}

package by.training.hotel.controller.command.impl;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DisplayRoomsCommand extends Command {

    private static final int ITEMS_PER_PAGE = 4;

    private final static int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        SearchUnitDTO searchUnit = (SearchUnitDTO)request.getAttribute(ParameterName.SEARCH_UNIT);
        String page = (String) request.getAttribute(ParameterName.PAGE);

        if (searchUnit == null){
            searchUnit = (SearchUnitDTO) session.getAttribute(ParameterName.SEARCH_UNIT);
        }

        int pageNumber;

        if (page != null && !page.isEmpty()){
            pageNumber = Integer.valueOf(page);
        } else {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        RoomService roomService = serviceFactory.getRoomService();
        CommonDTO<Room> roomsForDisplay = null;

        try {
            roomsForDisplay = roomService.getRoomsForDisplay(searchUnit, pageNumber, ITEMS_PER_PAGE);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (roomsForDisplay != null){

            session.setAttribute(ParameterName.ROOMS_FOR_DISPLAY, roomsForDisplay);
            session.setAttribute(ParameterName.PAGE, pageNumber);
            session.setAttribute(ParameterName.SEARCH_UNIT, searchUnit);

            request.getRequestDispatcher(PageEnum.FREE_ROOM_LIST.getPath()).forward(request, response);
        } else {
            response.sendRedirect(UrlPattern.SEARCH_ROOM);
        }
    }
}

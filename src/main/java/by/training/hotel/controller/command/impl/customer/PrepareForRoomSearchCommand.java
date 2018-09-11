package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import org.joda.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrepareForRoomSearchCommand implements Command {

    private final static int DEFAULT_ADULT_COUNT = 1;
    private final static int DEFAULT_CHILD_COUNT = 0;
    private final static int DEFAULT_COMFORT_LEVEL = 3;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String checkIn = request.getParameter(ParameterName.CHECK_IN);
        String checkOut = request.getParameter(ParameterName.CHECK_OUT);
        String adultCount = request.getParameter(ParameterName.ADULTS);
        String childCount = request.getParameter(ParameterName.CHILDREN);
        String comfortLevel = request.getParameter(ParameterName.COMFORT_LEVEL);
        String page = request.getParameter(ParameterName.PAGE);

        SearchUnitDTO searchUnit = new SearchUnitDTO();

        if ((checkIn != null && !checkIn.isEmpty()) && (checkOut != null && !checkOut.isEmpty())){
            searchUnit.setFrom(new LocalDate(checkIn));
            searchUnit.setTo(new LocalDate(checkOut));
        }

        if (adultCount != null && !adultCount.isEmpty()){
            searchUnit.setAdultCount(Integer.valueOf(adultCount));
        } else {
            searchUnit.setAdultCount(DEFAULT_ADULT_COUNT);
        }

        if (childCount != null && !childCount.isEmpty()){
            searchUnit.setChildCount(Integer.valueOf(childCount));
        } else {
            searchUnit.setChildCount(DEFAULT_CHILD_COUNT);
        }

        if (comfortLevel != null && !comfortLevel.isEmpty()){
            searchUnit.setComfortLevel(Integer.valueOf(comfortLevel));
        } else {
            searchUnit.setComfortLevel(DEFAULT_COMFORT_LEVEL);
        }

        request.setAttribute(ParameterName.SEARCH_UNIT, searchUnit);
        request.setAttribute(ParameterName.PAGE, page);

        request.getRequestDispatcher(request.getContextPath() + UrlPattern.DISPLAY_FREE_ROOMS).forward(request, response);
    }
}

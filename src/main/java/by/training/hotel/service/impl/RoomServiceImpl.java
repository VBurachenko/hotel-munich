package by.training.hotel.service.impl;

import by.training.hotel.dao.DAOFactory;
import by.training.hotel.dao.RoomDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.RoomService;
import by.training.hotel.service.exception.ServiceException;
import by.training.hotel.service.util.PageCountDeterminant;
import by.training.hotel.service.validation.BookingValidator;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final DAOFactory factory = DAOFactory.getInstance();

    private final RoomDAO<Integer, Room> roomDao = factory.getRoomDao();

    @Override
    public CommonDTO<Room> getRoomsForDisplay(SearchUnitDTO searchUnit, int pageNumber, int itemsPerPage) throws ServiceException {

        CommonDTO<Room> roomsForDisplay;

        int start = (pageNumber - 1) * itemsPerPage;

        try {
            if (searchUnit.getFrom() != null && searchUnit.getTo() != null){
                roomsForDisplay = getFreeRooms(searchUnit, start, itemsPerPage);
            } else {
                roomsForDisplay = getAllRooms(start, itemsPerPage);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return roomsForDisplay;
    }

    @Override
    public Room getRoomByRoomNumber(String strRoomNumber) throws ServiceException{

        Room currentRoom;

        try{
            currentRoom = roomDao.getById(Integer.valueOf(strRoomNumber));
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return currentRoom;
    }

    @Override
    public void attachSelectedRoomToBooking(Booking createdBooking, String roomNumber) throws ServiceException{
        if (!BookingValidator.validateCreatingBooking(createdBooking)){
            return;
        }
        try {
            Room selectedRoom = roomDao.getById(Integer.valueOf(roomNumber));
            createdBooking.getRoomsSet().add(selectedRoom);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private CommonDTO<Room> getFreeRooms(SearchUnitDTO searchUnit, int start,
                                         int itemsPerPage) throws DAOException{

        CommonDTO<Room> roomsForDisplay = new CommonDTO<>();

        List<Room> roomsList = roomDao.findFreeRooms(searchUnit, start, itemsPerPage);
        int roomsCount = roomDao.getCountOfFreeRooms(searchUnit);
        int pageCount = PageCountDeterminant.definePageCount(roomsCount, itemsPerPage);

        roomsForDisplay.setEntityList(roomsList);
        roomsForDisplay.setPagesCount(pageCount);

        return roomsForDisplay;
    }

    private CommonDTO<Room> getAllRooms(int start, int itemsPerPage) throws DAOException{

        CommonDTO<Room> roomsForDisplay = new CommonDTO<>();

        List<Room> roomsList = roomDao.getElementsList(start, itemsPerPage);
        int roomsCount = roomDao.getTotalCountOfRooms();
        int pageCount = PageCountDeterminant.definePageCount(roomsCount, itemsPerPage);

        roomsForDisplay.setEntityList(roomsList);
        roomsForDisplay.setPagesCount(pageCount);

        return roomsForDisplay;
    }



}

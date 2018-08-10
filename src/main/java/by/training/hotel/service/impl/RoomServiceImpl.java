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
import by.training.hotel.service.validation.CommonValidator;
import by.training.hotel.service.validation.RoomValidator;

import java.util.LinkedList;
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

    @Override
    public synchronized CommonDTO<Room> getRoomsForView(int pageNumber, int itemsPerPage) throws ServiceException{
        if (pageNumber <= 0){
            return null;
        }
        CommonDTO<Room> roomsForView = new CommonDTO<>();
        int start = (pageNumber - 1) * itemsPerPage;

        try {
            List<Room> roomsList = roomDao.getElementsList(start, itemsPerPage);
            int roomCount = roomDao.getTotalCountOfRooms();
            int pageCount = PageCountDeterminant.definePageCount(roomCount, itemsPerPage);

            roomsForView.setEntityList(roomsList);
            roomsForView.setPagesCount(pageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return roomsForView;
    }

    @Override
    public boolean changeRoomAvailableStatus(String strRoomNumber, String strBlockDown) throws ServiceException{
        boolean statusChanged = false;
        Integer roomNumber = Integer.valueOf(strRoomNumber);
        if (!CommonValidator.validateIntegerId(roomNumber)){
            return false;
        }
        Boolean blockDown = Boolean.valueOf(strBlockDown);
        try {
            Room roomInProcess = roomDao.getById(roomNumber);
            if (roomInProcess != null){
                roomInProcess.setAvailableStatus(blockDown);
                statusChanged = roomDao.update(roomInProcess);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return statusChanged;
    }

    @Override
    public CommonDTO<Room> getRoomForViewByRoomNumber(String strRoomNumber) throws ServiceException{
        CommonDTO<Room> roomForView = null;
        Integer roomNumber;

        try {
            roomNumber = Integer.valueOf(strRoomNumber);
        } catch (NumberFormatException e){
            return null;
        }

        if (!CommonValidator.validateIntegerId(roomNumber)){
            return null;
        }

        try {
            Room wantedRoom = roomDao.getById(roomNumber);
            if (wantedRoom != null){
                roomForView = new CommonDTO<>();
                List<Room> roomList = new LinkedList<>();
                roomList.add(wantedRoom);

                roomForView.setEntityList(roomList);
                roomForView.setPagesCount(1);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return roomForView;
    }

    @Override
    public Integer addNewRoom(String strRoomNumber,
                              String strBerthCount,
                              String strComfortLevel,
                              String strPricePerNight,
                              String pictureLink) throws ServiceException{

        Integer justAddedRoomNumber;

        Integer roomNumber;
        Integer berthCount;
        Integer comfortLevel;
        Double pricePerNight;

        try {
            roomNumber = Integer.valueOf(strRoomNumber);
            berthCount = Integer.valueOf(strBerthCount);
            comfortLevel = Integer.valueOf(strComfortLevel);
            pricePerNight = Double.valueOf(strPricePerNight);
        } catch (NumberFormatException e){
            return null;
        }

        Room addingRoom = new Room();
        addingRoom.setRoomNumber(roomNumber);
        addingRoom.setBerthCount(berthCount);
        addingRoom.setComfortLevel(comfortLevel);
        addingRoom.setPricePerNight(pricePerNight);
        addingRoom.setPictureLink(pictureLink);

        if (!RoomValidator.validateAddingRoom(addingRoom)){
            return null;
        }

        try {
            justAddedRoomNumber = roomDao.add(addingRoom);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return justAddedRoomNumber;
    }

    @Override
    public boolean changeRoomDescription(String strRoomNumber, String strBerthCount, String strComfortLevel,
                                         String strPricePerNight, String pictureLink, String strAvailableStatus) throws ServiceException{
        boolean roomChanged = false;

        Integer roomNumber;
        Integer berthCount;
        Integer comfortLevel;
        Double pricePerNight;
        Boolean availableStatus;

        try {
            roomNumber = Integer.valueOf(strRoomNumber);
            berthCount = Integer.valueOf(strBerthCount);
            comfortLevel = Integer.valueOf(strComfortLevel);
            pricePerNight = Double.valueOf(strPricePerNight);
            availableStatus = Boolean.valueOf(strAvailableStatus);
        } catch (NumberFormatException e){
            return false;
        }

        try {
            Room roomForChange = roomDao.getById(roomNumber);
            if (roomForChange != null){
                roomForChange.setBerthCount(berthCount);
                roomForChange.setComfortLevel(comfortLevel);
                roomForChange.setPricePerNight(pricePerNight);
                roomForChange.setPictureLink(pictureLink);
                roomForChange.setAvailableStatus(availableStatus);

                roomChanged = roomDao.update(roomForChange);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return roomChanged;

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

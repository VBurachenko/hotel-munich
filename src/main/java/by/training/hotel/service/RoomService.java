package by.training.hotel.service;

import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.exception.ServiceException;

public interface RoomService {

    CommonDTO<Room> getRoomsForDisplay(SearchUnitDTO searchUnit, int pageNumber,
                                 int itemsPerPage) throws ServiceException;

    Room getRoomByRoomNumber(String strRoomNumber) throws ServiceException;

    void attachSelectedRoomToBooking(Booking createdBooking, String roomNumber) throws ServiceException;
}

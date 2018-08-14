package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;

import java.util.List;

public interface RoomDAO<R extends Room, N extends Number> extends EntityDAO<R, N> {

    List<R> findFreeRooms(SearchUnitDTO searchUnit, int start, int offset) throws DAOException;

    int getCountOfFreeRooms(SearchUnitDTO searchUnit) throws DAOException;

}

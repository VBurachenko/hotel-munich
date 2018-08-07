package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;

import java.util.List;

public interface RoomDAO<K extends Number, T extends Entity> extends EntityDAO<K, T> {

    List<T> findFreeRooms(SearchUnitDTO searchUnit, int start, int offset) throws DAOException;

    int getCountOfFreeRooms(SearchUnitDTO searchUnit) throws DAOException;

    int getTotalCountOfRooms() throws DAOException;
}

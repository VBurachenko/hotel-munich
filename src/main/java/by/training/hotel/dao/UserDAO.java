package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

public interface UserDAO<K extends Number, T extends Entity> extends EntityDAO<K, T> {

    T getUserByEmail(String email) throws DAOException;

    int getTotalCountOfUsers() throws DAOException;
}

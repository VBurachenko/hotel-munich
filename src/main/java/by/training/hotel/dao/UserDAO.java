package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;
import by.training.hotel.entity.User;

public interface UserDAO<K extends Number, T extends Entity> extends EntityDAO<K, T> {

    T getUserByEmail(String email) throws DAOException;

    int getTotalCountOfUsers() throws DAOException;

    User getUserByEmailOrTelNumber(String emailOrTelNumber) throws DAOException;
}

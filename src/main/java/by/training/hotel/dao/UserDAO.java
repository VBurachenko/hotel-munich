package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.User;

public interface UserDAO<E extends User, N extends Number> extends EntityDAO<E, N>{

    E getUserByEmail(String email) throws DAOException;

    E getUserByIdOrEmailOrTelNumber(String emailOrTelNumber) throws DAOException;
}

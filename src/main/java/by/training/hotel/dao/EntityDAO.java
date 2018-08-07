package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

import java.util.List;

public interface EntityDAO<K extends Number, T extends Entity> {

    K add(T element) throws DAOException;

    boolean update(T element) throws DAOException;

    boolean delete(K id) throws DAOException;

    T getById(K id) throws DAOException;

    List<T> getElementsList(int start, int offset) throws DAOException;

    List<T> getElementsList() throws DAOException;
}

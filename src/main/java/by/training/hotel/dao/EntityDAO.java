package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

import java.util.List;

public interface EntityDAO<E extends Entity, N extends Number> {

    N add(E element) throws DAOException;

    boolean delete(N id) throws DAOException;

    boolean update(E element) throws DAOException;

    E getById(N id) throws DAOException;

    List<E> getElementsList(int start, int offset) throws DAOException;

    List<E> getElementsList() throws DAOException;

    int getTotalCountOfElements() throws DAOException;
}

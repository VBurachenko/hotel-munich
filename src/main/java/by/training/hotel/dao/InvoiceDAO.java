package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

import java.util.Set;

public interface InvoiceDAO<K extends Number, T extends Entity> extends EntityDAO<K, T> {

    Set<T> getInvoicesSetByUserId(Integer userId) throws DAOException;

    void clearUnspecifiedInvoices(Integer userId) throws DAOException;

}

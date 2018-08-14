package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Invoice;

import java.util.Set;

public interface InvoiceDAO<I extends Invoice, N extends Number> extends EntityDAO<I, N> {

    Set<I> getInvoicesSetByUserId(Integer userId) throws DAOException;

    void clearUnspecifiedInvoices(Integer userId) throws DAOException;

}

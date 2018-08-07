package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

import java.util.Set;

public interface BookingDAO<K extends Number, T extends Entity> extends EntityDAO<K, T> {

    Set<T> getBookingsSetByUserId(Integer userId) throws DAOException;

    K getBookingIdByInvoiceId(Long invoiceId) throws DAOException;

    int getTotalCountOfBookings() throws DAOException;

    void clearIncompleteBookings(Integer userId) throws DAOException;
}

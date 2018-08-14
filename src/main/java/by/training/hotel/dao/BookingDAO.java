package by.training.hotel.dao;

import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Booking;

import java.util.Set;

public interface BookingDAO<E extends Booking, N extends Number> extends EntityDAO<E, N> {

    Set<E> getBookingsSetByUserId(Integer userId) throws DAOException;

    N getBookingIdByInvoiceId(Long invoiceId) throws DAOException;

    void clearIncompleteBookings(Integer userId) throws DAOException;
}

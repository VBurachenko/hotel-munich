package by.training.hotel.service;

import by.training.hotel.entity.Booking;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.exception.ServiceException;

import java.util.Set;

public interface BookingService {

    Long addNewBooking(Booking booking) throws ServiceException;

    Set<Booking> getBookingsSetForUser(Integer userId)throws ServiceException;

    boolean updateBooking(Booking booking) throws ServiceException;

    void clearIncompleteBookings(Integer userId) throws ServiceException;

    Booking prepareBookingToOrder(SearchUnitDTO searchUnit, int userId);

    Booking getBookingByBookingId(String strBookingId) throws ServiceException;

    CommonDTO<Booking> getBookingsListForDisplay(int pageNumber, int itemsPerPage) throws ServiceException;

    boolean cancelBooking(String strBookingId) throws ServiceException;

    boolean confirmBooking(Long bookingId) throws ServiceException;

    Long getBookingIdByInvoiceId(Long invoiceId) throws ServiceException;
}

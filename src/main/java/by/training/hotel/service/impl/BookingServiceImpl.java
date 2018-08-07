package by.training.hotel.service.impl;

import by.training.hotel.dao.BookingDAO;
import by.training.hotel.dao.DAOFactory;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.BookingStatus;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.exception.ServiceException;
import by.training.hotel.service.util.PageCountDeterminant;
import by.training.hotel.service.validation.BookingValidator;
import by.training.hotel.service.validation.CommonValidator;

import java.util.List;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

    private final DAOFactory factory = DAOFactory.getInstance();

    private final BookingDAO<Long, Booking> bookingDao = factory.getBookingDao();

    @Override
    public Long addNewBooking(Booking booking) throws ServiceException {
        if (!BookingValidator.validateAddingBooking(booking)){
            return null;
        }
         try {
             return bookingDao.add(booking);
         } catch (DAOException e){
             throw new ServiceException(e);
         }
    }

    @Override
    public Set<Booking> getBookingsSetForUser(Integer userId) throws ServiceException{
        if (!CommonValidator.validateIntegerId(userId)){
            return null;
        }
        try{
            return bookingDao.getBookingsSetByUserId(userId);
        } catch (DAOException e){
            throw new ServiceException (e);
        }
    }

    @Override
    public boolean updateBooking(Booking booking) throws ServiceException{
        if (!BookingValidator.validateUpdatingBooking(booking)){
            return false;
        }
        try {
            return bookingDao.update(booking);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void clearIncompleteBookings(Integer userId) throws ServiceException{
        if (!CommonValidator.validateIntegerId(userId)){
            return;
        }
        try{
            bookingDao.clearIncompleteBookings(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Booking prepareBookingToOrder(SearchUnitDTO searchUnit, int userId){
        Booking preFormedOrder = new Booking();
        preFormedOrder.setCheckInDate(searchUnit.getFrom());
        preFormedOrder.setCheckOutDate(searchUnit.getTo());
        preFormedOrder.setAdultCount(searchUnit.getAdultCount());
        preFormedOrder.setChildCount(searchUnit.getChildCount());
        preFormedOrder.setComfortLevel(searchUnit.getComfortLevel());
        preFormedOrder.setUserId(userId);
        return preFormedOrder;
    }

    @Override
    public Booking getBookingByBookingId(String strBookingId) throws ServiceException{
        Long bookingId = Long.valueOf(strBookingId);
        if (!CommonValidator.validateLongId(bookingId)){
            return null;
        }
        try{
            return bookingDao.getById(bookingId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public synchronized CommonDTO<Booking> getBookingsListForDisplay(int pageNumber, int itemsPerPage) throws ServiceException{

        CommonDTO<Booking> bookingsForDisplay = new CommonDTO<>();

        int start = (pageNumber - 1) * itemsPerPage;

        try {
            List<Booking> bookingList = bookingDao.getElementsList(start, itemsPerPage);
            int bookingCount = bookingDao.getTotalCountOfBookings();
            int pageCount = PageCountDeterminant.definePageCount(bookingCount, itemsPerPage);

            bookingsForDisplay.setEntityList(bookingList);
            bookingsForDisplay.setPagesCount(pageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return bookingsForDisplay;
    }

    @Override
    public boolean cancelBooking(String strBookingId) throws ServiceException{

        Long bookingId = Long.valueOf(strBookingId);

        if (!CommonValidator.validateLongId(bookingId)){
            return false;
        }

        try {
            return bookingDao.delete(bookingId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean confirmBooking(Long bookingId) throws ServiceException {
        if (!CommonValidator.validateLongId(bookingId)){
            return false;
        }
        try {
            Booking bookingInProcess = bookingDao.getById(bookingId);
            if (bookingInProcess != null){
                bookingInProcess.setBookingStatus(BookingStatus.CONFIRMED);
                return bookingDao.update(bookingInProcess);
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long getBookingIdByInvoiceId(Long invoiceId) throws ServiceException {
        if (!CommonValidator.validateLongId(invoiceId)){
            return null;
        }
        try {
            return bookingDao.getBookingIdByInvoiceId(invoiceId);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}

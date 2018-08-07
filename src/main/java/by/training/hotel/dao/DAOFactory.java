package by.training.hotel.dao;

import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.impl.BookingDAOImpl;
import by.training.hotel.dao.impl.InvoiceDAOImpl;
import by.training.hotel.dao.impl.RoomDAOImpl;
import by.training.hotel.dao.impl.UserDAOImpl;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.User;

public final class DAOFactory {

    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private static DAOFactory INSTANCE = null;

    public UserDAO<Integer, User> getUserDao() {
        return new UserDAOImpl(POOL);
    }

    public BookingDAO<Long, Booking> getBookingDao() {
        return new BookingDAOImpl(POOL);
    }

    public InvoiceDAO<Long, Invoice> getInvoiceDao() {
        return new InvoiceDAOImpl(POOL);
    }

    public RoomDAO<Integer, Room> getRoomDao() {
        return new RoomDAOImpl(POOL);
    }

    public static synchronized DAOFactory getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DAOFactory();
        }
        return INSTANCE;
    }

}

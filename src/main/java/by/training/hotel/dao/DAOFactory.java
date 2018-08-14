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



    public UserDAO<User, Integer> getUserDao() {
        return new UserDAOImpl(POOL);
    }

    public BookingDAO<Booking, Long> getBookingDao() {
        return new BookingDAOImpl(POOL);
    }

    public InvoiceDAO<Invoice, Long> getInvoiceDao() {
        return new InvoiceDAOImpl(POOL);
    }

    public RoomDAO<Room, Integer> getRoomDao() {
        return new RoomDAOImpl(POOL);
    }

    public static synchronized DAOFactory getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DAOFactory();
        }
        return INSTANCE;
    }

}

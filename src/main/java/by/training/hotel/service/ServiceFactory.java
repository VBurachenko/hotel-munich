package by.training.hotel.service;

import by.training.hotel.service.impl.BookingServiceImpl;
import by.training.hotel.service.impl.InvoiceServiceImpl;
import by.training.hotel.service.impl.RoomServiceImpl;
import by.training.hotel.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final RoomService ROOM_SERVICE = new RoomServiceImpl();
    private static final BookingService BOOKING_SERVICE = new BookingServiceImpl();
    private static final InvoiceService INVOICE_SERVICE = new InvoiceServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public RoomService getRoomService() {
        return ROOM_SERVICE;
    }

    public BookingService getBookingService() {
        return BOOKING_SERVICE;
    }

    public InvoiceService getInvoiceService() {
        return INVOICE_SERVICE;
    }
}

package by.training.hotel.dao.impl;

import by.training.hotel.dao.BookingDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.dao.util.IdCreator;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.BookingStatus;
import by.training.hotel.entity.Room;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(DataProviderRunner.class)
public final class BookingDAOImplTest extends BaseDAOTest{

    private final BookingDAO<Long, Booking> bookingDAO = factory.getBookingDao();

    private Booking testBooking;

    @Before
    public void initTestingObject(){
        testBooking = new Booking();
    }

    @After
    public void cleanTestingObject(){
        testBooking = null;
    }

    @Test
    public void addNotExistingBooking() throws DAOException {

        Room roomFirst = new Room();
        roomFirst.setRoomNumber(1005);


        testBooking.setCheckInDate(new LocalDate(2018, 10, 25));
        testBooking.setCheckOutDate(new LocalDate(2018, 10, 28));
        testBooking.setAdultCount(1);
        testBooking.setChildCount(0);
        testBooking.setUserId(10);
        testBooking.getRoomsSet().add(roomFirst);

        Long actualBookingId = bookingDAO.add(testBooking);
        Long expectedBookingId = IdCreator.createLongIdByTodayDate(1);

        assertEquals(expectedBookingId, actualBookingId);
    }

    @Test
    public void addBookingWithOverlappingDates() throws DAOException {

        Room room = new Room();
        room.setRoomNumber(1006);
        testBooking.setCheckInDate(new LocalDate(2018, 7, 5));
        testBooking.setCheckOutDate(new LocalDate(2018, 7, 20));
        testBooking.setAdultCount(2);
        testBooking.setChildCount(1);
        testBooking.setUserId(12);
        testBooking.setBookingStatus(BookingStatus.REGISTERED);
        testBooking.getRoomsSet().add(room);

        Long actualBookingId = bookingDAO.add(testBooking);

        assertNull(actualBookingId);
    }

    @Test
    public void addBookingWithEmptyRoomNumbersSet() throws DAOException {

        testBooking.setCheckInDate(new LocalDate(2018, 7, 5));
        testBooking.setCheckOutDate(new LocalDate(2018, 7, 20));
        testBooking.setAdultCount(2);
        testBooking.setChildCount(1);
        testBooking.setUserId(12);
        testBooking.setBookingStatus(BookingStatus.REGISTERED);

        Long actualBookingId = bookingDAO.add(testBooking);

        assertNull(actualBookingId);
    }

    @Test
    public void addBookingWithNULLData() throws DAOException {

        Long actualBookingId = bookingDAO.add(testBooking);

        assertNull(actualBookingId);
    }

    @Test
    public void delete() {

    }

    @DataProvider
    public static Object[][] provideBookingsId(){
        return new Object[][] {{127062018L, true} , {124124L, false}, {127062018L, true}, {6L, false}};
    }

    @Test
    @UseDataProvider("provideBookingsId")
    public void getById(Long bookingId, boolean expected) throws DAOException {
        testBooking = bookingDAO.getById(bookingId);
        boolean bookingExists = testBooking != null;
        assertEquals(bookingExists, expected);
    }

    @Test
    public void getElementsList() throws DAOException {
        final int expected = 4;
        int actual = bookingDAO.getElementsList().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getElementsListLimit() throws DAOException {
        final int expected = 2;
        int actual = bookingDAO.getElementsList(1,2).size();
        assertEquals(expected, actual);
    }


}

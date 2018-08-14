package by.training.hotel.dao.impl;

import by.training.hotel.dao.AbstractDAO;
import by.training.hotel.dao.BookingDAO;
import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.dao.util.EntityParameter;
import by.training.hotel.dao.util.IdCreator;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.BookingStatus;
import by.training.hotel.entity.Room;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.training.hotel.dao.util.DataTypeConverter.localDateToSqlDate;
import static by.training.hotel.dao.util.DataTypeConverter.sqlToLocalDate;

public class BookingDAOImpl extends AbstractDAO<Booking, Long> implements BookingDAO<Booking, Long> {

    private static final String ADD_NEW_BOOKING_QUERY =
            "INSERT INTO booking (" +
                    "booking.booking_id, " +
                    "booking.check_in, " +
                    "booking.check_out, " +
                    "booking.adult_count, " +
                    "booking.child_count, " +
                    "booking.user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_ID_IF_OCCUPIED_ROOM =
            "{CALL defineNumberOfOccupiedRoom(?, ?, ?)}";

    private static final String INCLUDE_ROOM_IN_BOOKING =
            "INSERT INTO room_in_booking (" +
                    "room_in_booking.booking_id, " +
                    "room_in_booking.room_number) " +
                    "VALUES (?, ?)";

    private static final String CHECK_BOOKING =
            "SELECT booking.booking_id FROM booking ";

    private static final String CHECK_ROOM_STATUS =
            "SELECT room.room_number " +
                    "FROM room " +
                    "WHERE room_number = ? AND room.available_status > 0 ";

    private static final String WHERE_BOOKING_ID = " WHERE booking.booking_id = ?";

    private static final String WHERE_USER_ID = " WHERE booking.user_id = ?";

    private static final String AND_NULL_INVOICE = " AND booking.invoice_id IS NULL";

    private static final String WHERE_INVOICE_ID = " WHERE invoice_id = ?";

    private static final String DELETE_BOOKING =
            "DELETE FROM booking ";

    private static final String DELETE_ROOMS_FROM_BOOKING =
            "DELETE FROM room_in_booking WHERE room_in_booking.booking_id = ?";

    private static final String GET_BOOKINGS =
            "SELECT * FROM booking ";

    private static final String JOIN_FOR_LIMIT =
            "JOIN (SELECT booking.booking_id FROM booking ORDER BY booking.booking_id LIMIT ?, ?) " +
                    "AS b ON b.booking_id = booking.booking_id ";

    private static final String GET_ALL_ROOMS_FOR_CURRENT_BOOKING =
            "SELECT" +
                    " room.room_number," +
                    " room.berth_count," +
                    " room.comfort_level," +
                    " room.price_per_night," +
                    " room.picture_link," +
                    " room.available_status" +
                    " FROM room INNER JOIN room_in_booking " +
                    "ON room.room_number = room_in_booking.room_number " +
                    "WHERE `room_in_booking`.`booking_id` = ?";

    private static final String UPDATE_BOOKING =
            "UPDATE booking " +
                    "SET " +
                    "booking.check_in = ?, " +
                    "booking.check_out = ?, " +
                    "booking.adult_count = ?, " +
                    "booking.child_count = ?, " +
                    "booking.user_id = ?, " +
                    "booking.invoice_id = ?," +
                    "booking.booking_status = ? ";

    private static final String GET_TOTAL_COUNT_OF_BOOKINGS = "SELECT COUNT(*) FROM booking ";

    public BookingDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_BOOKING + WHERE_BOOKING_ID;
    }

    @Override
    protected String getTotalCountQuery() {
        return GET_TOTAL_COUNT_OF_BOOKINGS;
    }

    @Override
    public synchronized Long add(Booking incomingBooking) throws DAOException {

        Long justAddedBookingId = null;

        ProxyConnection proxyConnection = null;
        PreparedStatement preStatement = null;
        CallableStatement callStatement = null;

        try {

            proxyConnection = pool.takeConnection();

            callStatement = proxyConnection.prepareCall(GET_ID_IF_OCCUPIED_ROOM);
            preStatement = proxyConnection.prepareStatement(CHECK_ROOM_STATUS);

            if (areFreeRoomsCheck(callStatement, incomingBooking) &&
                    areAvailableRoomsCheck(preStatement, incomingBooking.getRoomsSet())) {

                proxyConnection.setAutoCommit(false);

                preStatement = proxyConnection.prepareStatement(ADD_NEW_BOOKING_QUERY);

                justAddedBookingId = prepareForAdd(proxyConnection, preStatement, incomingBooking);

                if (justAddedBookingId != null) {

                    preStatement = proxyConnection.prepareStatement(INCLUDE_ROOM_IN_BOOKING);

                    if (!includeRoomsInBooking(preStatement, justAddedBookingId, incomingBooking.getRoomsSet())) {
                        proxyConnection.rollback();
                        justAddedBookingId = null;
                    }
                }
                proxyConnection.commit();
                proxyConnection.setAutoCommit(true);
            }
        } catch (PoolException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DAOException(e1);
            }
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            pool.closeConnection(proxyConnection, preStatement, callStatement);
        }
        return justAddedBookingId;
    }

    @Override
    public synchronized boolean update(Booking booking) throws DAOException {

        boolean updatedSuccessfully = false;

        ProxyConnection proxyConnection = null;
        PreparedStatement preStatement = null;
        CallableStatement callStatement = null;

        try {

            proxyConnection = pool.takeConnection();

            proxyConnection.setAutoCommit(false);

            preStatement = proxyConnection.prepareStatement(UPDATE_BOOKING + WHERE_BOOKING_ID);

            prepareForUpdate(preStatement, booking);

            preStatement.execute();

            preStatement = proxyConnection.prepareStatement(DELETE_ROOMS_FROM_BOOKING);

            preStatement.setLong(1, booking.getBookingId());

            preStatement.execute();

            callStatement = proxyConnection.prepareCall(GET_ID_IF_OCCUPIED_ROOM);
            preStatement = proxyConnection.prepareStatement(CHECK_ROOM_STATUS);

            if (areFreeRoomsCheck(callStatement, booking) &&
                    areAvailableRoomsCheck(preStatement, booking.getRoomsSet())) {

                preStatement = proxyConnection.prepareStatement(INCLUDE_ROOM_IN_BOOKING);

                if (includeRoomsInBooking(preStatement, booking.getBookingId(), booking.getRoomsSet())) {
                    updatedSuccessfully = true;
                    proxyConnection.commit();
                    proxyConnection.setAutoCommit(true);
                }
            }

        } catch (PoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }
        } finally {
            pool.closeConnection(proxyConnection, preStatement, callStatement);
        }
        return updatedSuccessfully;
    }

    @Override
    public Booking getById(Long bookingId) throws DAOException {

        Booking wantedBooking = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(GET_BOOKINGS + WHERE_BOOKING_ID);

            statement.setLong(1, bookingId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                wantedBooking = getFromResultSet(resultSet);
                Set<Room> roomNumbersSet = getRoomsSetForBooking(proxyConnection, bookingId);
                wantedBooking.setRoomsSet(roomNumbersSet);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return wantedBooking;
    }

    @Override
    public synchronized List<Booking> getElementsList(int start, int offset) throws DAOException {

        List<Booking> allBookingsList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            if (start == 0 && offset == 0) {
                statement = proxyConnection.prepareStatement(GET_BOOKINGS);
            } else {
                statement = proxyConnection.prepareStatement(GET_BOOKINGS + JOIN_FOR_LIMIT);

                statement.setInt(1, start);
                statement.setInt(2, offset);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Booking currentBooking = getFromResultSet(resultSet);
                Set<Room> roomsInBooking = getRoomsSetForBooking(proxyConnection, currentBooking.getBookingId());
                currentBooking.setRoomsSet(roomsInBooking);
                allBookingsList.add(currentBooking);
            }
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return allBookingsList;
    }

    @Override
    public Set<Booking> getBookingsSetByUserId(Integer userId) throws DAOException {

        Set<Booking> bookingOfUser = new HashSet<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            statement = proxyConnection.prepareStatement(GET_BOOKINGS + WHERE_USER_ID);

            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Booking currentBooking = getFromResultSet(resultSet);
                Set<Room> roomNumbersSet = getRoomsSetForBooking(proxyConnection, currentBooking.getBookingId());
                currentBooking.setRoomsSet(roomNumbersSet);
                bookingOfUser.add(currentBooking);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return bookingOfUser;
    }

    @Override
    public Long getBookingIdByInvoiceId(Long invoiceId) throws DAOException {
        Long wantedBookingId = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            statement = proxyConnection.prepareStatement(CHECK_BOOKING + WHERE_INVOICE_ID);
            statement.setLong(1, invoiceId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                wantedBookingId = resultSet.getLong(1);
            }
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return wantedBookingId;
    }

    @Override
    public void clearIncompleteBookings(Integer userId) throws DAOException {

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(DELETE_BOOKING + WHERE_USER_ID + AND_NULL_INVOICE);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
    }

    private Set<Room> getRoomsSetForBooking(ProxyConnection connection, Long bookingId)
            throws SQLException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Set<Room> roomsSet = new HashSet<>();

        try {
            statement = connection.prepareStatement(GET_ALL_ROOMS_FOR_CURRENT_BOOKING);
            statement.setLong(1, bookingId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room receivedRoom = getRoomForBooking(resultSet);
                roomsSet.add(receivedRoom);
            }
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return roomsSet;
    }

    private boolean areFreeRoomsCheck(CallableStatement statement, Booking booking)
            throws SQLException {

        boolean roomsAreFree = true;
        if (booking.getRoomsSet().size() != 0) {
            for (Room roomInBooking : booking.getRoomsSet()) {
                statement.setDate(1, localDateToSqlDate(booking.getCheckInDate()));
                statement.setDate(2, localDateToSqlDate(booking.getCheckOutDate()));
                statement.setInt(3, roomInBooking.getRoomNumber());
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    roomsAreFree = false;
                    break;
                }
            }
        } else {
            roomsAreFree = false;
        }
        return roomsAreFree;
    }

    private boolean areAvailableRoomsCheck(PreparedStatement statement, Set<Room> roomsSet)
            throws SQLException {
        boolean roomIsAvailable = true;
        for (Room roomInBooking : roomsSet) {
            statement.setInt(1, roomInBooking.getRoomNumber());
            if (!statement.executeQuery().next()) {
                roomIsAvailable = false;
                break;
            }
        }
        return roomIsAvailable;
    }

    private Long prepareForAdd(ProxyConnection connection, PreparedStatement statement, Booking booking)
            throws SQLException {

        Long bookingId = generateBookingId(connection);

        statement.setLong(1, bookingId);

        java.sql.Date checkIn = localDateToSqlDate(booking.getCheckInDate());
        statement.setDate(2, checkIn);

        java.sql.Date checkOut = localDateToSqlDate(booking.getCheckOutDate());
        statement.setDate(3, checkOut);

        Integer adultsCount = booking.getAdultCount();
        statement.setInt(4, adultsCount);

        Integer childCount = booking.getChildCount();
        statement.setInt(5, childCount);

        Integer userId = booking.getUserId();
        statement.setInt(6, userId);

        statement.execute();

        return bookingId;
    }

    private Long generateBookingId(ProxyConnection proxyConnection)
            throws SQLException {
        int startIndex = 1;
        Long bookingId = IdCreator.createLongIdByTodayDate(startIndex);
        while (bookingIdIsExists(proxyConnection, bookingId)) {
            bookingId = IdCreator.createLongIdByTodayDate(++startIndex);
        }
        return bookingId;
    }

    private boolean includeRoomsInBooking(PreparedStatement statement, long orderId, Set<Room> roomsInBooking)
            throws SQLException {
        for (Room currentRoom : roomsInBooking) {
            statement.setLong(1, orderId);
            statement.setInt(2, currentRoom.getRoomNumber());
            statement.addBatch();
        }
        return roomsInBooking.size() == statement.executeBatch().length;
    }

    private boolean bookingIdIsExists(ProxyConnection proxyConnection, Long orderId)
            throws SQLException {
        PreparedStatement statement = proxyConnection.prepareStatement(CHECK_BOOKING + WHERE_BOOKING_ID);
        statement.setLong(1, orderId);
        return statement.executeQuery().next();
    }

    private void prepareForUpdate(PreparedStatement statement, Booking booking) throws SQLException {

        java.sql.Date checkIn = localDateToSqlDate(booking.getCheckInDate());
        statement.setDate(1, checkIn);

        java.sql.Date checkOut = localDateToSqlDate(booking.getCheckOutDate());
        statement.setDate(2, checkOut);

        Integer adultsCount = booking.getAdultCount();
        statement.setInt(3, adultsCount);

        Integer childCount = booking.getChildCount();
        statement.setInt(4, childCount);

        Integer userId = booking.getUserId();
        statement.setInt(5, userId);

        Long invoiceId = booking.getInvoiceId();
        statement.setLong(6, invoiceId);

        String bookingStatus = booking.getBookingStatus().toString();
        statement.setString(7, bookingStatus.toLowerCase());

        Long bookingId = booking.getBookingId();
        statement.setLong(8, bookingId);
    }

    private Booking getFromResultSet(ResultSet resultSet) throws SQLException {

        Booking booking = new Booking();

        Long bookingId = resultSet.getLong(EntityParameter.BOOKING_ID);
        booking.setBookingId(bookingId);

        java.sql.Date checkInDate = resultSet.getDate(EntityParameter.CHECK_IN);
        booking.setCheckInDate(sqlToLocalDate(checkInDate));

        java.sql.Date checkOutDate = resultSet.getDate(EntityParameter.CHECK_OUT);
        booking.setCheckOutDate(sqlToLocalDate(checkOutDate));

        Integer adultCount = resultSet.getInt(EntityParameter.ADULT_COUNT);
        booking.setAdultCount(adultCount);

        Integer childCount = resultSet.getInt(EntityParameter.CHILD_COUNT);
        booking.setChildCount(childCount);

        Integer userId = resultSet.getInt(EntityParameter.USER_ID);
        booking.setUserId(userId);

        Long invoiceId = resultSet.getLong(EntityParameter.INVOICE_ID);
        booking.setInvoiceId(invoiceId);

        String bookingStatus = resultSet.getString(EntityParameter.BOOKING_STATUS).toUpperCase();
        booking.setBookingStatus(BookingStatus.valueOf(bookingStatus));

        return booking;
    }

    private Room getRoomForBooking(ResultSet resultSet) throws SQLException {

        Room receivedRoom = new Room();

        Integer roomNumber = resultSet.getInt(EntityParameter.ROOM_NUMBER);
        receivedRoom.setRoomNumber(roomNumber);

        Integer berthCount = resultSet.getInt(EntityParameter.BERTH_COUNT);
        receivedRoom.setBerthCount(berthCount);

        Integer comfortLevel = resultSet.getInt(EntityParameter.COMFORT_LEVEL);
        receivedRoom.setComfortLevel(comfortLevel);

        Double pricePerNight = resultSet.getDouble(EntityParameter.PRICE_PER_NIGHT);
        receivedRoom.setPricePerNight(pricePerNight);

        String pictureLink = resultSet.getString(EntityParameter.PICTURE_LINK);
        receivedRoom.setPictureLink(pictureLink);

        Boolean availableStatus = resultSet.getBoolean(EntityParameter.AVAILABLE_STATUS);
        receivedRoom.setAvailableStatus(availableStatus);

        return receivedRoom;
    }
}

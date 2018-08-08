package by.training.hotel.dao.impl;

import by.training.hotel.dao.AbstractDAO;
import by.training.hotel.dao.RoomDAO;
import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.dao.util.EntityParameter;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import org.joda.time.LocalDate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.hotel.dao.util.DataTypeConverter.localDateToSqlDate;

public class RoomDAOImpl extends AbstractDAO implements RoomDAO<Integer, Room> {

    private static final String ADD_ROOM_QUERY =
            "INSERT INTO room " +
                    "(room.room_number, " +
                    "room.berth_count, " +
                    "room.comfort_level, " +
                    "room.price_per_night, " +
                    "room.picture_link) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ROOMS =
            "SELECT " +
                    "room.room_number, " +
                    "room.berth_count, " +
                    "room.comfort_level, " +
                    "room.price_per_night, " +
                    "room.picture_link, " +
                    "room.available_status " +
                    "FROM room ";

    private static final String JOIN_FOR_LIMIT =
            "JOIN (SELECT room.room_number FROM room ORDER BY room.room_number LIMIT ?, ?) " +
                    "AS b ON b.room_number = room.room_number";

    private static final String UPDATE_ROOM =
            "UPDATE room " +
                    "SET " +
                    "room.berth_count = ?, " +
                    "room.comfort_level = ?, " +
                    "room.price_per_night = ?, " +
                    "room.picture_link = ?, " +
                    "room.available_status = ? ";

    private static final String DELETE_ROOM =
            "DELETE FROM room ";

    private static final String WHERE_PICTURE_LINK =
            "WHERE room.picture_link = ?";

    private static final String GET_CURRENT_ROOM_NUMBER =
            "SELECT room.room_number FROM room ";


    private static final String WHERE_ROOM_NUMBER = " WHERE room_number = ?";

    private static final String GET_FREE_ROOMS = "{CALL findFreeRooms(?, ?, ?, ?, ?, ?, ?)}";

    private static final String GET_COUNT_OF_FREE_ROOMS = "{CALL getCountOfFreeRooms(?, ?, ?, ?, ?)}";

    private static final String GET_TOTAL_COUNT_OF_ROOMS = "SELECT COUNT(*) FROM room";

    public RoomDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Integer add(Room room) throws DAOException {

        Integer roomIsAdded = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_ROOMS + WHERE_PICTURE_LINK);

            statement.setString(1, room.getPictureLink());

            resultSet = statement.executeQuery();

            if (!resultSet.next()){
                statement = proxyConnection.prepareStatement(ADD_ROOM_QUERY);

                prepareForAdd(statement, room);

                statement.execute();

                statement = proxyConnection.prepareStatement(GET_CURRENT_ROOM_NUMBER + WHERE_PICTURE_LINK);

                statement.setString(1, room.getPictureLink());

                resultSet = statement.executeQuery();

                if (resultSet.next()){
                    roomIsAdded = resultSet.getInt(EntityParameter.ROOM_NUMBER);
                }
            }
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return roomIsAdded;
    }

    @Override
    public boolean update(Room room) throws DAOException {

        boolean updatedSuccessfully = false;

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(UPDATE_ROOM + WHERE_ROOM_NUMBER);

            prepareForUpdate(statement, room);

            if (statement.executeUpdate() == 1){
                updatedSuccessfully = true;
            }

        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
        return updatedSuccessfully;
    }

    @Override
    public boolean delete(Integer roomNumber) throws DAOException {
        boolean deletedSuccessfully = false;

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(DELETE_ROOM + WHERE_ROOM_NUMBER);

           statement.setInt(1, roomNumber);

            if (statement.executeUpdate() == 1){
                deletedSuccessfully = true;
            }

        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
        return deletedSuccessfully;
    }

    @Override
    public Room getById(Integer roomNumber) throws DAOException {

        Room currentRoom = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_ROOMS + WHERE_ROOM_NUMBER);

            statement.setInt(1, roomNumber);

            resultSet = statement.executeQuery();
            if (resultSet.next()){
                currentRoom = getFromResultSet(resultSet);
            }
        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return currentRoom;
    }

    @Override
    public List<Room> getElementsList(int start, int offset) throws DAOException {
        List <Room> allRoomsList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){

            if (start == 0 && offset == 0){
                statement = proxyConnection.prepareStatement(GET_ROOMS);
            } else {
                statement = proxyConnection.prepareStatement(GET_ROOMS + JOIN_FOR_LIMIT);

                statement.setInt(1, start);
                statement.setInt(2, offset);
            }

            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Room room = getFromResultSet(resultSet);
                allRoomsList.add(room);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return allRoomsList;
    }

    @Override
    public List<Room> getElementsList() throws DAOException {
        return getElementsList(0, 0);
    }

    @Override
    public List<Room> findFreeRooms(SearchUnitDTO searchUnit, int start, int offset) throws DAOException {
        List<Room> freeRooms = new ArrayList<>();

        CallableStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareCall(GET_FREE_ROOMS);

            prepareToFind(statement, searchUnit);

            statement.setInt(6, start);

            statement.setInt(7, offset);

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Room room = getFromResultSet(resultSet);
                freeRooms.add(room);
            }

        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return freeRooms;
    }

    @Override
    public int getCountOfFreeRooms(SearchUnitDTO searchUnit) throws DAOException{

        int freeRoomsCount;

        CallableStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareCall(GET_COUNT_OF_FREE_ROOMS);

            prepareToFind(statement, searchUnit);

            resultSet = statement.executeQuery();
            resultSet.next();
            freeRoomsCount = resultSet.getInt(1);

        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }

        return freeRoomsCount;
    }

    @Override
    public int getTotalCountOfRooms() throws DAOException {

        int totalCountOfRooms;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_TOTAL_COUNT_OF_ROOMS);
            resultSet = statement.executeQuery();
            resultSet.next();
            totalCountOfRooms = resultSet.getInt(1);
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return totalCountOfRooms;
    }

    private void prepareForUpdate(PreparedStatement statement, Room room) throws SQLException {

        Integer berthCount = room.getBerthCount();
        statement.setInt(1, berthCount);

        Integer luxuryLevel = room.getComfortLevel();
        statement.setInt(2, luxuryLevel);

        Double pricePerNight = room.getPricePerNight();
        statement.setDouble(3, pricePerNight);

        String pictureLink = room.getPictureLink();
        statement.setString(4, pictureLink);

        Boolean availableStatus = room.getAvailableStatus();
        statement.setBoolean(5, availableStatus);

        Integer roomNumber = room.getRoomNumber();
        statement.setInt(6, roomNumber);

    }


    private void prepareForAdd(PreparedStatement statement, Room room) throws SQLException {

        Integer roomNumber = room.getRoomNumber();
        statement.setInt(1, roomNumber);

        Integer berthCount = room.getBerthCount();
        statement.setInt(2, berthCount);

        Integer comfortLevel = room.getComfortLevel();
        statement.setInt(3, comfortLevel);

        Double pricePerNight = room.getPricePerNight();
        statement.setDouble(4, pricePerNight);

        String pictureLink = room.getPictureLink();
        statement.setString(5, pictureLink);

    }

    private Room getFromResultSet(ResultSet resultSet) throws SQLException {

        Room currentRoom = new Room();

        Integer roomNumber = resultSet.getInt(EntityParameter.ROOM_NUMBER);
        currentRoom.setRoomNumber(roomNumber);

        Integer berthCount = resultSet.getInt(EntityParameter.BERTH_COUNT);
        currentRoom.setBerthCount(berthCount);

        Integer comfortLevel = resultSet.getInt(EntityParameter.COMFORT_LEVEL);
        currentRoom.setComfortLevel(comfortLevel);

        Double pricePerNight = resultSet.getDouble(EntityParameter.PRICE_PER_NIGHT);
        currentRoom.setPricePerNight(pricePerNight);

        String pictureLink = resultSet.getString(EntityParameter.PICTURE_LINK);
        currentRoom.setPictureLink(pictureLink);

        Boolean availableStatus = resultSet.getBoolean(EntityParameter.AVAILABLE_STATUS);
        currentRoom.setAvailableStatus(availableStatus);

        return currentRoom;
    }

    private void prepareToFind(CallableStatement statement, SearchUnitDTO searchUnit) throws SQLException{

        LocalDate from = searchUnit.getFrom();
        statement.setDate(1, localDateToSqlDate(from));

        LocalDate to = searchUnit.getTo();
        statement.setDate(2, localDateToSqlDate(to));

        Integer adultCount = searchUnit.getAdultCount();
        statement.setInt(3, adultCount);

        Integer childCount = searchUnit.getChildCount();
        statement.setInt(4, childCount);

        Integer comfortLevel = searchUnit.getComfortLevel();
        statement.setInt(5, comfortLevel);
    }
}

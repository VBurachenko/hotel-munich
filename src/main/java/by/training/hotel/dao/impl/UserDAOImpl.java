package by.training.hotel.dao.impl;

import by.training.hotel.dao.AbstractDAO;
import by.training.hotel.dao.UserDAO;
import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.dao.util.EntityParameter;
import by.training.hotel.entity.User;
import by.training.hotel.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.hotel.dao.util.DateTypeConverter.localDateToSqlDate;
import static by.training.hotel.dao.util.DateTypeConverter.sqlToLocalDate;

public class UserDAOImpl extends AbstractDAO implements UserDAO<Integer, User> {

    private final static Logger LOGGER = LogManager.getLogger();

    private final static String ADD_USER_QUERY =
            "INSERT INTO user " +
                    "(user.email," +
                    " user.password," +
                    " user.name," +
                    " user.surname," +
                    " user.tel_number," +
                    " user.birthday," +
                    " user.discount," +
                    " user.gender_male) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String GET_USERS =
            "SELECT" +
                    " user.user_id," +
                    " user.email," +
                    " user.password," +
                    " user.name," +
                    " user.surname," +
                    " user.tel_number," +
                    " user.birthday," +
                    " user.discount," +
                    " user.gender_male," +
                    " user.blocked," +
                    " user.role " +
                    "FROM user";

    private final static String JOIN_FOR_LIMIT =
            " JOIN (SELECT user_id FROM user ORDER BY user_id LIMIT ?, ?) " +
            "AS b ON b.user_id = user.user_id";

    private final static String GET_CURRENT_USER_ID = "SELECT user.user_id FROM user ";

    private final static String GET_TOTAL_COUNT_OF_USERS = "SELECT COUNT(*) FROM user ";

    private final static String UPDATE_USER_QUERY =
            "UPDATE user " +
                    "SET user.email = ?," +
                    " user.password = ?," +
                    " user.name = ?," +
                    " user.surname = ?," +
                    " user.tel_number = ?," +
                    " user.birthday = ?," +
                    " user.discount = ?," +
                    " user.gender_male = ?," +
                    " user.blocked = ?," +
                    " user.role = ? ";

    private final static String DELETE_USER = "DELETE FROM user";

    private final static String WHERE_USER_ID = " WHERE user.user_id = ?";

    private final static String WHERE_USER_EMAIL = " WHERE user.email = ?";

    private final static String OR_TEL_NUMBER = " OR user.tel_number = ?";

    public UserDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Integer add(User user) throws DAOException {

        Integer userId = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_USERS + WHERE_USER_EMAIL + OR_TEL_NUMBER);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getTelNumber());

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                statement = proxyConnection.prepareStatement(ADD_USER_QUERY);

                prepareForAdd(statement, user);

                statement.execute();

                statement = proxyConnection.prepareStatement(GET_CURRENT_USER_ID + WHERE_USER_EMAIL);

                statement.setString(1, user.getEmail());

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }

            }
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return userId;
    }

    @Override
    public boolean update(User user) throws DAOException {

        boolean updatedSuccessfully = false;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){

            statement = proxyConnection.prepareStatement(UPDATE_USER_QUERY + WHERE_USER_ID);

            prepareForUpdate(statement, user);

            if (statement.executeUpdate() == 1){
                updatedSuccessfully = true;
            }

        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return updatedSuccessfully;
    }

    @Override
    public boolean delete(Integer id) throws DAOException {

        boolean deletedSuccessfully = false;

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(DELETE_USER + WHERE_USER_ID);

            statement.setInt(1, id);

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
    public User getById(Integer id) throws DAOException {

        User wantedUser = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_USERS + WHERE_USER_ID);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()){
                wantedUser = getFromResultSet(resultSet);
            }
        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return wantedUser;
    }

    @Override
    public List<User> getElementsList(int start, int offset) throws DAOException {

        List<User> userList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){

            if (start == 0 && offset == 0){
                statement = proxyConnection.prepareStatement(GET_USERS);
            } else {
                statement = proxyConnection.prepareStatement(GET_USERS + JOIN_FOR_LIMIT);

                statement.setInt(1, start);
                statement.setInt(2, offset);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = getFromResultSet(resultSet);
                userList.add(user);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return userList;
    }

    @Override
    public List<User> getElementsList() throws DAOException {

        return getElementsList(0, 0);
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {

        User user = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_USERS + WHERE_USER_EMAIL);

            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()){
                user = getFromResultSet(resultSet);
            }
        } catch (SQLException | PoolException e){
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return user;
    }

    @Override
    public int getTotalCountOfUsers() throws DAOException{

        int totalCountOfRooms;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(GET_TOTAL_COUNT_OF_USERS);
            resultSet = statement.executeQuery();
            resultSet.next();
            totalCountOfRooms = resultSet.getInt(1);
        } catch (SQLException | PoolException e){
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return totalCountOfRooms;
    }

    private void prepareForAdd(PreparedStatement statement, User user) throws SQLException {

        String email = user.getEmail();
        statement.setString(1, email);

        String password = user.getPassword();
        statement.setString(2, password);

        String name = user.getName();
        statement.setString(3, name);

        String surname = user.getSurname();
        statement.setString(4, surname);

        String telNumber = user.getTelNumber();
        statement.setString(5, telNumber);

        LocalDate birthday = user.getBirthday();
        statement.setDate(6, localDateToSqlDate(birthday));

        Integer discount = user.getDiscount();
        statement.setInt(7, discount);

        Boolean genderMale = user.getGenderMale();
        statement.setBoolean(8, genderMale);
    }

    private void prepareForUpdate(PreparedStatement statement, User user) throws SQLException {

        String email = user.getEmail();
        statement.setString(1, email);

        String password = user.getPassword();
        statement.setString(2, password);

        String name = user.getName();
        statement.setString(3, name);

        String surname = user.getSurname();
        statement.setString(4, surname);

        String telNumber = user.getTelNumber();
        statement.setString(5, telNumber);

        LocalDate birthday = user.getBirthday();
        statement.setDate(6, localDateToSqlDate(birthday));

        Integer discount = user.getDiscount();
        statement.setInt(7, discount);

        Boolean genderMale = user.getGenderMale();
        statement.setBoolean(8, genderMale);

        Boolean blocked = user.getBlocked();
        statement.setBoolean(9, blocked);

        String userRole = user.getRole().toString();
        statement.setString(10, userRole);

        Integer userId = user.getUserId();
        statement.setInt(11, userId);
    }

    private User getFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();

        Integer userId = resultSet.getInt(EntityParameter.USER_ID);
        user.setUserId(userId);

        String email = resultSet.getString(EntityParameter.EMAIL);
        user.setEmail(email);

        String password = resultSet.getString(EntityParameter.PASSWORD);
        user.setPassword(password);

        String name = resultSet.getString(EntityParameter.NAME);
        user.setName(name);

        String surname = resultSet.getString(EntityParameter.SURNAME);
        user.setSurname(surname);

        String telNumber = resultSet.getString(EntityParameter.TEL_NUMBER);
        user.setTelNumber(telNumber);

        java.sql.Date birthday = resultSet.getDate(EntityParameter.BIRTHDAY);
        user.setBirthday(sqlToLocalDate(birthday));

        Integer discount = resultSet.getInt(EntityParameter.DISCOUNT);
        user.setDiscount(discount);

        Boolean genderMale = resultSet.getBoolean(EntityParameter.GENDER_MALE);
        user.setGenderMale(genderMale);

        Boolean blocked = resultSet.getBoolean(EntityParameter.BLOCKED);
        user.setBlocked(blocked);

        String role = resultSet.getString(EntityParameter.ROLE).toUpperCase();
        user.setRole(UserRole.valueOf(role));

        return user;
    }

}
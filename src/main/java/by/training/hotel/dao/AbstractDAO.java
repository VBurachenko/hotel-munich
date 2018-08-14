package by.training.hotel.dao;

import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<E extends Entity, N extends Number> implements EntityDAO<E, N>{

    protected ConnectionPool pool;

    protected AbstractDAO(ConnectionPool connectionPool) {
        pool = connectionPool;
    }

    protected abstract String getDeleteQuery();

    public boolean delete(N id) throws DAOException{

        boolean deletedSuccessfully = false;

        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(getDeleteQuery());
            statement.setObject(1, id);

            if (statement.executeUpdate() == 1){
                deletedSuccessfully = true;
            }
        } catch (PoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
        return deletedSuccessfully;
    }

    public List<E> getElementsList() throws DAOException{
        return getElementsList(0, 0);
    }

    protected abstract String getTotalCountQuery();

    public int getTotalCountOfElements() throws DAOException{

        int totalCountOfElements = 0;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (ProxyConnection proxyConnection = pool.takeConnection()){
            statement = proxyConnection.prepareStatement(getTotalCountQuery());
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                totalCountOfElements = resultSet.getInt(1);
            }
        } catch (PoolException | SQLException e) {
            throw new DAOException();
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return totalCountOfElements;
    }
}

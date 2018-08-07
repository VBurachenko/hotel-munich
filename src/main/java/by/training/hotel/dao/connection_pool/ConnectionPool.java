package by.training.hotel.dao.connection_pool;

import by.training.hotel.dao.connection_pool.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();

    private static volatile ConnectionPool instance;

    private BlockingQueue<ProxyConnection> POOL = null;

    private AtomicBoolean poolInitialised = new AtomicBoolean(false);

    private ConnectionPool() {
    }

    public void init(String configFilePath){
        ConnectionCreator connectionCreator = ConnectionCreator.getInstance(configFilePath);
        if (!poolInitialised.get()){
            int poolSize = ConfigProperties.POOL_SIZE;
            try{
                POOL = new ArrayBlockingQueue<>(poolSize);
                for (int i = 0; i < poolSize; i++){
                    ProxyConnection proxyConnection = connectionCreator.getConnection();
                    proxyConnection.setAutoCommit(true);
                    POOL.add(proxyConnection);
                }
                poolInitialised.set(true);
            } catch (SQLException | ClassNotFoundException e){
                LOGGER.error("Exception during connection pool init.", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void destroy(){
        if (poolInitialised.get()){
            try {
                for (ProxyConnection proxyConnection : POOL){
                    if (!proxyConnection.getAutoCommit()){
                        proxyConnection.commit();
                    }
                    proxyConnection.finallyClose();
                }
                POOL.clear();
                poolInitialised.set(false);
            } catch (SQLException e) {
                LOGGER.error("Exception during connection pool destroy.", e);
                throw new RuntimeException(e);
            }
        }
    }

    public ProxyConnection takeConnection() throws PoolException {
        try {
            return POOL.take();
        } catch (InterruptedException e) {
            throw new PoolException("Thread error during connection receiving.", e);
        }
    }

    private void returnConnection(ProxyConnection proxyConnection) throws PoolException{
        try {
            POOL.put(proxyConnection);
        } catch (InterruptedException e) {
            throw new PoolException("Thread error during connection returning.", e);
        }
    }

    void releaseConnection(ProxyConnection proxyConnection){
        POOL.offer(proxyConnection);
    }

    public void closeConnection(ProxyConnection connection, Statement ... statements){
        closeConnection(connection);
        for (Statement usedStatement : statements){
            closeStatement(usedStatement);
        }
    }

    public void closeConnection(ProxyConnection connection, ResultSet resultSet, Statement ... statements){
        closeConnection(connection);
        closeResultSet(resultSet);
        for (Statement usedStatement : statements){
            closeStatement(usedStatement);
        }
    }

    public void closeUsedResources(ResultSet resultSet, Statement ... statements){
        closeResultSet(resultSet);
        for (Statement usedStatement : statements){
            closeStatement(usedStatement);
        }
    }

    public void closeUsedResources(Statement ... statements){
        for (Statement usedStatement : statements){
            closeStatement(usedStatement);
        }
    }

    private void closeStatement(Statement usedStatement){
        if (usedStatement != null){
            try {
                usedStatement.close();
            } catch (SQLException e) {
                LOGGER.error("Statement wasn't closed.");
            }
        }
    }

    private void closeResultSet(ResultSet usedResultSet){
        if (usedResultSet != null){
            try {
                usedResultSet.close();
            } catch (SQLException e) {
                LOGGER.error("ResultSet wasn't closed.", e);
            }
        }
    }

    private void closeConnection(ProxyConnection connection){
        if (connection != null){
            try {
                returnConnection(connection);
            } catch (PoolException e) {
                LOGGER.error("Connection wasn't closed.", e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null){
            synchronized (ConnectionPool.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }
}

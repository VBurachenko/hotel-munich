package by.training.hotel.dao;

import by.training.hotel.dao.connection_pool.ConnectionPool;

public abstract class AbstractDAO {

    protected ConnectionPool pool;

    protected AbstractDAO(ConnectionPool connectionPool) {
        pool = connectionPool;
    }
}

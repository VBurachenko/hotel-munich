package by.training.hotel.dao.connection_pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static by.training.hotel.dao.connection_pool.Parameter.DB_AUTO_RECONNECT;
import static by.training.hotel.dao.connection_pool.Parameter.DB_CHAR_ENCODING;
import static by.training.hotel.dao.connection_pool.Parameter.DB_PASSWORD;
import static by.training.hotel.dao.connection_pool.Parameter.DB_USER;
import static by.training.hotel.dao.connection_pool.Parameter.DB_USE_SSL;
import static by.training.hotel.dao.connection_pool.Parameter.DB_USE_UNICODE;

final class ConnectionCreator {

    private static ConnectionCreator INSTANCE = null;

    private Properties properties;

    private ConnectionCreator(String configFilePath){

        ConfigProperties.init(configFilePath);

        properties = new Properties();

        properties.put(DB_USE_SSL, ConfigProperties.USE_SSL);
        properties.put(DB_USER, ConfigProperties.USER);
        properties.put(DB_PASSWORD, ConfigProperties.PASSWORD);
        properties.put(DB_AUTO_RECONNECT, ConfigProperties.AUTO_RECONNECT);
        properties.put(DB_CHAR_ENCODING, ConfigProperties.CHAR_ENCODING);
        properties.put(DB_USE_UNICODE, ConfigProperties.USE_UNICODE);

    }

    ProxyConnection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(ConfigProperties.DRIVER);

        String url = ConfigProperties.URL;

        Connection connection = DriverManager.getConnection(url, properties);

        return new ProxyConnection(connection);
    }

    static synchronized ConnectionCreator getInstance(String configFilePath) {
        if (INSTANCE == null){
            INSTANCE = new ConnectionCreator(configFilePath);
        }
        return INSTANCE;
    }
}

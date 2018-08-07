package by.training.hotel.dao.impl;

import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import static by.training.hotel.dao.impl.util.ReaderCreator.getBufferedReaderForFile;

public class TestDatabaseManager {

    private final static String TEST_DATABASE_CONFIG_FILE_PATH = "test_database_config";

    private final static String DUMP_DATABASE_FILE_PATH = "test_database_dump.sql";

    private final static ConnectionPool pool = ConnectionPool.getInstance();

    private TestDatabaseManager() {
    }

    static void prepareTestConnectionPool(){
        pool.init(TEST_DATABASE_CONFIG_FILE_PATH);
    }

    static void createAndFillTestDatabase() throws PoolException, IOException, SQLException {

        ProxyConnection proxyConnection = null;
        Statement statement = null;

        try (BufferedReader reader = getBufferedReaderForFile(DUMP_DATABASE_FILE_PATH)){

            String readLine;
            StringBuilder databaseDump = new StringBuilder();

            while ((readLine = reader.readLine()) != null){
                databaseDump.append(readLine).append("\n");
            }

            proxyConnection = pool.takeConnection();

            statement = proxyConnection.createStatement();
            statement.execute(databaseDump.toString());

        } finally {
            pool.closeConnection(proxyConnection, statement);
        }
    }

    static void destroyTestConnectionPool(){
        pool.destroy();
    }
}

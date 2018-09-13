package by.training.hotel.controller.listener;

import by.training.hotel.dao.connection_pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolInitializer implements ServletContextListener {

    private final static String databaseConfigFilePath = "main_database_config";

    private static final ConnectionPool pool = ConnectionPool.getInstance();

    protected static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool.init(databaseConfigFilePath);
        LOGGER.info("Pool init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        pool.destroy();
        LOGGER.info("Pool destroyed");
    }
}

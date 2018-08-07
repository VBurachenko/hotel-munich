package by.training.hotel.dao.impl;

import by.training.hotel.dao.DAOFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class BaseDAOTest {

    final DAOFactory factory = DAOFactory.getInstance();

    @Rule
    public final ExpectedException thrownException = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {
        TestDatabaseManager.prepareTestConnectionPool();
        TestDatabaseManager.createAndFillTestDatabase();
    }

    @AfterClass
    public static void tearDown() {
        TestDatabaseManager.destroyTestConnectionPool();
    }
}

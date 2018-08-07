package by.training.hotel.dao.impl;

import by.training.hotel.dao.UserDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.User;
import by.training.hotel.entity.UserRole;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static by.training.hotel.dao.util.PasswordConverter.convertToHash;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class UserDAOImplTest extends BaseDAOTest{

    private final UserDAO<Integer, User> userDAO = factory.getUserDao();

    private User testUser = new User();

    @Before
    public void initTestingObject(){
        testUser = new User();
    }

    @After
    public void cleanTestingObject(){
        testUser = null;
    }

    @Test
    public void addNotExistingUser() throws DAOException {

        testUser.setEmail("13Saha@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375296026827");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(20);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        Integer expectedUserId = 15;

        Integer actualUserId = userDAO.add(testUser);
        assertEquals(expectedUserId, actualUserId);

    }

    @Test
    public void addUserWithExistingEmail() throws DAOException {

        testUser.setEmail("qwert@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375296026827");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(20);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        Integer actualUserId = userDAO.add(testUser);
        assertNull(actualUserId);
    }

    @Test
    public void addUserWithExistingTelNumber() throws DAOException {

        testUser.setEmail("qwert@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375447754924");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(20);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        Integer actualUserId = userDAO.add(testUser);
        assertNull(actualUserId);
    }

    @Test
    public void addExistingUser() throws DAOException {

        testUser.setUserId(10);
        testUser.setEmail("qwert@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375447754924");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(20);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        Integer actualUserId = userDAO.add(testUser);
        assertNull(actualUserId);
    }

    @Test
    public void addNotExistingUserWithWrongData() {

        testUser.setEmail("mnbvc@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375447754990");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(-50);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        try {
            userDAO.add(testUser);
        } catch (DAOException e) {
            assertThat(e.getMessage(), containsString("Out of range value"));
        }
    }

    @Test
    public void addNotExistingUserWithSomeNULLData() throws DAOException{

        testUser.setEmail("xedrtfr@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Kovalets");
        testUser.setTelNumber("+375447755933");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(10);
        testUser.setGenderMale(null);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);

        thrownException.expect(NullPointerException.class);
        userDAO.add(testUser);
    }

    @Test
    public void addNotExistingUserWithNULLData() throws DAOException{

        thrownException.expect(NullPointerException.class);
        userDAO.add(testUser);
    }

    @Test
    public void updateExistingUser() throws DAOException {

        testUser.setEmail("zaqwsx@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Burachenko");
        testUser.setTelNumber("+375447754990");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(10);
        testUser.setGenderMale(false);
        testUser.setBlocked(false);
        testUser.setRole(UserRole.CUSTOMER);
        testUser.setUserId(12);

        boolean actual = userDAO.update(testUser);
        assertTrue(actual);
    }

    @Test
    public void updateNotExistingUser() throws DAOException {

        testUser.setEmail("zaqwsx@gmail.com");
        testUser.setPassword(convertToHash("3105199431z"));
        testUser.setName("Aliaksandra");
        testUser.setSurname("Burachenko");
        testUser.setTelNumber("+375447754990");
        testUser.setBirthday(new LocalDate(1994, 5, 31));
        testUser.setDiscount(10);
        testUser.setGenderMale(false);
        testUser.setBlocked(true);
        testUser.setRole(UserRole.CUSTOMER);
        testUser.setUserId(55);

        boolean actual = userDAO.update(testUser);
        assertFalse(actual);
    }

    @Test
    public void deleteExistingUser() throws DAOException {
        Integer userId = 14;
        boolean actualResult = userDAO.delete(userId);
        assertTrue(actualResult);
    }

    @Test
    public void deleteNotExistingUser() throws DAOException {
        Integer userId = 55;
        boolean actualResult = userDAO.delete(userId);
        assertFalse(actualResult);
    }

    @DataProvider
    public static Object[][] provideUsersId(){
        return new Object[][] {{1, true} , {55, false}, {13, true}, {6, false}};
    }

    @Test
    @UseDataProvider("provideUsersId")
    public void getById(Integer userId, boolean expected) throws DAOException{
        testUser = userDAO.getById(userId);
        boolean userExists = testUser != null;
        assertEquals(expected, userExists);
    }

    @DataProvider
    public static Object[][] provideUsersEmail(){
        return new Object[][] {{"qwert@gmail.com", true}, {"zzzz@mail.ru", false}};
    }

    @Test
    @UseDataProvider("provideUsersEmail")
    public void getCustomerByEmail(String email, boolean expected) throws DAOException {
        testUser = userDAO.getUserByEmail(email);
        boolean userExists = testUser != null;
        assertEquals(expected, userExists);
    }

    @Test
    public void getElementsList() throws DAOException {

        final int expected = 7;
        int actual = userDAO.getElementsList().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getLimitedElementsList() throws DAOException {

        final int expected = 4;
        int actual = userDAO.getElementsList(1, 4).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getTotalCountOfUsers() throws DAOException {
        final int expectedCount = userDAO.getElementsList().size();
        int actualCount = userDAO.getTotalCountOfUsers();
        assertEquals(expectedCount, actualCount);
    }

}
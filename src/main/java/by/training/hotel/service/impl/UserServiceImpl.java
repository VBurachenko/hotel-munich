package by.training.hotel.service.impl;

import by.training.hotel.dao.DAOFactory;
import by.training.hotel.dao.UserDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.User;
import by.training.hotel.entity.UserRole;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;
import by.training.hotel.service.util.PageCountDeterminant;
import by.training.hotel.service.validation.CommonValidator;
import by.training.hotel.service.validation.UserValidator;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final DAOFactory factory = DAOFactory.getInstance();

    private final UserDAO<User, Integer> userDAO = factory.getUserDao();

    @Override
    public boolean updateCustomer(User user) throws ServiceException {
        if (!UserValidator.validateUpdatingUser(user)){
            return false;
        }

        try {
            return userDAO.update(user);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteCustomer(int userId) throws ServiceException {
        if (!CommonValidator.validateIntegerId(userId)){
            return false;
        }
        try {
            return userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(int userId) throws ServiceException{
        if (!CommonValidator.validateIntegerId(userId)){
            return null;
        }
        try {
            return userDAO.getById(userId);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        if (!CommonValidator.validateEmail(email)){
            return null;
        }
        try {
            return userDAO.getUserByEmail(email);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getPartUsers(int start, int offset) throws ServiceException {
        try{
            return userDAO.getElementsList(start, offset);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public User authorizeUser(String email, String password) throws ServiceException{
        if (!UserValidator.validateIncomingUser(email, password)){
            return null;
        }
        User currentUser;
        try {
            currentUser = userDAO.getUserByEmail(email);
            if (currentUser != null
                    && currentUser.getPassword().equals(password)){
                return currentUser;
            } else {
                return null;
            }
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer registerNewCustomer(String email, String passwordFirst, String passwordSecond,
                                       String name, String surname, String telNumber,
                                       String birthday, String genderMale) throws ServiceException{
        try {
            if (passwordFirst.equals(passwordSecond)){

                User registeringCustomer = new User();

                registeringCustomer.setEmail(email);
                registeringCustomer.setPassword(passwordFirst);
                registeringCustomer.setName(name);
                registeringCustomer.setSurname(surname);
                registeringCustomer.setTelNumber(telNumber);
                registeringCustomer.setBirthday(new LocalDate(birthday));
                registeringCustomer.setDiscount(0);
                registeringCustomer.setGenderMale(Boolean.valueOf(genderMale));

                if (!UserValidator.validateAddingUser(registeringCustomer)){
                    return null;
                }

                return userDAO.add(registeringCustomer);
            } else {
                return null;
            }
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public synchronized CommonDTO<User> getUsersForView(int pageNumber, int itemsPerPage) throws ServiceException{

        if (pageNumber <= 0){
            return null;
        }

        CommonDTO<User> usersForView = new CommonDTO<>();

        int start = (pageNumber - 1) * itemsPerPage;

        try {
            List<User> usersList = userDAO.getElementsList(start, itemsPerPage);
            int userCount = userDAO.getTotalCountOfElements();
            int pageCount = PageCountDeterminant.definePageCount(userCount, itemsPerPage);

            usersForView.setEntityList(usersList);
            usersForView.setPagesCount(pageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return usersForView;
    }

    @Override
    public boolean changeBlockUser(String strUserId, String strBlockDown) throws ServiceException{

        boolean userBlocked = false;

        Integer userId = Integer.valueOf(strUserId);
        if (!CommonValidator.validateIntegerId(userId)){
            return false;
        }
        Boolean blockDown = Boolean.valueOf(strBlockDown);

        try {
            User userInProcess = userDAO.getById(userId);
            if (userInProcess != null){
                userInProcess.setBlocked(blockDown);
                userBlocked = userDAO.update(userInProcess);
            }
        } catch (DAOException e){
            throw new ServiceException(e);
        }

        return userBlocked;
    }

    @Override
    public CommonDTO<User> getUserByIdOrEmailOrTelephoneNumber(String searchUserArtifact) throws ServiceException{

        CommonDTO<User> userForView = null;

        try {
            User wantedUser = userDAO.getUserByIdOrEmailOrTelNumber(searchUserArtifact);
            if (wantedUser != null){
                userForView = new CommonDTO<>();
                List<User> userList = new LinkedList<>();
                userList.add(wantedUser);

                userForView.setEntityList(userList);
                userForView.setPagesCount(1);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userForView;
    }

    @Override
    public boolean registerNewAdmin(String email) throws ServiceException{

        if (!UserValidator.validateUserEmail(email)){
            return false;
        }

        try {
            User incomingAdmin = userDAO.getUserByEmail(email);
            incomingAdmin.setRole(UserRole.ADMIN);
            return userDAO.update(incomingAdmin);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}

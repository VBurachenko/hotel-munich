package by.training.hotel.service;

import by.training.hotel.entity.User;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    int addCustomer(User user) throws ServiceException;

    boolean updateCustomer(User user) throws ServiceException;

    boolean deleteCustomer(int userId) throws ServiceException;

    User getUserById(int id) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    List<User> getPartUsers(int start, int offset) throws ServiceException;

    User authorizeUser(String email, String password) throws ServiceException;

    int registerNewCustomer(String email,
                                String passwordFirst,
                                String passwordSecond,
                                String name,
                                String surname,
                                String telNumber,
                                String birthday,
                                String genderMale) throws ServiceException;

    CommonDTO<User> getUsersForView(int pageNumber, int itemsPerPage) throws ServiceException;

    boolean changeBlockUser(String strUserId, String strBlockDown) throws ServiceException;

    CommonDTO<User> getUserByEmailOrTelephoneNumber(String telNumOrEmail) throws ServiceException;
}

package by.training.hotel.service.validation;

import by.training.hotel.entity.User;

public class UserValidator {

    public static boolean validateAddingUser(User user){
        return true;
    }

    public static boolean validateUpdatingUser(User user){
        return true;
    }

    public static boolean validateIncomingUser(String email, String password){
        return true;
    }

    public static boolean validateUserEmail(String email){
        return true;
    }
}

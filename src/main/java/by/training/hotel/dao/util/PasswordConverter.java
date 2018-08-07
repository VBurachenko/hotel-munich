package by.training.hotel.dao.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordConverter {

    public static String convertToHash(String password){
        return DigestUtils.md5Hex(password);
    }
}

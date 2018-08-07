package by.training.hotel.dao.connection_pool;

import java.util.ResourceBundle;

final class ConfigProperties {

    static String DRIVER;

    static String URL;

    static String USER;

    static String PASSWORD;

    static Boolean AUTO_RECONNECT;

    static String CHAR_ENCODING;

    static Boolean USE_UNICODE;

    static Integer POOL_SIZE;

    static Boolean USE_SSL;

    private ConfigProperties(String driver, String url, String user, String password, Boolean autoReconnect,
                             String charEncoding, Boolean useUnicode, Integer poolSize, Boolean useSSL) {
        DRIVER = driver;
        URL = url;
        USER = user;
        PASSWORD = password;
        AUTO_RECONNECT = autoReconnect;
        CHAR_ENCODING = charEncoding;
        USE_UNICODE = useUnicode;
        POOL_SIZE = poolSize;
        USE_SSL = useSSL;
    }

    static void init(String configFilePath){

        ResourceBundle bundle = ResourceBundle.getBundle(configFilePath);

        String driver = bundle.getString(Parameter.DB_DRIVER);

        String url = bundle.getString(Parameter.DB_URL);

        String user = bundle.getString(Parameter.DB_USER);

        String password = bundle.getString(Parameter.DB_PASSWORD);

        Boolean autoReconnect = Boolean.valueOf(bundle.getString(Parameter.DB_AUTO_RECONNECT));

        String charEncoding = bundle.getString(Parameter.DB_CHAR_ENCODING);

        Boolean useUnicode = Boolean.valueOf(bundle.getString(Parameter.DB_USE_UNICODE));

        Integer poolSize = Integer.valueOf(bundle.getString(Parameter.DB_POOL_SIZE));

        Boolean useSSL = Boolean.valueOf(bundle.getString(Parameter.DB_USE_SSL));

        new ConfigProperties(driver, url, user, password, autoReconnect, charEncoding, useUnicode, poolSize, useSSL);
    }
}
package by.training.hotel.controller.tag;

import java.util.ResourceBundle;

final class ConfigFooterInfo {

    static String AUTHOR_COPYRIGHT;

    static String EMAIL_SIGN;

    static String CONTACT_EMAIL;

    static String TELEPHONE_SIGN;

    static String CONTACT_TELEPHONE;

    static String COMPANY_NAME;

    static String COMPANY_TYPE;

    private ConfigFooterInfo(String authorCopyright, String emailSign, String contactEmail,
                             String telephoneSign, String contactTelephone, String companyName,
                             String companyType){
        AUTHOR_COPYRIGHT = authorCopyright;
        EMAIL_SIGN = emailSign;
        CONTACT_EMAIL = contactEmail;
        TELEPHONE_SIGN = telephoneSign;
        CONTACT_TELEPHONE = contactTelephone;
        COMPANY_NAME = companyName;
        COMPANY_TYPE = companyType;
    }

    static void init(String configFilePath){
        ResourceBundle bundle = ResourceBundle.getBundle(configFilePath);

        String authorCopyright = bundle.getString("authorCopyright");

        String emailSign = bundle.getString("emailSign");

        String contactEmail = bundle.getString("contactEmail");

        String telephoneSign = bundle.getString("telephoneSign");

        String contactTelephone = bundle.getString("contactTelephone");

        String companyName = bundle.getString("companyName");

        String companyType = bundle.getString("companyType");

        new ConfigFooterInfo(authorCopyright, emailSign, contactEmail, telephoneSign, contactTelephone, companyName, companyType);
    }


}

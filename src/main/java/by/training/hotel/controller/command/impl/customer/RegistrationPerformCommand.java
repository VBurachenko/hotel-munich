package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.dao.util.PasswordConverter;
import by.training.hotel.service.UserService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationPerformCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter(ParameterName.EMAIL);

        String passwordFirst = request.getParameter(ParameterName.PASSWORD);
        String encryptedPwdFirst = PasswordConverter.convertToHash(passwordFirst);

        String passwordSecond = request.getParameter(ParameterName.PASSWORD_SECOND);
        String encryptedPwdSecond = PasswordConverter.convertToHash(passwordSecond);

        String name = request.getParameter(ParameterName.FIRST_NAME);
        String surname = request.getParameter(ParameterName.LAST_NAME);
        String telNumber = request.getParameter(ParameterName.TEL_NUMBER);
        String birthday = request.getParameter(ParameterName.BIRTHDAY);
        String genderMale = request.getParameter(ParameterName.GENDER_MALE);

        UserService userService = serviceFactory.getUserService();

        Integer registeredCustomerId = null;

        try {

            registeredCustomerId = userService.registerNewCustomer(email,
                    encryptedPwdFirst,
                    encryptedPwdSecond,
                    name,
                    surname,
                    telNumber,
                    birthday,
                    genderMale);

        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        HttpSession session = request.getSession();

        String urlPattern;
        if (registeredCustomerId != null) {
            session.removeAttribute(ParameterName.OPERATION_MESSAGE);
            urlPattern = UrlPattern.SUCCESSFULLY_REGISTER;
        } else {
            session.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.REGISTERED_ALREADY_OR_ERROR_CODE);
            urlPattern = UrlPattern.REGISTER;
        }
        response.sendRedirect(request.getContextPath() + urlPattern);
    }
}

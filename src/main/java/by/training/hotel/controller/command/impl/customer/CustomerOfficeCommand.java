package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Invoice;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class CustomerOfficeCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Integer currentUserId = (Integer) session.getAttribute(ParameterName.USER_ID);

        BookingService bookingService = serviceFactory.getBookingService();
        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        Set<Booking> usersBookingSet = null;
        Set<Invoice> usersInvoiceSet = null;
        try {
            usersBookingSet = bookingService.getBookingsSetForUser(currentUserId);
            usersInvoiceSet = invoiceService.getInvoicesSetByUserId(currentUserId);
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        request.setAttribute(ParameterName.USERS_BOOKING_SET, usersBookingSet);
        request.setAttribute(ParameterName.USERS_INVOICE_SET, usersInvoiceSet);

        request.getRequestDispatcher(PageEnum.CUSTOMER_OFFICE.getPath()).forward(request, response);
    }
}

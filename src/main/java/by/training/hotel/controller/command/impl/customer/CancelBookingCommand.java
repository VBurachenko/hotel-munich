package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelBookingCommand extends Command {
    
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String bookingId = request.getParameter(ParameterName.CANCEL_BOOKING_ID);
        String invoiceId = request.getParameter(ParameterName.CANCEL_INVOICE_ID);
        
        boolean bookingCanceled = false;
        boolean invoiceCanceled = false;
        try {
            BookingService bookingService = serviceFactory.getBookingService();
            InvoiceService invoiceService = serviceFactory.getInvoiceService();

            bookingCanceled = bookingService.cancelBooking(bookingId);
            invoiceCanceled = invoiceService.cancelInvoice(invoiceId);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (bookingCanceled && invoiceCanceled){
            response.sendRedirect(request.getContextPath() + UrlPattern.OPEN_USER_OFFICE);
        } else {
            response.sendRedirect(request.getContextPath() + UrlPattern.HOME);
        }
    }
}

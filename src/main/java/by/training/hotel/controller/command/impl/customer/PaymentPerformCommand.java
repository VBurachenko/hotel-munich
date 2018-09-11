package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Invoice;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaymentPerformCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strTotalAmount = request.getParameter(ParameterName.TOTAL_AMOUNT);
        double totalAmount = Double.valueOf(strTotalAmount);
        String strInvoiceIdForPayment = request.getParameter(ParameterName.INVOICE_ID_FOR_PAYMENT);
        long invoiceIdForPayment = Long.parseLong(strInvoiceIdForPayment);

        InvoiceService invoiceService = serviceFactory.getInvoiceService();
        BookingService bookingService = serviceFactory.getBookingService();

        boolean paymentPerformed = false;
        boolean bookingConfirmed = false;

        try{
            Invoice invoiceForPayment = invoiceService.getInvoiceById(invoiceIdForPayment);
            if (invoiceForPayment.getTotalPayment().equals(totalAmount)){

                paymentPerformed = invoiceService.payInvoiceInstantly(invoiceForPayment);

                Long bookingId = bookingService.getBookingIdByInvoiceId(invoiceForPayment.getInvoiceId());
                bookingConfirmed = bookingService.confirmBooking(bookingId);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
        if (paymentPerformed && bookingConfirmed){
            response.sendRedirect(request.getContextPath() + UrlPattern.SUCCESS_PAYMENT);
        } else {
            System.out.println("error payment");
        }
    }
}

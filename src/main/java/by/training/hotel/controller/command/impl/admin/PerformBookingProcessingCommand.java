package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
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

public class PerformBookingProcessingCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String strBookingStatus = request.getParameter(ParameterName.BOOKING_STATUS);
        String strInvoiceIsPayed = request.getParameter(ParameterName.IS_PAYED);

        Booking bookingInProcess = (Booking) session.getAttribute(ParameterName.BOOKING_IN_PROCESS);
        Invoice invoiceForBooking = (Invoice) session.getAttribute(ParameterName.INVOICE_FOR_BOOKING);

        BookingService bookingService = serviceFactory.getBookingService();
        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        boolean bookingProcessed = false;
        boolean invoiceProcessed = false;

        try {
            if (bookingInProcess != null && invoiceForBooking != null){
                bookingProcessed = bookingService.changeBookingStatus(bookingInProcess, strBookingStatus);
                invoiceProcessed = invoiceService.registerInvoicePayment(invoiceForBooking, strInvoiceIsPayed);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        if (bookingProcessed || invoiceProcessed){
            response.sendRedirect(UrlPattern.SUCCESS_BOOKING_PROCESSING);
        } else {

            session.removeAttribute(ParameterName.BOOKING_IN_PROCESS);
            session.removeAttribute(ParameterName.INVOICE_FOR_BOOKING);

            request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.BOOKING_NOT_PROCESSED_CODE);
            request.getRequestDispatcher(PageEnum.BOOKING_LIST.getPath()).forward(request, response);
        }

    }
}

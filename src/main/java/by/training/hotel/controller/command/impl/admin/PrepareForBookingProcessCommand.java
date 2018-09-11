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

public class PrepareForBookingProcessCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String strBookingId = request.getParameter(ParameterName.BOOKING_ID);
        String strInvoiceId = request.getParameter(ParameterName.INVOICE_ID);

        Booking bookingInProcess = null;
        Invoice invoiceForBooking = null;

        BookingService bookingService = serviceFactory.getBookingService();
        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        try {
            if (strBookingId == null){
                strBookingId = bookingService.getBookingIdByInvoiceId(strInvoiceId).toString();
            }
            bookingInProcess = bookingService.getBookingByBookingId(strBookingId);
            if (bookingInProcess != null){
                invoiceForBooking = invoiceService.getInvoiceById(bookingInProcess.getInvoiceId());
                if (invoiceForBooking != null){
                    session.setAttribute(ParameterName.BOOKING_IN_PROCESS, bookingInProcess);
                    session.setAttribute(ParameterName.INVOICE_FOR_BOOKING, invoiceForBooking);

                    response.sendRedirect(UrlPattern.OPEN_BOOKING_PROCESSING_FORM);
                }
            } else {
                request.setAttribute(ParameterName.OPERATION_MESSAGE, ParameterName.IMPOSSIBLE_PROCESS_BOOKING_MESSAGE_CODE);
                request.getRequestDispatcher(PageEnum.BOOKING_LIST.getPath()).forward(request, response);
            }
        } catch (ServiceException e){
            LOGGER.error(e);
        }
    }
}

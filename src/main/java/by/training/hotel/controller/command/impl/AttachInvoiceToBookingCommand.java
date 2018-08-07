package by.training.hotel.controller.command.impl;

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

public class AttachInvoiceToBookingCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Long preparedBookingId = (Long) session.getAttribute(ParameterName.BOOKING_ID);

        BookingService bookingService = serviceFactory.getBookingService();
        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        Booking bookingInProcess;
        Invoice invoiceForBooking;

        try {
            bookingInProcess = bookingService.getBookingByBookingId(preparedBookingId.toString());
            if (bookingInProcess != null) {
                invoiceForBooking = invoiceService.attachInvoiceToBooking(bookingInProcess);
                if (invoiceForBooking != null){
                    bookingService.updateBooking(bookingInProcess);
                    session.setAttribute(ParameterName.BOOKING_IN_PROCESS, bookingInProcess);
                    session.setAttribute(ParameterName.INVOICE_FOR_BOOKING, invoiceForBooking);

                    response.sendRedirect(UrlPattern.COMPLETE_BOOKING);
                }
            } else {
                request.getRequestDispatcher(PageEnum.BOOKING_IMPOSSIBLE.getPath()).forward(request, response);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
    }
}
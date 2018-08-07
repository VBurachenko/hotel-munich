package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.InvoiceStatus;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DecideOfPaymentIssueCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String payment = request.getParameter(ParameterName.PAYMENT).toUpperCase();
        String strInvoiceId = request.getParameter(ParameterName.INVOICE_ID_FOR_PAYMENT);
        long invoiceId = Long.parseLong(strInvoiceId);

        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        InvoiceStatus payInHotelStatus = InvoiceStatus.PAY_IN_HOTEL;

        boolean invoiceStatusDefined = false;

        try {
            if (InvoiceStatus.valueOf(payment).equals(payInHotelStatus)) {
                Invoice invoiceForPayment = invoiceService.getInvoiceById(invoiceId);
                if (invoiceForPayment != null){
                    invoiceForPayment.setInvoiceStatus(payInHotelStatus);
                    invoiceStatusDefined = invoiceService.updateInvoice(invoiceForPayment);
                }
            } else {
                session.setAttribute(ParameterName.INVOICE_ID_FOR_PAYMENT, invoiceId);
                response.sendRedirect(UrlPattern.PAY_INVOICE);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
        if (invoiceStatusDefined){
            response.sendRedirect(UrlPattern.SUCCESS_PAYMENT);
        } else {
            System.out.println("2Booking can not be finalized");
        }

    }
}

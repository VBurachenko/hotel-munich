package by.training.hotel.controller.command.impl.customer;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Invoice;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PayInvoiceCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Long invoiceIdForPayment;

        invoiceIdForPayment = (Long) session.getAttribute(ParameterName.INVOICE_ID_FOR_PAYMENT);
        if (invoiceIdForPayment == null){
            String strInvoiceIdForPayment = request.getParameter(ParameterName.INVOICE_ID_FOR_PAYMENT);
            invoiceIdForPayment = Long.valueOf(strInvoiceIdForPayment);
        }

        Invoice invoiceForPayment = null;

        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        try {
            invoiceForPayment = invoiceService.getInvoiceById(invoiceIdForPayment);
        } catch (ServiceException e){
            LOGGER.error(e);
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }
        session.setAttribute(ParameterName.INVOICE_FOR_PAYMENT, invoiceForPayment);
        request.getRequestDispatcher(PageEnum.INVOICE_PAYMENT.getPath()).forward(request,response);
    }
}

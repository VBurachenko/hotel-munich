package by.training.hotel.controller.command.impl.admin;

import by.training.hotel.controller.command.Command;
import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.PageEnum;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProvideInvoiceForViewCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strInvoiceId = request.getParameter(ParameterName.INVOICE_ID);

        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        CommonDTO<Invoice> invoiceForView = null;

        try {
            invoiceForView = invoiceService.getOneInvoiceForView(strInvoiceId);
        } catch (ServiceException e){
            LOGGER.error(e);
        }

        if (invoiceForView != null){
            request.setAttribute(ParameterName.INVOICES_FOR_VIEW, invoiceForView);
        } else {
            request.setAttribute(ParameterName.INVOICE_OPERATION_MESSAGE, ParameterName.NO_SUCH_INVOICE_MESSAGE_CODE);
        }
        request.getRequestDispatcher(PageEnum.INVOICE_LIST.getPath()).forward(request, response);
    }
}

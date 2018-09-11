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

public class ListInvoicesViewCommand implements Command {

    private final static int ITEMS_PER_PAGE = 10;

    private final static int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String strPage = request.getParameter(ParameterName.PAGE);
        int pageNumber;

        if (strPage!= null && !strPage.isEmpty()){
            pageNumber = Integer.valueOf(strPage);
        } else {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        CommonDTO<Invoice> invoicesForView = null;
        InvoiceService invoiceService = serviceFactory.getInvoiceService();

        try {
            invoicesForView = invoiceService.getInvoicesForView(pageNumber, ITEMS_PER_PAGE);
        } catch (ServiceException e){
            LOGGER.error(e);
            e.printStackTrace();
            request.getRequestDispatcher(PageEnum.ERROR_PAGE.getPath()).forward(request, response);
        }

        request.setAttribute(ParameterName.INVOICES_FOR_VIEW, invoicesForView);
        request.setAttribute(ParameterName.PAGE, pageNumber);

        request.getRequestDispatcher(PageEnum.INVOICE_LIST.getPath()).forward(request, response);
    }
}

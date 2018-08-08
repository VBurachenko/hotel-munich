package by.training.hotel.service;

import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.exception.ServiceException;

import java.util.Set;

public interface InvoiceService {

    Set<Invoice> getInvoicesSetByUserId(Integer userId) throws ServiceException;

    Invoice prepareInvoice(Booking booking) throws ServiceException;

    Long addInvoice(Invoice invoice) throws ServiceException;

    boolean updateInvoice(Invoice invoice) throws ServiceException;

    Invoice getInvoiceById(Long invoiceId)throws ServiceException;

    void clearUnspecifiedInvoices(Integer userId) throws ServiceException;

    Invoice attachInvoiceToBooking(Booking bookingInProcess) throws ServiceException;

    boolean payInvoiceInstantly(Invoice invoiceForBooking) throws ServiceException;

    boolean cancelInvoice(String strInvoiceId) throws ServiceException;

    boolean registerInvoicePayment(Invoice invoiceInProcess, String strIsPayed) throws ServiceException;

    CommonDTO<Invoice> getInvoicesForView(int pageNumber, int itemsPerPage) throws ServiceException;

    CommonDTO<Invoice> getOneInvoiceForView(String strInvoiceId) throws ServiceException;
}

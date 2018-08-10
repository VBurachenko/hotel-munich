package by.training.hotel.service.impl;

import by.training.hotel.dao.DAOFactory;
import by.training.hotel.dao.InvoiceDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Booking;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.InvoiceStatus;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.CommonDTO;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.exception.ServiceException;
import by.training.hotel.service.util.DatesCalculator;
import by.training.hotel.service.util.PageCountDeterminant;
import by.training.hotel.service.validation.BookingValidator;
import by.training.hotel.service.validation.CommonValidator;
import by.training.hotel.service.validation.InvoiceValidator;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InvoiceServiceImpl implements InvoiceService {

    private final DAOFactory factory = DAOFactory.getInstance();

    private final InvoiceDAO<Long, Invoice> invoiceDAO = factory.getInvoiceDao();

    @Override
    public Set<Invoice> getInvoicesSetByUserId(Integer userId) throws ServiceException {
        try {
            return invoiceDAO.getInvoicesSetByUserId(userId);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Invoice prepareInvoice(Booking booking) {

        Invoice preparedInvoice = new Invoice();

        if (booking != null){
            preparedInvoice.setUserId(booking.getUserId());

            LocalDate invoiceDate = new LocalDate();
            preparedInvoice.setInvoiceDate(invoiceDate);

            int nightsCount = DatesCalculator.nightsCountCalculate(booking.getCheckInDate(), booking.getCheckOutDate());
            preparedInvoice.setNightsCount(nightsCount);

            double totalPayment = calculateTotalPayment(nightsCount, booking.getRoomsSet());
            preparedInvoice.setTotalPayment(totalPayment);

            preparedInvoice.setInvoiceStatus(InvoiceStatus.INVOICED);

            preparedInvoice.setIsPayed(false);

        }
        return preparedInvoice;
    }

    @Override
    public Long addInvoice(Invoice invoice) throws ServiceException{
        if (!InvoiceValidator.validateAddingInvoice(invoice)){
            return null;
        }
        try {
            return invoiceDAO.add(invoice);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateInvoice(Invoice invoice) throws ServiceException{
        if (!InvoiceValidator.validateUpdatingInvoice(invoice)){
            return false;
        }
        try {
            return invoiceDAO.update(invoice);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Invoice getInvoiceById(Long invoiceId)throws ServiceException{
        if (!CommonValidator.validateLongId(invoiceId)){
            return null;
        }
        try{
            return invoiceDAO.getById(invoiceId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void clearUnspecifiedInvoices(Integer userId) throws ServiceException{
        if (!CommonValidator.validateIntegerId(userId)){
            return;
        }
        try{
            invoiceDAO.clearUnspecifiedInvoices(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Invoice attachInvoiceToBooking(Booking bookingInProcess) throws ServiceException{

        Invoice invoiceForBooking;

        invoiceForBooking = prepareInvoice(bookingInProcess);

        if (!InvoiceValidator.validateAddingInvoice(invoiceForBooking) &&
                !BookingValidator.validateUpdatingBooking(bookingInProcess)){
            return null;
        }

        Long invoiceId;

        if (bookingInProcess.getInvoiceId() == null ||
                bookingInProcess.getInvoiceId().equals(0L)){

            invoiceId = addInvoice(invoiceForBooking);
            bookingInProcess.setInvoiceId(invoiceId);
        } else {
            invoiceId = bookingInProcess.getInvoiceId();
            invoiceForBooking.setInvoiceId(invoiceId);
            updateInvoice(invoiceForBooking);
        }

        return getInvoiceById(invoiceId);
    }

    @Override
    public boolean payInvoiceInstantly(Invoice invoiceForBooking) throws ServiceException{
        if (!InvoiceValidator.validateUpdatingInvoice(invoiceForBooking)){
            return false;
        }
        try{
            invoiceForBooking.setInvoiceStatus(InvoiceStatus.INSTANT_PAY);
            invoiceForBooking.setIsPayed(true);
            return invoiceDAO.update(invoiceForBooking);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean cancelInvoice(String strInvoiceId) throws ServiceException{
        Long invoiceId = Long.valueOf(strInvoiceId);
        if (!CommonValidator.validateLongId(invoiceId)){
            return false;
        }
        try {
            return invoiceDAO.delete(invoiceId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registerInvoicePayment(Invoice invoiceInProcess, String strIsPayed) throws ServiceException {
        Boolean isPayed;
        if (strIsPayed != null){
            isPayed = Boolean.parseBoolean(strIsPayed);
        } else {
            return false;
        }
        if (!InvoiceValidator.validateUpdatingInvoice(invoiceInProcess)){
            return false;
        }
        try {
            invoiceInProcess.setIsPayed(isPayed);
            return invoiceDAO.update(invoiceInProcess);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public synchronized CommonDTO<Invoice> getInvoicesForView(int pageNumber, int itemsPerPage) throws ServiceException{
        if (pageNumber <= 0){
            return null;
        }
        CommonDTO<Invoice> invoicesForView = new CommonDTO<>();

        int start = (pageNumber - 1) * itemsPerPage;

        try {
            List<Invoice> invoiceList = invoiceDAO.getElementsList(start, itemsPerPage);
            int invoiceCount = invoiceDAO.getTotalCountOfInvoices();
            int pageCount = PageCountDeterminant.definePageCount(invoiceCount, itemsPerPage);

            invoicesForView.setEntityList(invoiceList);
            invoicesForView.setPagesCount(pageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return invoicesForView;
    }

    @Override
    public synchronized CommonDTO<Invoice> getOneInvoiceForView(String strInvoiceId) throws ServiceException{
        Long invoiceId;
        CommonDTO<Invoice> invoiceForView = null;

        try {
            invoiceId = Long.valueOf(strInvoiceId);
        } catch (NumberFormatException e){
            return null;
        }

        if (!CommonValidator.validateLongId(invoiceId)){
            return null;
        }

        try {
            Invoice wantedInvoice = invoiceDAO.getById(invoiceId);
            if (wantedInvoice != null){
                invoiceForView = new CommonDTO<>();
                List<Invoice> invoicesList = new LinkedList<>();
                invoicesList.add(wantedInvoice);

                invoiceForView.setEntityList(invoicesList);
                invoiceForView.setPagesCount(1);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return invoiceForView;
    }


    private Double calculateTotalPayment(int nightsCount, Set<Room> roomsInBooking){

        double commonDailyCost = 0.0;

        for (Room currentRoom : roomsInBooking){
            commonDailyCost += currentRoom.getPricePerNight();
        }
        return commonDailyCost * nightsCount;
    }
}

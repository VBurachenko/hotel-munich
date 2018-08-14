package by.training.hotel.dao.impl;

import by.training.hotel.dao.InvoiceDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Invoice;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class InvoiceDAOImplTest extends BaseDAOTest{

    private final InvoiceDAO<Invoice, Long> invoiceDAO = factory.getInvoiceDao();

    private Invoice testInvoice;

    @Before
    public void initTestingObject(){
        testInvoice = new Invoice();
    }

    @After
    public void cleanTestingObject(){
        testInvoice = null;
    }

    @Test
    public void addNotExistingInvoice() throws DAOException {
        testInvoice.setUserId(10);
        testInvoice.setNightsCount(20);
        testInvoice.setTotalPayment(800.0);
        Long actualInvoiceId = invoiceDAO.add(testInvoice);
        Long expectedInvoiceId = 23L;
        assertEquals(expectedInvoiceId, actualInvoiceId);
    }

    @Test
    public void addExistingInvoice() {

    }

    @Test
    public void addNewInvoiceWithNullData() {

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @DataProvider
    public static Object[][] provideInvoiceId(){

        return new Object[][]{{Long.valueOf("21"), true}, {56L, false}, {20L, true}, {44L, false}, {22L, true}};
    }


    @Test
    @UseDataProvider("provideInvoiceId")
    public void getById(Long invoiceId, boolean expectedExisting) throws DAOException {
        testInvoice = invoiceDAO.getById(invoiceId);
        System.out.println(testInvoice);
        boolean actualExisting = testInvoice != null;
        assertEquals(expectedExisting, actualExisting);
    }

    @Test
    public void getElementsList() {
    }

    @Test
    public void getElementsList1() {
    }

    @Test
    public void getInvoicesSetByUserId() {
    }

    @Test
    public void addInvoice() {
    }

    @Test
    public void clearUnspecifiedInvoices() {
    }
}
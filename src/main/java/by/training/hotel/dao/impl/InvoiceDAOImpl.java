package by.training.hotel.dao.impl;

import by.training.hotel.dao.AbstractDAO;
import by.training.hotel.dao.InvoiceDAO;
import by.training.hotel.dao.connection_pool.ConnectionPool;
import by.training.hotel.dao.connection_pool.ProxyConnection;
import by.training.hotel.dao.connection_pool.exception.PoolException;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.dao.util.EntityParameter;
import by.training.hotel.entity.Invoice;
import by.training.hotel.entity.InvoiceStatus;
import org.joda.time.LocalDate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.training.hotel.dao.util.DataTypeConverter.localDateToSqlDate;
import static by.training.hotel.dao.util.DataTypeConverter.sqlToLocalDate;

public class InvoiceDAOImpl extends AbstractDAO implements InvoiceDAO<Long, Invoice> {

    private final static String ADD_INVOICE_QUERY =
            "INSERT INTO invoice (" +
                    "invoice.user_id," +
                    "invoice.invoice_date, " +
                    "invoice.nights_count, " +
                    "invoice.total_payment) " +
                    "VALUES (?, CURRENT_DATE , ?, ?)";

    private final static String UPDATE_INVOICE_QUERY =
            "UPDATE invoice " +
                    "SET " +
                    "invoice.user_id = ?, " +
                    "invoice.invoice_date = ?, " +
                    "invoice.nights_count = ?, " +
                    "invoice.total_payment = ?, " +
                    "invoice.invoice_status = ?," +
                    "invoice.payed = ? ";

    private final static String GET_INVOICES =
            "SELECT * FROM invoice ";

    private final static String JOIN_FOR_LIMIT =
            "JOIN (SELECT invoice.invoice_id FROM invoice ORDER BY invoice.invoice_id LIMIT ?, ?) " +
                    "AS i ON i.invoice_id = invoice.invoice_id";

    private final static String DELETE_INVOICE =
            "DELETE FROM invoice ";

    private final static String LAST_ADDED_INVOICE_ID = "SELECT LAST_INSERT_ID()";

    private final static String AND_INVOICE_STATUS = " AND invoice.invoice_status = \'invoiced\'";

    private final static String WHERE_INVOICE_ID = " WHERE invoice.invoice_id = ?";

    private final static String WHERE_USER_ID = "WHERE invoice.user_id = ?";

    private static final String GET_TOTAL_COUNT_OF_INVOICES = "SELECT COUNT(*) FROM invoice ";

    public InvoiceDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Long add(Invoice invoice) throws DAOException {

        Long invoiceId = null;

        ProxyConnection proxyConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            proxyConnection = pool.takeConnection();
            proxyConnection.setAutoCommit(false);

            statement = proxyConnection.prepareStatement(ADD_INVOICE_QUERY);

            prepareForAdd(statement, invoice);

            statement.execute();

            statement = proxyConnection.prepareStatement(LAST_ADDED_INVOICE_ID);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                invoiceId = resultSet.getLong(1);
            }

            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);

        } catch (PoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }
            throw new DAOException(e);
        } finally {
            pool.closeConnection(proxyConnection, resultSet, statement);
        }

        return invoiceId;
    }

    @Override
    public boolean update(Invoice invoice) throws DAOException {

        boolean updatedSuccessfully = false;

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(UPDATE_INVOICE_QUERY + WHERE_INVOICE_ID);

            prepareForUpdate(statement, invoice);

            if (statement.executeUpdate() == 1) {
                updatedSuccessfully = true;
            }

        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
        return updatedSuccessfully;
    }

    @Override
    public boolean delete(Long id) throws DAOException {

        boolean deletedSuccessfully = false;

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(DELETE_INVOICE + WHERE_INVOICE_ID);

            statement.setLong(1, id);

            if (statement.executeUpdate() == 1) {
                deletedSuccessfully = true;
            }

        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
        return deletedSuccessfully;
    }

    @Override
    public Invoice getById(Long id) throws DAOException {

        Invoice wantedInvoice = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            statement = proxyConnection.prepareStatement(GET_INVOICES + WHERE_INVOICE_ID);

            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                wantedInvoice = getFromResultSet(resultSet);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return wantedInvoice;
    }

    @Override
    public List<Invoice> getElementsList(int start, int offset) throws DAOException {

        List<Invoice> allInvoicesList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            if (start == 0 && offset == 0) {
                statement = proxyConnection.prepareStatement(GET_INVOICES);
            } else {
                statement = proxyConnection.prepareStatement(GET_INVOICES + JOIN_FOR_LIMIT);

                statement.setInt(1, start);
                statement.setInt(2, offset);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Invoice invoice = getFromResultSet(resultSet);
                allInvoicesList.add(invoice);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return allInvoicesList;
    }

    @Override
    public List<Invoice> getElementsList() throws DAOException {
        return getElementsList(0, 0);
    }

    @Override
    public Set<Invoice> getInvoicesSetByUserId(Integer userId) throws DAOException {

        Set<Invoice> invoicesOfUser = new HashSet<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {

            statement = proxyConnection.prepareStatement(GET_INVOICES + WHERE_USER_ID);

            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Invoice currentInvoice = getFromResultSet(resultSet);
                invoicesOfUser.add(currentInvoice);
            }
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return invoicesOfUser;
    }

    @Override
    public void clearUnspecifiedInvoices(Integer userId) throws DAOException {

        PreparedStatement statement = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(DELETE_INVOICE + WHERE_USER_ID + AND_INVOICE_STATUS);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(statement);
        }
    }

    @Override
    public int getTotalCountOfInvoices() throws DAOException {

        int totalCountOfInvoices;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (ProxyConnection proxyConnection = pool.takeConnection()) {
            statement = proxyConnection.prepareStatement(GET_TOTAL_COUNT_OF_INVOICES);
            resultSet = statement.executeQuery();
            resultSet.next();
            totalCountOfInvoices = resultSet.getInt(1);
        } catch (SQLException | PoolException e) {
            throw new DAOException(e);
        } finally {
            pool.closeUsedResources(resultSet, statement);
        }
        return totalCountOfInvoices;
    }

    private void prepareForUpdate(PreparedStatement statement, Invoice invoice) throws SQLException {

        Integer userId = invoice.getUserId();
        statement.setInt(1, userId);

        LocalDate invoiceDate = invoice.getInvoiceDate();
        statement.setDate(2, localDateToSqlDate(invoiceDate));

        Integer nightsCount = invoice.getNightsCount();
        statement.setInt(3, nightsCount);

        Double totalPayment = invoice.getTotalPayment();
        statement.setDouble(4, totalPayment);

        String invoiceStatus = invoice.getInvoiceStatus().toString();
        statement.setString(5, invoiceStatus.toLowerCase());

        Boolean payed = invoice.getIsPayed();
        statement.setBoolean(6, payed);

        Long invoiceId = invoice.getInvoiceId();
        statement.setLong(7, invoiceId);

    }


    private void prepareForAdd(PreparedStatement statement, Invoice invoice) throws SQLException {

        Integer userId = invoice.getUserId();
        statement.setInt(1, userId);

        Integer nightsCount = invoice.getNightsCount();
        statement.setInt(2, nightsCount);

        Double totalPayment = invoice.getTotalPayment();
        statement.setDouble(3, totalPayment);

    }


    private Invoice getFromResultSet(ResultSet resultSet) throws SQLException {

        Invoice currentInvoice = new Invoice();

        Long invoiceId = resultSet.getLong(EntityParameter.INVOICE_ID);
        currentInvoice.setInvoiceId(invoiceId);

        Integer userId = resultSet.getInt(EntityParameter.USER_ID);
        currentInvoice.setUserId(userId);

        java.sql.Date invoiceDate = resultSet.getDate(EntityParameter.INVOICE_DATE);
        currentInvoice.setInvoiceDate(sqlToLocalDate(invoiceDate));

        Integer nightsCount = resultSet.getInt(EntityParameter.NIGHTS_COUNT);
        currentInvoice.setNightsCount(nightsCount);

        Double totalPayment = resultSet.getDouble(EntityParameter.TOTAL_PAYMENT);
        currentInvoice.setTotalPayment(totalPayment);

        String status = resultSet.getString(EntityParameter.INVOICE_STATUS).toUpperCase();
        currentInvoice.setInvoiceStatus(InvoiceStatus.valueOf(status));

        Boolean payed = resultSet.getBoolean(EntityParameter.PAYED);
        currentInvoice.setIsPayed(payed);

        return currentInvoice;
    }
}

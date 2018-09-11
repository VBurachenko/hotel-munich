package by.training.hotel.filter;

import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.service.BookingService;
import by.training.hotel.service.InvoiceService;
import by.training.hotel.service.ServiceFactory;
import by.training.hotel.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class IncompleteBookingFilter implements Filter {

    private final static Logger LOGGER = LogManager.getLogger();

    private final static Set<String> ALLOWED_ACTIONS = new HashSet<>();

    private final static BookingService bookingService = ServiceFactory.getInstance().getBookingService();
    private final static InvoiceService invoiceService = ServiceFactory.getInstance().getInvoiceService();

    private static Pattern PATTERN_ALLOWED_ACTION;

    @Override
    public void init(FilterConfig filterConfig) {

        ServletContext servletContext = filterConfig.getServletContext();
        String allowedActionRegex = servletContext.getInitParameter(ParameterName.ALLOWED_ACTION_REG);
        PATTERN_ALLOWED_ACTION = Pattern.compile(allowedActionRegex);

        Enumeration<String> allowedActionNames = filterConfig.getInitParameterNames();
        while (allowedActionNames.hasMoreElements()){
            String actionName = allowedActionNames.nextElement();
            ALLOWED_ACTIONS.add(filterConfig.getInitParameter(actionName));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Integer userId = (Integer) session.getAttribute(ParameterName.USER_ID);
        String action = httpRequest.getServletPath();
        if (PATTERN_ALLOWED_ACTION.matcher(action).find()){
            if (userId != null && !ALLOWED_ACTIONS.contains(action)) {
                try{
                    bookingService.clearIncompleteBookings(userId);
                    invoiceService.clearUnspecifiedInvoices(userId);
                } catch (ServiceException e){
                    LOGGER.error(e);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        PATTERN_ALLOWED_ACTION = null;
        ALLOWED_ACTIONS.clear();
    }

}

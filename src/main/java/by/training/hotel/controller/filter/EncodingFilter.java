package by.training.hotel.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String APPLICATION_ENCODING = "encoding";

    private String defaultEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultEncoding = filterConfig.getServletContext().getInitParameter(APPLICATION_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestEncoding = request.getCharacterEncoding();

        if (defaultEncoding != null && !defaultEncoding.equalsIgnoreCase(requestEncoding)){
            request.setCharacterEncoding(defaultEncoding);
            response.setCharacterEncoding(defaultEncoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        defaultEncoding = null;
    }
}

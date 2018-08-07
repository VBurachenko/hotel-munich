package by.training.hotel.filter;

import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GuestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        String role = (String) session.getAttribute(ParameterName.ROLE);

        if (role == null || role.isEmpty()){
            session.setAttribute(ParameterName.ROLE, UserRole.GUEST.toString());
        }

        chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {

    }
}

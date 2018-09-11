package by.training.hotel.filter;

import by.training.hotel.controller.command.ParameterName;
import by.training.hotel.controller.command.mapping.UrlPattern;
import by.training.hotel.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleAccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session == null){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + UrlPattern.HOME);
        } else {
            String role = (String) session.getAttribute(ParameterName.ROLE);

            if (role != null && !role.isEmpty()){
                try {
                    switch (UserRole.valueOf(role)){
                        default:
                            chain.doFilter(request, response);
                    }
                } catch (IllegalArgumentException e){
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendRedirect(httpRequest.getContextPath() + UrlPattern.HOME);
                }
            } else {
                session.setAttribute(ParameterName.ROLE, UserRole.GUEST.toString());
                chain.doFilter(request, response);
            }
        }



    }

    @Override
    public void destroy() {

    }
}

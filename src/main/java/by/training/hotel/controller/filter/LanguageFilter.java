package by.training.hotel.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.training.hotel.controller.command.ParameterName.LOCAL_LANG;

public class LanguageFilter implements Filter {

    private static final String APPLICATION_LANGUAGE = "Language";

    private String defaultLanguage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLanguage = filterConfig.getInitParameter(APPLICATION_LANGUAGE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        String localLang = (String) session.getAttribute(LOCAL_LANG);

        if (localLang == null){
            session.setAttribute(LOCAL_LANG, defaultLanguage);
        }
        chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {
        defaultLanguage = null;
    }
}

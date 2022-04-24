package fi.miolfo.rss.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    @Value("${miolfo.rss.api-key}")
    private String API_KEY;

    private static final String API_KEY_HEADER = "API-KEY";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI();

        if(path.startsWith("/api")) {

            final var apiKey = ((HttpServletRequest) servletRequest).getHeader(API_KEY_HEADER);
            if(API_KEY.equals(apiKey)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid api key");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

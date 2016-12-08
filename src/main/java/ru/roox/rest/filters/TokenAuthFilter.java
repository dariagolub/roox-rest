package ru.roox.rest.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/customerapi/*")
public class TokenAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization header should be presented");
            return;
        } else {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            String customerId;
            try {
                customerId = httpServletRequest.getServletPath().split("/")[2];
            } catch (ArrayIndexOutOfBoundsException e) {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "User identity should be presented");
                return;
            }
            if (!isTokenValid(token, customerId)) {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "User is unauthorized");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isTokenValid(String token, String customerId) {
        return token.equals(customerId);
    }

    @Override
    public void destroy() {

    }
}

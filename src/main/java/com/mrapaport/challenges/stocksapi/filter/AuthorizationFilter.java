package com.mrapaport.challenges.stocksapi.filter;

import com.mrapaport.challenges.stocksapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    public static final String API_KEY_HEADER = "X-API-KEY";

    public String[] allowedPaths = {"/signup", "/swagger.*", "/v3/api-docs.*", "/swagger-ui.html", "/webjars.*", "/swagger-resources.*"};

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (Boolean.FALSE.equals(authorizationService.isValidKey(apiKey))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The given api-key is not valid.");
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        for (String path: allowedPaths) {
            if (request.getRequestURI().matches(path)) {
                return Boolean.TRUE;
            }
        }
        return super.shouldNotFilter(request);
    }
}

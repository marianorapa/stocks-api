package com.mrapaport.challenges.stocksapi.filter;

import com.mrapaport.challenges.stocksapi.service.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

@SpringBootTest
public class AuthorizationFilterTests {

    public static final String API_KEY_HEADER = "X-API-KEY";
    @Autowired
    AuthorizationFilter authorizationFilter;

    @MockBean
    AuthorizationService authorizationService;

    @Test
    public void whenRequestingSignUpURL_givenNoApiKey_shouldBeAllowedToProceed() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        req.setRequestURI("/signup");

        authorizationFilter.doFilter(req, res, chain);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatus());
    }

    @Test
    public void whenRequestingStocksURL_givenNoApiKey_shouldNotBeAllowedToProceed() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        req.setRequestURI("/stocks");

        authorizationFilter.doFilter(req, res, chain);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
    }

    @Test
    public void whenRequestingStocksURL_givenInvalidApiKey_shouldNotBeAllowedToProceed() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        req.setRequestURI("/stocks");
        req.addHeader(API_KEY_HEADER, "invalid-key");

        Mockito.when(authorizationService.isValidKey("invalid-key")).thenReturn(Boolean.FALSE);

        authorizationFilter.doFilter(req, res, chain);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
    }

    @Test
    public void whenRequestingStocksURL_givenValidApiKey_shouldBeAllowedToProceed() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        req.setRequestURI("/stocks");
        req.addHeader(API_KEY_HEADER, "valid-key");

        Mockito.when(authorizationService.isValidKey("valid-key")).thenReturn(Boolean.TRUE);

        authorizationFilter.doFilter(req, res, chain);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatus());
    }

}
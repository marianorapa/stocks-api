package com.mrapaport.challenges.stocksapi.controller;

import com.mrapaport.challenges.stocksapi.dto.AuthorizedUserDto;
import com.mrapaport.challenges.stocksapi.dto.SignupDto;
import com.mrapaport.challenges.stocksapi.dto.response.SignupResponse;
import com.mrapaport.challenges.stocksapi.exception.EmailAlreadyRegisteredException;
import com.mrapaport.challenges.stocksapi.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @PostMapping(path = "/signup")
    @Operation(summary = "Signup for an api key")
    public SignupResponse signup(@Valid @RequestBody SignupDto request) throws EmailAlreadyRegisteredException {
        logger.info("Signup request received: {}", request);
        AuthorizedUserDto authorizedUserDto = authorizationService.signUp(request);
        return new SignupResponse(authorizedUserDto);
    }
}

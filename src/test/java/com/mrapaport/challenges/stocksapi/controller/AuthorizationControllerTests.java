package com.mrapaport.challenges.stocksapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrapaport.challenges.stocksapi.dto.AuthorizedUserDto;
import com.mrapaport.challenges.stocksapi.dto.SignupDto;
import com.mrapaport.challenges.stocksapi.exception.EmailAlreadyRegisteredException;
import com.mrapaport.challenges.stocksapi.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = AuthorizationController.class)
@AutoConfigureMockMvc
public class AuthorizationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorizationService authorizationService;

    @Test
    public void whenSigningUp_givenInvalidName_shouldReturnBadRequest() throws Exception {
        SignupDto request = new SignupDto("", "Rapaport", "mrapaport@home.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenSigningUp_givenInvalidLastName_shouldReturnBadRequest() throws Exception {
        SignupDto request = new SignupDto("Mariano", "", "mrapaport@home.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenSigningUp_givenInvalidEmail_shouldReturnBadRequest() throws Exception {
        SignupDto request = new SignupDto("Mariano", "Rapaport", "mrapaport@");
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenSigningUp_givenValidData_shouldCallAuthorizationService() throws Exception {
        SignupDto request = new SignupDto("Mariano", "Rapaport", "mrapaport@home.com");
        Mockito.when(authorizationService.signUp(request)).thenReturn(new AuthorizedUserDto());
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(authorizationService, Mockito.times(1)).signUp(request);
    }

    @Test
    public void whenSigningUp_givenAlreadyRegisteredEmail_shouldReturnBadRequest() throws Exception {
        SignupDto request = new SignupDto("Mariano", "Rapaport", "mrapaport@home.com");
        Mockito.when(authorizationService.signUp(request)).thenThrow(EmailAlreadyRegisteredException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenSigningUp_givenNotExistingEmail_shouldReturnCreatedInfo() throws Exception {
        SignupDto request = new SignupDto("Mariano", "Rapaport", "mrapaport@home.com");
        AuthorizedUserDto authorizedUser = new AuthorizedUserDto("Mariano", "Rapaport", "mrapaport@home.com", "some-key");
        Mockito.when(authorizationService.signUp(request)).thenReturn(authorizedUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(authorizedUser)));
    }

}

package com.mrapaport.challenges.stocksapi.service;

import com.mrapaport.challenges.stocksapi.dto.AuthorizedUserDto;
import com.mrapaport.challenges.stocksapi.dto.SignupDto;
import com.mrapaport.challenges.stocksapi.exception.EmailAlreadyRegisteredException;
import com.mrapaport.challenges.stocksapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class AuthorizationServiceTests {

    @Autowired
    AuthorizationService authorizationService;

    @MockBean
    UserService userService;

    @Test
    public void whenSigningUpUser_givenExistentEmail_shouldThrowEmailAlreadyExistsException() {
        Mockito.when(userService.findOneByEmail("mrapaport@home.com"))
                .thenReturn(Optional.of(new User("Mariano", "Rapaport", "mrapaport@home.com", "some-key")));
        SignupDto signupDto = new SignupDto("Mariano", "Rapaport", "mrapaport@home.com");
        Assertions.assertThrows(EmailAlreadyRegisteredException.class, () -> authorizationService.signUp(signupDto));
    }

    @Test
    public void whenSigningUpUser_givenNotExistentEmail_shouldCreateNewUserWithApiKey() throws EmailAlreadyRegisteredException {
        Mockito.when(userService.findOneByEmail("mrapaport@home.com"))
                .thenReturn(Optional.empty());
        SignupDto signupDto = new SignupDto("Mariano", "Rapaport", "mrapaport@home.com");

        User newUser = new User("Mariano", "Rapaport", "mrapaport@home.com", "any-key");
        Mockito.when(userService.save(newUser)).thenReturn(newUser);

        AuthorizedUserDto authorizedUserDto = authorizationService.signUp(signupDto);
        Assertions.assertEquals("Mariano", authorizedUserDto.getName());
        Assertions.assertEquals("Rapaport", authorizedUserDto.getLastName());
        Assertions.assertEquals("mrapaport@home.com", authorizedUserDto.getEmail());
        Assertions.assertNotNull(authorizedUserDto.getApiKey());
        Assertions.assertFalse(authorizedUserDto.getApiKey().isBlank());
    }

    @Test
    public void whenCheckingForValidKey_givenNotExistentKey_shouldReturnFalse() {
        Mockito.when(userService.findOneByKey("some-key")).thenReturn(Optional.empty());
        Boolean isValidKey = authorizationService.isValidKey("some-key");
        Assertions.assertFalse(isValidKey);
    }

    @Test
    public void whenCheckingForValidKey_givenExistentKey_shouldReturnTrue() {
        Mockito.when(userService.findOneByKey("some-key")).thenReturn(Optional.of(new User("Mariano", "Rapaport", "mrapaport@home.com", "some-key")));
        Boolean isValidKey = authorizationService.isValidKey("some-key");
        Assertions.assertTrue(isValidKey);
    }

}

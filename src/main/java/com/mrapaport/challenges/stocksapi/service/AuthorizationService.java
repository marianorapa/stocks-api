package com.mrapaport.challenges.stocksapi.service;

import com.mrapaport.challenges.stocksapi.dto.AuthorizedUserDto;
import com.mrapaport.challenges.stocksapi.dto.SignupDto;
import com.mrapaport.challenges.stocksapi.exception.EmailAlreadyRegisteredException;
import com.mrapaport.challenges.stocksapi.helper.ApiKeyGenerator;
import com.mrapaport.challenges.stocksapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiKeyGenerator apiKeyGenerator;

    public AuthorizedUserDto signUp(SignupDto request) throws EmailAlreadyRegisteredException {
        Optional<User> foundUser = userService.findOneByEmail(request.getEmail());
        if (foundUser.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
        String userApiKey = apiKeyGenerator.generateKey();
        User savedUser = userService.save(new User(request.getName(), request.getLastName(), request.getEmail(), userApiKey));
        return new AuthorizedUserDto(savedUser);
    }

    public Boolean isValidKey(String key) {
        Optional<User> foundUser = userService.findOneByKey(key);
        return foundUser.isPresent();
    }
}

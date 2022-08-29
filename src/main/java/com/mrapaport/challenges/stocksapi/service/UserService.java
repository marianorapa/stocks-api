package com.mrapaport.challenges.stocksapi.service;

import com.mrapaport.challenges.stocksapi.model.User;
import com.mrapaport.challenges.stocksapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findOneByKey(String key) { return userRepository.findOneByApiKey(key);
    }
}

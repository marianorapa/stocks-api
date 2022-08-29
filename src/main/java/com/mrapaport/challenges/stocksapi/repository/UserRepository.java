package com.mrapaport.challenges.stocksapi.repository;

import com.mrapaport.challenges.stocksapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByApiKey(String key);
}

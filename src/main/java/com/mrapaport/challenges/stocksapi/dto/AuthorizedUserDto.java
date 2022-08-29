package com.mrapaport.challenges.stocksapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrapaport.challenges.stocksapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedUserDto {
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "api_key")
    private String apiKey;

    public AuthorizedUserDto(User savedUser) {
        this.name = savedUser.getName();
        this.lastName = savedUser.getLastName();
        this.email = savedUser.getEmail();
        this.apiKey = savedUser.getApiKey();
    }
}

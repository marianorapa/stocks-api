package com.mrapaport.challenges.stocksapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrapaport.challenges.stocksapi.dto.AuthorizedUserDto;
import lombok.Data;

@Data
public class SignupResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "api_key")
    private String apiKey;

    public SignupResponse(AuthorizedUserDto authorizedUserDto) {
        this.name = authorizedUserDto.getName();
        this.lastName = authorizedUserDto.getLastName();
        this.email = authorizedUserDto.getEmail();
        this.apiKey = authorizedUserDto.getApiKey();
    }
}

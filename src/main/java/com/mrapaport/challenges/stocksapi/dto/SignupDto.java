package com.mrapaport.challenges.stocksapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    @NotBlank(message = "Name cannot be blank")
    @JsonProperty(value = "name")
    private String name;

    @NotBlank(message = "Lastname cannot be blank")
    @JsonProperty(value = "last_name")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @JsonProperty(value = "email")
    private String email;
}

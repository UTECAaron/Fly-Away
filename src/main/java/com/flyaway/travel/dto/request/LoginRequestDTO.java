package com.flyaway.travel.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}

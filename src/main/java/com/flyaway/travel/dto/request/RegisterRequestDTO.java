package com.flyaway.travel.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "El nombre debe tener al menos una mayúscula")
    private String firstName;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "El apellido debe tener al menos una mayúscula")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$",
             message = "La contraseña debe tener mínimo 8 caracteres, al menos 1 letra y 1 número")
    private String password;
}

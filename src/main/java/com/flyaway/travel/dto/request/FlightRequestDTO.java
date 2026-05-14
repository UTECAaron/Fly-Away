package com.flyaway.travel.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$", message = "Número de vuelo inválido. Formato: 2-3 letras mayúsculas + 3 dígitos (ej: AA448)")
    private String flightNumber;

    @NotBlank
    private String airlineName;

    @NotNull
    private LocalDateTime estDepartureTime;

    @NotNull
    private LocalDateTime estArrivalTime;

    @NotNull
    @Min(value = 1, message = "Los asientos disponibles deben ser mayores a 0")
    private Integer availableSeats;
}

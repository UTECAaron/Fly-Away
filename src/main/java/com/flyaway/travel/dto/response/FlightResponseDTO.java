package com.flyaway.travel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String airlineName;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
    private Integer availableSeats;
}

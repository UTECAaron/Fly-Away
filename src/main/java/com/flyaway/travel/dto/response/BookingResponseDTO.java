package com.flyaway.travel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private LocalDateTime bookingDate;
    private Long flightId;
    private String flightNumber;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
}

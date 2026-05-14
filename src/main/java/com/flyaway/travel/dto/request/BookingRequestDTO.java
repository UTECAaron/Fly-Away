package com.flyaway.travel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotNull
    private Long flightId;
}

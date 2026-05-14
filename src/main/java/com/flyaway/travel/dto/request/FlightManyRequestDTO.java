package com.flyaway.travel.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class FlightManyRequestDTO {
    private List<FlightRequestDTO> inputs;
}

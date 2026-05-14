package com.flyaway.travel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightSearchResponseDTO {
    private List<FlightResponseDTO> items;
}

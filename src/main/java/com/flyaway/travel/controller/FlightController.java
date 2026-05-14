package com.flyaway.travel.controller;

import com.flyaway.travel.dto.request.FlightManyRequestDTO;
import com.flyaway.travel.dto.request.FlightRequestDTO;
import com.flyaway.travel.dto.response.FlightManyResponseDTO;
import com.flyaway.travel.dto.response.FlightResponseDTO;
import com.flyaway.travel.dto.response.FlightSearchResponseDTO;
import com.flyaway.travel.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody FlightRequestDTO request) {
        FlightResponseDTO dto = flightService.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", dto.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

    @PostMapping("/create-many")
    public ResponseEntity<FlightManyResponseDTO> createMany(@RequestBody FlightManyRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createMany(request));
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponseDTO> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) String estDepartureTimeFrom,
            @RequestParam(required = false) String estDepartureTimeTo
    ) {
        return ResponseEntity.ok(flightService.search(flightNumber, airlineName, estDepartureTimeFrom, estDepartureTimeTo));
    }
}

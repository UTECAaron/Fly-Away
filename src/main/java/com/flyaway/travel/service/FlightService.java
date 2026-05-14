package com.flyaway.travel.service;

import com.flyaway.travel.dto.request.FlightManyRequestDTO;
import com.flyaway.travel.dto.request.FlightRequestDTO;
import com.flyaway.travel.dto.response.FlightManyResponseDTO;
import com.flyaway.travel.dto.response.FlightResponseDTO;
import com.flyaway.travel.dto.response.FlightSearchResponseDTO;
import com.flyaway.travel.model.Flight;
import com.flyaway.travel.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightResponseDTO createFlight(FlightRequestDTO request) {
        if (!request.getEstDepartureTime().isBefore(request.getEstArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La hora de salida debe ser anterior a la de llegada");
        }
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un vuelo con ese número");
        }

        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airlineName(request.getAirlineName())
                .estDepartureTime(request.getEstDepartureTime())
                .estArrivalTime(request.getEstArrivalTime())
                .availableSeats(request.getAvailableSeats())
                .build();

        return toDTO(flightRepository.save(flight));
    }

    public FlightResponseDTO getById(Long id) {
        return toDTO(flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo no encontrado")));
    }

    public FlightSearchResponseDTO search(String flightNumber, String airlineName,
                                          String estDepartureTimeFrom, String estDepartureTimeTo) {
        List<Flight> results;

        if (flightNumber != null && !flightNumber.isBlank()) {
            results = flightRepository.findByFlightNumberContainingIgnoreCase(flightNumber);
        } else if (airlineName != null && !airlineName.isBlank()) {
            results = flightRepository.findByAirlineNameContainingIgnoreCase(airlineName);
        } else if (estDepartureTimeFrom != null && estDepartureTimeTo != null) {
            LocalDateTime from = LocalDateTime.parse(estDepartureTimeFrom.replace("Z", ""));
            LocalDateTime to = LocalDateTime.parse(estDepartureTimeTo.replace("Z", ""));
            results = flightRepository.findByEstDepartureTimeBetween(from, to);
        } else if (estDepartureTimeFrom != null) {
            LocalDateTime from = LocalDateTime.parse(estDepartureTimeFrom.replace("Z", ""));
            results = flightRepository.findByEstDepartureTimeFrom(from);
        } else if (estDepartureTimeTo != null) {
            LocalDateTime to = LocalDateTime.parse(estDepartureTimeTo.replace("Z", ""));
            results = flightRepository.findByEstDepartureTimeTo(to);
        } else {
            results = flightRepository.findAll();
        }

        List<FlightResponseDTO> dtos = results.stream().map(this::toDTO).collect(Collectors.toList());
        return new FlightSearchResponseDTO(dtos);
    }

    public FlightManyResponseDTO createMany(FlightManyRequestDTO request) {
        List<Long> ids = new java.util.ArrayList<>();
        for (FlightRequestDTO dto : request.getInputs()) {
            try {
                FlightResponseDTO created = createFlight(dto);
                ids.add(created.getId());
            } catch (Exception e) {
                // skip duplicates or invalid entries
            }
        }
        return new FlightManyResponseDTO(ids);
    }

    public void deleteAll() {
        flightRepository.deleteAll();
    }

    private FlightResponseDTO toDTO(Flight f) {
        return new FlightResponseDTO(f.getId(), f.getFlightNumber(), f.getAirlineName(),
                f.getEstDepartureTime(), f.getEstArrivalTime(), f.getAvailableSeats());
    }
}

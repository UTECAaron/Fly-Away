package com.flyaway.travel.controller;

import com.flyaway.travel.repository.BookingRepository;
import com.flyaway.travel.repository.FlightRepository;
import com.flyaway.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CleanupController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, String>> cleanup() {
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
        return ResponseEntity.ok(Map.of("message", "Base de datos limpiada"));
    }
}

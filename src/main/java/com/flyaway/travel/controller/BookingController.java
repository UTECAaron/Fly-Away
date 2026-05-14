package com.flyaway.travel.controller;

import com.flyaway.travel.dto.request.BookingRequestDTO;
import com.flyaway.travel.dto.response.BookingResponseDTO;
import com.flyaway.travel.model.User;
import com.flyaway.travel.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> book(
            @Valid @RequestBody BookingRequestDTO request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(bookingService.book(request, currentUser));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }
}

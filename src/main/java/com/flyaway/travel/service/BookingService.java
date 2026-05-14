package com.flyaway.travel.service;

import com.flyaway.travel.dto.request.BookingRequestDTO;
import com.flyaway.travel.dto.response.BookingResponseDTO;
import com.flyaway.travel.model.Booking;
import com.flyaway.travel.model.Flight;
import com.flyaway.travel.model.User;
import com.flyaway.travel.repository.BookingRepository;
import com.flyaway.travel.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingResponseDTO book(BookingRequestDTO request, User currentUser) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo no encontrado"));

        if (!flight.getEstDepartureTime().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede reservar un vuelo que ya partió o está en tránsito");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay asientos disponibles");
        }

        if (!bookingRepository.findConflicting(currentUser, flight.getEstDepartureTime(), flight.getEstArrivalTime()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes una reserva con conflicto de horario");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = Booking.builder()
                .user(currentUser)
                .flight(flight)
                .bookingDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))
                .build();

        Booking saved = bookingRepository.save(booking);

        generateConfirmationFile(saved);

        return toDTO(saved);
    }

    public BookingResponseDTO getById(Long id) {
        return toDTO(bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada")));
    }

    private void generateConfirmationFile(Booking booking) {
        DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // Truncar a microsegundos para que el formato sea exacto
        String bookingDateStr = booking.getBookingDate()
                .truncatedTo(java.time.temporal.ChronoUnit.MICROS)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
        String content = String.format(
                "Hello %s %s,%n%nYour booking was successful! %n%nThe booking is for flight %s with departure date of %s and arrival date of %s.%n%nThe booking was registered at %s.%n%nBon Voyage!%nFly Away Travel%n",
                booking.getUser().getFirstName(), booking.getUser().getLastName(),
                booking.getFlight().getFlightNumber(),
                booking.getFlight().getEstDepartureTime().format(fmtDate),
                booking.getFlight().getEstArrivalTime().format(fmtDate),
                bookingDateStr
        );
        try {
            String userHome = System.getProperty("user.home");
            Files.writeString(Path.of(userHome, "flight_booking_email_" + booking.getId() + ".txt"), content);
        } catch (IOException e) {
            System.err.println("No se pudo generar el archivo de confirmación: " + e.getMessage());
        }
    }

    private BookingResponseDTO toDTO(Booking b) {
        return new BookingResponseDTO(
                b.getId(),
                b.getBookingDate(),
                b.getFlight().getId(),
                b.getFlight().getFlightNumber(),
                b.getUser().getId(),
                b.getUser().getFirstName(),
                b.getUser().getLastName(),
                b.getFlight().getEstDepartureTime(),
                b.getFlight().getEstArrivalTime()
        );
    }
}

package com.flyaway.travel.repository;

import com.flyaway.travel.model.Booking;
import com.flyaway.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT b FROM Booking b JOIN b.flight f
        WHERE b.user = :user
        AND f.estDepartureTime < :arrivalTime
        AND f.estArrivalTime   > :departureTime
    """)
    List<Booking> findConflicting(
        @Param("user")          User user,
        @Param("departureTime") LocalDateTime departureTime,
        @Param("arrivalTime")   LocalDateTime arrivalTime
    );
}

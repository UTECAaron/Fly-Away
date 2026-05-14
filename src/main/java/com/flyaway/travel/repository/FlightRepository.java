package com.flyaway.travel.repository;

import com.flyaway.travel.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByFlightNumberContainingIgnoreCase(String flightNumber);

    List<Flight> findByAirlineNameContainingIgnoreCase(String airlineName);

    @Query("SELECT f FROM Flight f WHERE f.estDepartureTime >= :from")
    List<Flight> findByEstDepartureTimeFrom(@Param("from") LocalDateTime from);

    @Query("SELECT f FROM Flight f WHERE f.estDepartureTime <= :to")
    List<Flight> findByEstDepartureTimeTo(@Param("to") LocalDateTime to);

    @Query("SELECT f FROM Flight f WHERE f.estDepartureTime >= :from AND f.estDepartureTime <= :to")
    List<Flight> findByEstDepartureTimeBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}

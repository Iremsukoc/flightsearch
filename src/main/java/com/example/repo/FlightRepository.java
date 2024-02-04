package com.example.repo;

import com.example.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FlightRepository  extends JpaRepository <Flight, Long> {


    @Query(value = "SELECT * FROM flights WHERE flight_id = :id", nativeQuery = true)
    Optional<Flight> findByID(@Param("id") Long id);

    @Query( value = "SELECT * FROM flights  WHERE  departure_airport = :departureAirport "
            + "AND arrival_airport = :arrivalAirport "
            + "AND departure_date = :departureDate "
            + "AND arrival_date = :arrivalDate", nativeQuery = true)
    List<Flight> findRoundTripFlights(
            @Param("departureAirport") String departureAirport,
            @Param("arrivalAirport") String arrivalAirport,
            @Param("departureDate") Date departureDate,
            @Param("returnDate") Date arrivalDate);

    @Query(value = "SELECT * FROM flights  WHERE departure_airport = :departureAirport "
            + "AND arrival_airport = :arrivalAirport "
            + "AND departure_date = :departureDate", nativeQuery = true)
    List<Flight> findOneWayFlights(
            @Param("departureAirport") String departureAirport,
            @Param("arrivalAirport") String arrivalAirport,
            @Param("departureDate") Date departureDate);
}



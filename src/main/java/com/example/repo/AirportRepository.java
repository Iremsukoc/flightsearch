package com.example.repo;

import com.example.model.Airport;
import com.example.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AirportRepository  extends JpaRepository<Airport, Long> {

    @Query(value = "SELECT * FROM airports WHERE airport_id = :id", nativeQuery = true)
    Optional<Airport> findByID(@Param("id") Long id);

}

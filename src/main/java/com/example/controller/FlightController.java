package com.example.controller;


import com.example.dto.*;
import com.example.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private  FlightService flightService;


    @DeleteMapping(value="/delete-flight-by-id")
    public QueryResponseDto deleteFlight(@RequestBody FlightIdDto flightIdDto) {
        try {
            System.out.println("Before deleting flight");
            flightService.deleteFlight(flightIdDto);
            return new QueryResponseDto("Flight successfully deleted.");
        } catch (Exception e) {
            System.out.println("Flight deletion failed: " + e.getMessage());
            return new QueryResponseDto("Not Deleted");
        }
    }


    @PostMapping(value="/create-flight")
    public QueryResponseDto createFlight(
            @RequestBody CreateRequestDto createRequestDto) {
        try {
            flightService.createFlight(createRequestDto);
            return new QueryResponseDto("Flight successfully created.");
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryResponseDto("Flight creation failed. Error: " + e.getMessage());
        }
    }


    @GetMapping("/get-flight-by-id")
    public FlightResponseDto getFlightById(@RequestBody FlightIdDto flightIdDto) {
        return flightService.getFlightById(flightIdDto);
    }

    @PutMapping("/update-flight")
    public ResponseEntity<QueryResponseDto> updateFlight(
            @RequestBody UpdateFlightRequestDto updateRequestDto) {
        try {
            flightService.updateFlight(updateRequestDto);
            return ResponseEntity.ok(new QueryResponseDto("Flight successfully updated."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new QueryResponseDto("Flight update failed. Error: " + e.getMessage()));
        }
    }


    @GetMapping("/search-flights")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(
            @RequestParam String departureAirport,
            @RequestParam String arrivalAirport,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date returnDate) {

        List<FlightResponseDto> flights;

        if (returnDate != null) {
            flights = flightService.findRoundTripFlights(departureAirport, arrivalAirport, departureDate, returnDate);
        } else {
            flights = flightService.findOneWayFlights(departureAirport, arrivalAirport, departureDate);
        }

        return ResponseEntity.ok(flights);
    }

}

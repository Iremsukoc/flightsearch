package com.example.controller;


import com.example.dto.*;
import com.example.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @DeleteMapping(value="/delete-airport-by-id")
    public QueryResponseDto deleteAirport(@RequestBody AirportIdDto airportIdDto) {
        try {
            airportService.deleteAirport(airportIdDto);
            return new QueryResponseDto("Airport successfully deleted.");
        } catch (Exception e) {
            System.out.println("Airport deletion failed: " + e.getMessage());
            return new QueryResponseDto("Not Deleted");
        }
    }


    @PostMapping(value="/create-airport")
    public QueryResponseDto createFlight(
            @RequestBody CreateAirportRequestDto createRequestDto) {
        try {
            airportService.createAirport(createRequestDto);
            return new QueryResponseDto("Flight successfully created.");
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryResponseDto("Flight creation failed. Error: " + e.getMessage());
        }
    }


    @GetMapping("/get-airport-by-id")
    public AirportResponseDto getFlightById(@RequestBody AirportIdDto airportIdDto) {
        return airportService.getAirportById(airportIdDto);
    }


    @PutMapping("/update-airport")
    public QueryResponseDto updateAirport(
            @RequestBody UpdateAirportRequestDto updateRequestDto) {
        try {
            airportService.updateAirport(updateRequestDto);
            return new QueryResponseDto("Airport successfully updated.");
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryResponseDto("Airport update failed. Error: " + e.getMessage());
        }
    }



}

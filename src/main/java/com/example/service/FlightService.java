package com.example.service;


import com.example.dto.*;
import com.example.model.Flight;
import com.example.repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {


    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);


    @Autowired
    private FlightRepository flightRepository;


    public boolean validateFlightExistence(Long id) throws Exception {
        if(!flightRepository.existsById(id)){
            throw new Exception("Invalid ID existence");
        } else {
            return true;
        }
    }


    public void deleteFlight(FlightIdDto flightIdDto) throws Exception {
        if (validateFlightExistence(flightIdDto.getFlightId())){
            flightRepository.deleteById(flightIdDto.getFlightId());
        }

    }


    public void createFlight(CreateRequestDto createRequestDto){

        try {
            saveFlight(createRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void saveFlight(CreateRequestDto createRequestDto) {
        try {
            Flight newFlight = new Flight();
            newFlight.setArrivalAirport(createRequestDto.getArrivalAirport());
            newFlight.setDepartureAirport(createRequestDto.getDepartureAirport());
            newFlight.setArrivalDate(createRequestDto.getArrivalDate());
            newFlight.setDepartureDate(createRequestDto.getDepartureDate());
            newFlight.setPrice(createRequestDto.getPrice());

            flightRepository.save(newFlight);
            logger.info("Flight saved successfully.");
        } catch (Exception e) {
            logger.info("Error while saving flight: " + e.getMessage());
            throw e;
        }
    }


    public FlightResponseDto getFlightById(FlightIdDto flightIdDto) {
        FlightResponseDto flightResponseDto = new FlightResponseDto ();

        Flight flight = flightRepository.findByID(flightIdDto.getFlightId ())
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + flightIdDto.getFlightId ()));

        flightResponseDto.setFlightId(flightIdDto.getFlightId());
        flightResponseDto.setArrivalAirport(flight.getArrivalAirport());
        flightResponseDto.setDepartureAirport(flight.getDepartureAirport());
        flightResponseDto.setArrivalDate(flight.getArrivalDate());
        flightResponseDto.setDepartureDate(flight.getDepartureDate());
        flightResponseDto.setPrice(flight.getPrice());

        return flightResponseDto;
    }


    public void updateFlight(UpdateFlightRequestDto updateRequestDto) {
        try {
            Flight flight = flightRepository.findByID(updateRequestDto.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight not found with id: " + updateRequestDto.getFlightId()));

            flight.setArrivalAirport(updateRequestDto.getUpdatedArrivalAirport());
            flight.setDepartureAirport(updateRequestDto.getUpdatedDepartureAirport());
            flight.setArrivalDate(updateRequestDto.getUpdatedArrivalDate());
            flight.setDepartureDate(updateRequestDto.getUpdatedDepartureDate());
            flight.setPrice(updateRequestDto.getUpdatedPrice());

            flightRepository.save(flight);
        } catch (Exception e) {
            throw e;
        }
    }


    public List<FlightResponseDto> findRoundTripFlights(String departureAirport, String arrivalAirport, Date departureDate, Date returnDate) {
        List<Flight> roundTripFlights = flightRepository.findRoundTripFlights(departureAirport, arrivalAirport, departureDate, returnDate);

        return roundTripFlights.stream()
                .map(this::convertToFlightResponseDto)
                .collect(Collectors.toList());
    }

    public List<FlightResponseDto> findOneWayFlights(String departureAirport, String arrivalAirport, Date departureDate) {
        List<Flight> oneWayFlights = flightRepository.findOneWayFlights(departureAirport, arrivalAirport, departureDate);

        return oneWayFlights.stream()
                .map(this::convertToFlightResponseDto)
                .collect(Collectors.toList());
    }


    private FlightResponseDto convertToFlightResponseDto(Flight flight) {
        FlightResponseDto flightResponseDto = new FlightResponseDto();
        flightResponseDto.setFlightId(flight.getFlightID());
        flightResponseDto.setArrivalAirport(flight.getArrivalAirport());
        flightResponseDto.setDepartureAirport(flight.getDepartureAirport());
        flightResponseDto.setArrivalDate(flight.getArrivalDate());
        flightResponseDto.setDepartureDate(flight.getDepartureDate());
        flightResponseDto.setPrice(flight.getPrice());

        return flightResponseDto;
    }


}

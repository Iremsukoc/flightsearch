package com.example.service;

import com.example.dto.FlightApiResponseDto;
import com.example.dto.FlightResponseDto;
import com.example.model.Flight;
import com.example.repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightDataUpdater {

    @Autowired
    private FlightRepository flightRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateFlightData() {
        List<Flight> mockFlights = callMockApi();

        flightRepository.saveAll(mockFlights);

        System.out.println("Flight data updated successfully.");
    }

    private List<Flight> callMockApi() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://mock-api-url.com/flights";

        FlightApiResponseDto mockApiResponse = restTemplate.getForObject(apiUrl, FlightApiResponseDto.class);

        return convertToFlights(mockApiResponse);
    }

    private List<Flight> convertToFlights(FlightApiResponseDto mockApiResponse) {
        return mockApiResponse.getFlights().stream()
                .map(flightDto -> new Flight(
                        flightDto.getFlightId(),
                        flightDto.getDepartureAirport(),
                        flightDto.getArrivalAirport(),
                        flightDto.getDepartureDate(),
                        flightDto.getArrivalDate(),
                        flightDto.getPrice()
                ))
                .collect(Collectors.toList());
    }




}

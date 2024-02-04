package com.example.service;


import com.example.dto.*;
import com.example.model.Airport;
import com.example.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repo.AirportRepository;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;


    public boolean validateFlightExistence(Long id) throws Exception {
        if(!airportRepository.existsById(id)){
            throw new Exception("Invalid ID existence");
        } else {
            return true;
        }
    }


    public void deleteAirport(AirportIdDto airportIdDto) throws Exception {
        if (validateFlightExistence(airportIdDto.getAirportId())){
            airportRepository.deleteById(airportIdDto.getAirportId());
        }

    }


    public void createAirport(CreateAirportRequestDto createRequestDto){

        try {
            saveFlight(createRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void saveFlight(CreateAirportRequestDto createRequestDto) {
        try {
            Airport airport = new Airport();
            airport.setAirportID(createRequestDto.getAirportId());
            airport.setCity(createRequestDto.getCity());

            airportRepository.save(airport);
        } catch (Exception e) {
            throw e;
        }
    }


    public AirportResponseDto getAirportById(AirportIdDto airportIdDto) {
        AirportResponseDto airportResponseDto = new AirportResponseDto ();

        Airport airport = airportRepository.findByID(airportIdDto.getAirportId ())
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + airportIdDto.getAirportId ()));

        airportResponseDto.setAirportId(airport.getAirportID());
        airportResponseDto.setCity(airport.getCity());


        return airportResponseDto;
    }


    public void updateAirport(UpdateAirportRequestDto updateRequestDto) {
        try {
            Airport airport = airportRepository.findByID(updateRequestDto.getAirportId())
                    .orElseThrow(() -> new RuntimeException("Airport not found with id: " + updateRequestDto.getAirportId()));

            airport.setCity(updateRequestDto.getUpdatedCity());

            airportRepository.save(airport);
        } catch (Exception e) {
            throw e;
        }
    }



}

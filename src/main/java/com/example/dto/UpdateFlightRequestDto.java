package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFlightRequestDto {
    private Long flightId;
    private String updatedArrivalAirport;
    private String updatedDepartureAirport;
    private Date updatedArrivalDate;
    private Date updatedDepartureDate;
    private double updatedPrice;

}


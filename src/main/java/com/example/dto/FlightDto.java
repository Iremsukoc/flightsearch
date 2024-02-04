package com.example.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private Long flightId;
    private String departureAirport;
    private String arrivalAirport;
    private Date departureDate;
    private Date arrivalDate;
    private double price;

}

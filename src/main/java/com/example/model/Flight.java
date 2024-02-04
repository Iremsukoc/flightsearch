package com.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Long flightID;

    @Column(name = "departure_airport" , nullable = false)
    private String departureAirport;

    @Column(name = "arrival_airport" , nullable = false)
    private String arrivalAirport;


    @Column(name = "departure_date" , nullable = false)
    private Date departureDate;

    @Column(name = "arrival_date" , nullable = false)
    private Date arrivalDate;

    @Column(name = "price" , nullable = false)
    private double price;

}

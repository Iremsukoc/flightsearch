package com.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightApiResponseDto {

    private List<FlightDto> flights;
}
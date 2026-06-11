package com.example.RMJHallAdmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEnquiryRequest {
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String notes;
}

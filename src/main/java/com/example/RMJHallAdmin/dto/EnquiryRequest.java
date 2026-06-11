package com.example.RMJHallAdmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryRequest {

    private String customerName;
    private String phoneNumber;
    private String address;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int noOfGuests;
    private String notes;
}

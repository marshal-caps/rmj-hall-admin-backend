package com.example.RMJHallAdmin.dto;

import com.example.RMJHallAdmin.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingBookingDTO {
    private String customerName;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus status;
}
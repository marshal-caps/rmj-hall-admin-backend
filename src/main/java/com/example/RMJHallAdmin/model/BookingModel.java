package com.example.RMJHallAdmin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;
    private String customerName;
    private boolean deleted;
    private String phoneNumber;
    private String address;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private int noOfGuests;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

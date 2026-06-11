package com.example.RMJHallAdmin.dto;

import com.example.RMJHallAdmin.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentEnquiryDTO {

    private String customerName;
    private String phoneNumber;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private BookingStatus status;
}
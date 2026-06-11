package com.example.RMJHallAdmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardSummary {
        private long totalBookings;
        private long enquiredBookings;
        private long confirmedBookings;
        private long cancelledBookings;
        private long todayBookings;
}

package com.example.RMJHallAdmin.controller;

import com.example.RMJHallAdmin.dto.DashBoardSummary;
import com.example.RMJHallAdmin.dto.EnquiryRequest;
import com.example.RMJHallAdmin.dto.UpdateEnquiryRequest;
import com.example.RMJHallAdmin.model.BookingModel;
import com.example.RMJHallAdmin.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.RMJHallAdmin.dto.UpcomingBookingDTO;
import com.example.RMJHallAdmin.dto.RecentEnquiryDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/enquiry")
    public String createEnquiry(@RequestBody EnquiryRequest enquiry){
        bookingService.createsEnquiry(enquiry);
        return "Added Successfully";
    }

    @GetMapping("/bookings")
    public List<BookingModel> getBookingbyDate(@RequestParam("eventDate") LocalDate localDate){
        return bookingService.getBookingbyDate(localDate);

    }
    @PostMapping("/bookings/{bookingId}/confirm")
    public String confirmBooking(@PathVariable("bookingId") int bookingId){
        bookingService.confirm(bookingId);
        return "Confirmed Successfully";

    }
    @PutMapping("/bookings/{id}/update")
    public String updateBooking(
            @PathVariable long id,
            @RequestBody UpdateEnquiryRequest request
    ) {
        bookingService.updateEnquiry(id,
                request.getEventDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getNotes());
        return "Updated Successfully";
    }
    @GetMapping("/bookings/all")
    public List<BookingModel> getBookings(){
        return bookingService.getBookings();
    }
    @DeleteMapping("/bookings/{id}")
    public String delete(@PathVariable long id) {
        bookingService.softDelete(id);
        return "Deleted Successfully";
    }
    @GetMapping("/dashboard/summary")
    public DashBoardSummary dashboard(){
        return bookingService.getsummary();
    }

    @GetMapping("/dashboard/upcoming")
    public List<UpcomingBookingDTO> getUpcomingBookings() {
        return bookingService.getUpcomingBookings();
    }

    @GetMapping("/dashboard/recent-enquiries")
    public List<RecentEnquiryDTO> getRecentEnquiries() {
        return bookingService.getRecentEnquiries();
    }


}

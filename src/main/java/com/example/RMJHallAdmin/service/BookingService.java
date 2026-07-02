package com.example.RMJHallAdmin.service;

import com.example.RMJHallAdmin.dto.DashBoardSummary;
import com.example.RMJHallAdmin.dto.EnquiryRequest;
import com.example.RMJHallAdmin.exception.BookingNotFoundException;
import com.example.RMJHallAdmin.exception.TimeSlotUnavailableException;
import com.example.RMJHallAdmin.model.BookingModel;
import com.example.RMJHallAdmin.model.BookingStatus;
import com.example.RMJHallAdmin.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import com.example.RMJHallAdmin.dto.UpcomingBookingDTO;
import com.example.RMJHallAdmin.dto.RecentEnquiryDTO;

@Service
public class BookingService {
    @Autowired
    BookingRepo bookingRepo;


    public void createsEnquiry(EnquiryRequest enquiry) {
        if (enquiry.getPhoneNumber() == null || enquiry.getPhoneNumber().length() < 10) {
            throw new IllegalArgumentException("Enter Valid Phone number");
        }
        if (enquiry.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }
        if (enquiry.getStartTime().equals(enquiry.getEndTime()) || enquiry.getStartTime().isAfter(enquiry.getEndTime())) {
            throw new IllegalArgumentException("Invalid Time Intervals");
        }

        List<BookingModel> existingBookings = bookingRepo.findByEventDateAndDeletedFalse(enquiry.getEventDate());
        for (BookingModel b : existingBookings) {
            if (b.getStatus() == BookingStatus.CANCELLED) {
                continue;
            }
            if (enquiry.getStartTime().isBefore(b.getEndTime()) && enquiry.getEndTime().isAfter(b.getStartTime())) {
                throw new TimeSlotUnavailableException();
            }
        }
        BookingModel bm = new BookingModel();
        bm.setCustomerName(enquiry.getCustomerName());
        bm.setEventName(enquiry.getEventName());
        bm.setPhoneNumber(enquiry.getPhoneNumber());
        bm.setAddress(enquiry.getAddress());
        bm.setEventDate(enquiry.getEventDate());
        bm.setStartTime(enquiry.getStartTime());
        bm.setEndTime(enquiry.getEndTime());
        bm.setNoOfGuests(enquiry.getNoOfGuests());
        bm.setNotes(enquiry.getNotes());
        bm.setStatus(BookingStatus.ENQUIRED);
        bm.setDeleted(false);
        bm.setCreatedAt(LocalDateTime.now());
        bm.setUpdatedAt(LocalDateTime.now());

        bookingRepo.save(bm);

    }

    public List<BookingModel> getBookingbyDate(LocalDate localDate) {
        if (localDate == null) {
            throw new IllegalArgumentException("Invalid Date");
        }
        List<BookingModel> listbookingModel = bookingRepo.findByEventDateAndDeletedFalse(localDate);
        listbookingModel.sort((a, b) -> a.getStartTime().compareTo(b.getStartTime()));
        return listbookingModel;

    }
    public List<UpcomingBookingDTO> getUpcomingBookings() {
        List<BookingModel> bookings =
                bookingRepo.findByEventDateAfterAndDeletedFalseOrderByEventDateAsc(LocalDate.now());

        return bookings.stream()
                .map(b -> new UpcomingBookingDTO(
                        b.getCustomerName(),
                        b.getEventDate(),
                        b.getStartTime(),
                        b.getEndTime(),
                        b.getEventName(),
                        b.getStatus()
                ))
                .toList();
    }

    public List<RecentEnquiryDTO> getRecentEnquiries() {
        List<BookingModel> enquiries =
                bookingRepo.findTop5ByDeletedFalseOrderByCreatedAtDesc();

        return enquiries.stream()
                .map(b -> new RecentEnquiryDTO(
                        b.getCustomerName(),
                        b.getPhoneNumber(),
                        b.getEventName(),
                        b.getEventDate(),
                        b.getCreatedAt(),
                        b.getStatus()
                ))
                .toList();
    }

    public void confirm(int bookingId) {
        Optional<BookingModel> confirmId = bookingRepo.findById((long) bookingId);
        if (confirmId.isEmpty()) {
            throw new BookingNotFoundException();
        }
        BookingModel confirmbooking = confirmId.get();
        if (confirmbooking.isDeleted() == true || confirmbooking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot Confirm..");
        }
        if (confirmbooking.getStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException("Already Confirmed");
        }
        List<BookingModel> existingBookings = bookingRepo.findByEventDateAndDeletedFalse(confirmbooking.getEventDate());
        for (BookingModel b : existingBookings) {
            if (b.getStatus() == BookingStatus.CANCELLED) {
                continue;
            }
            if (b.getBookingId() == confirmbooking.getBookingId()) {
                continue;
            }

            if (confirmbooking.getStartTime().isBefore(b.getEndTime()) && confirmbooking.getEndTime().isAfter(b.getStartTime())) {
                throw new TimeSlotUnavailableException();
            }
        }
        confirmbooking.setStatus(BookingStatus.CONFIRMED);
        confirmbooking.setUpdatedAt(LocalDateTime.now());
        bookingRepo.save(confirmbooking);


    }

    public void updateEnquiry(long id, LocalDate eventDate, LocalTime startTime, LocalTime endTime, String notes) {
        Optional<BookingModel> exists = bookingRepo.findById((long) id);
        if (exists.isEmpty()) {
            throw new BookingNotFoundException();
        }
        BookingModel updatebooking = exists.get();
        if (updatebooking.isDeleted() || updatebooking.getStatus().equals(BookingStatus.CANCELLED) || updatebooking.getStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Cannot Update");

        }
        if (eventDate.isBefore(LocalDate.now()) || startTime.isAfter(endTime)||startTime.equals(endTime)) {
            throw new IllegalArgumentException("Cannot Update");
        }
        List<BookingModel> existsBookings = bookingRepo.findByEventDateAndDeletedFalse(eventDate);
        for (BookingModel b : existsBookings) {
            if (b.getBookingId() == updatebooking.getBookingId()) {
                continue;
            }
            if (startTime.isBefore(b.getEndTime()) && endTime.isAfter(b.getStartTime())) {
                throw new IllegalArgumentException("Cannot Update");
            }
        }

        updatebooking.setEventDate(eventDate);
        updatebooking.setStartTime(startTime);
        updatebooking.setEndTime(endTime);
        updatebooking.setNotes(notes);
        updatebooking.setUpdatedAt(LocalDateTime.now());
        bookingRepo.save(updatebooking);
    }

    public List<BookingModel> getBookings() {
        List<BookingModel> bookings = bookingRepo.findByDeletedFalseOrderByEventDateAsc();
        bookings.sort((a,b)->a.getStartTime().compareTo(b.getStartTime()));
        return bookings;
    }

    public void softDelete(long id) {
        Optional<BookingModel> exists = bookingRepo.findById((long)id);
        if(exists.isEmpty()){
            throw new BookingNotFoundException();
        }
        BookingModel deletemodel = exists.get();
        if(deletemodel.isDeleted()){
            throw new IllegalArgumentException("Cannot Delete");
        }
        deletemodel.setDeleted(true);
        deletemodel.setUpdatedAt(LocalDateTime.now());
        bookingRepo.save(deletemodel);
    }
    public DashBoardSummary getsummary(){
        DashBoardSummary dashBoardSummary = new DashBoardSummary();
        dashBoardSummary.setConfirmedBookings(bookingRepo.countByStatus(BookingStatus.CONFIRMED));
        dashBoardSummary.setEnquiredBookings(bookingRepo.countByStatus(BookingStatus.ENQUIRED));
        dashBoardSummary.setCancelledBookings(bookingRepo.countByDeletedTrue());
        dashBoardSummary.setTotalBookings(bookingRepo.count());
        dashBoardSummary.setTodayBookings(bookingRepo.countByEventDate(LocalDate.now()));
        return dashBoardSummary;
    }
}


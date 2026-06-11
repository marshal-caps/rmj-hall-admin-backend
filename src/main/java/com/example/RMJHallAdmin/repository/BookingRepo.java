package com.example.RMJHallAdmin.repository;

import com.example.RMJHallAdmin.model.BookingModel;
import com.example.RMJHallAdmin.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<BookingModel,Long> {

   List<BookingModel> findByEventDateAndDeletedFalse(LocalDate eventDate);
   List<BookingModel> findByDeletedFalseOrderByEventDateAsc();
   long countByStatus(BookingStatus status);
   long countByDeletedTrue();
   long countByEventDate(LocalDate localDate);
   List<BookingModel> findByEventDateAfterAndDeletedFalseOrderByEventDateAsc(LocalDate date);

   List<BookingModel> findTop5ByDeletedFalseOrderByCreatedAtDesc();



}

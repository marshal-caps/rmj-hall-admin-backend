package com.example.RMJHallAdmin.exception;

public class BookingNotFoundException extends RuntimeException{

    public BookingNotFoundException(){
        super("Booking Not Found");
    }
}

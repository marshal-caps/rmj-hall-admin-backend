package com.example.RMJHallAdmin.exception;

public class TimeSlotUnavailableException extends RuntimeException{

    public TimeSlotUnavailableException(){
        super("Time Slot Unavailable");
    }

}

package com.traveller.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Integer eventId) {
        super("Event with id " + eventId + " not found");
    }

}

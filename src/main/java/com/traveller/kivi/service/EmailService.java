package com.traveller.kivi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.users.User;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String getenv = System.getenv("SPRING_MAIL_USERNAME");
        if (getenv == null) {
            throw new RuntimeException("Environment variable SPRING_MAIL_USERNAME is not set");
        }
        message.setFrom(getenv);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    /**
     * Notify all attended users that the event date has changed.
     */
    public void notifyEventDateChanged(Event event) {
        notifyAttendees(event, "Event Date Changed",
                "The event '" + event.getName() + "' has been rescheduled to " + event.getStartDate() + ".");
    }

    /**
     * Notify all attended users that the event has been canceled.
     */
    public void notifyEventCancelled(Event event) {
        notifyAttendees(event, "Event Cancelled",
                "The event '" + event.getName() + "' has been canceled.");
    }

    /**
     * Notify all attended users that the event type has changed.
     */
    public void notifyEventTypeChanged(Event event) {
        notifyAttendees(event, "Event Type Changed",
                "The event '" + event.getName() + "' type has been changed to " + event.getEventType() + ".");
    }

    /**
     * Notify all attended users that the event name has changed.
     */
    public void notifyEventNameChanged(Event event) {
        notifyAttendees(event, "Event Name Changed",
                "The event name has been changed to '" + event.getName() + "'.");
    }

    /**
     * Notify all attended users that the event details have changed.
     */
    public void notifyEventDetailsChanged(Event event) {
        notifyAttendees(event, "Event Details Changed",
                "The details of the event '" + event.getName() + "' have been updated.");
    }

    /**
     * Notify all attended users that the event duration has changed.
     */
    public void notifyEventDurationChanged(Event event) {
        notifyAttendees(event, "Event Duration Changed",
                "The duration of the event '" + event.getName() + "' has been updated to " + event.getDuration() + " minutes.");
    }

    /**
     * Notify all attended users that the event language has changed.
     */
    public void notifyEventLanguageChanged(Event event) {
        notifyAttendees(event, "Event Language Changed",
                "The language of the event '" + event.getName() + "' has been changed to " + event.getLanguage() + ".");
    }

    /**
     * Helper method to notify all attendees of an event.
     */
    private void notifyAttendees(Event event, String subject, String messageBody) {
        List<User> attendees = event.getAttendants();
        for (User user : attendees) {
            sendSimpleMessage(user.getEmail(), subject, messageBody);
        }
    }
}
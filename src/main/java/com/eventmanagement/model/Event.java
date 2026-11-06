package com.eventmanagement.model;

import java.sql.Date;

/**
 * Event model class representing an event in the system
 */
public class Event {
    private int eventId;
    private String name;
    private Date date;
    private String venue;
    private String description;
    
    public Event() {}
    
    public Event(int eventId, String name, Date date, String venue, String description) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }
    
    // Getters and Setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}


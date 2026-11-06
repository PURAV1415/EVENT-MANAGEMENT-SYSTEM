package com.eventmanagement.model;

import java.sql.Timestamp;

/**
 * Registration model class representing a user registration for an event
 */
public class Registration {
    private int regId;
    private String userName;
    private String email;
    private String phone;
    private int eventId;
    private Timestamp registeredAt;
    
    public Registration() {}
    
    public Registration(int regId, String userName, String email, String phone, int eventId, Timestamp registeredAt) {
        this.regId = regId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.eventId = eventId;
        this.registeredAt = registeredAt;
    }
    
    // Getters and Setters
    public int getRegId() { return regId; }
    public void setRegId(int regId) { this.regId = regId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    
    public Timestamp getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Timestamp registeredAt) { this.registeredAt = registeredAt; }
}


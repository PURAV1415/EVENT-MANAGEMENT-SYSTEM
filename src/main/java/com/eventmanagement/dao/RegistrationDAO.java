package com.eventmanagement.dao;

import com.eventmanagement.model.Registration;
import com.eventmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Registration operations
 */
public class RegistrationDAO {
    
    /**
     * Register user for an event
     * @param registration the registration details
     * @return true if successful, false otherwise
     */
    public boolean registerUser(Registration registration) {
        String sql = "INSERT INTO registrations (user_name, email, phone, event_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, registration.getUserName());
            stmt.setString(2, registration.getEmail());
            stmt.setString(3, registration.getPhone());
            stmt.setInt(4, registration.getEventId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        registration.setRegId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all registrations for a specific event
     * @param eventId the event ID
     * @return List of registrations for the event
     */
    public List<Registration> getRegistrationsByEventId(int eventId) {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE event_id = ? ORDER BY registered_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegId(rs.getInt("reg_id"));
                    registration.setUserName(rs.getString("user_name"));
                    registration.setEmail(rs.getString("email"));
                    registration.setPhone(rs.getString("phone"));
                    registration.setEventId(rs.getInt("event_id"));
                    registration.setRegisteredAt(rs.getTimestamp("registered_at"));
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting registrations by event ID: " + e.getMessage());
        }
        return registrations;
    }
    
    /**
     * Get all registrations
     * @return List of all registrations
     */
    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT r.*, e.name as event_name FROM registrations r " +
                    "JOIN events e ON r.event_id = e.event_id " +
                    "ORDER BY r.registered_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Registration registration = new Registration();
                registration.setRegId(rs.getInt("reg_id"));
                registration.setUserName(rs.getString("user_name"));
                registration.setEmail(rs.getString("email"));
                registration.setPhone(rs.getString("phone"));
                registration.setEventId(rs.getInt("event_id"));
                registration.setRegisteredAt(rs.getTimestamp("registered_at"));
                registrations.add(registration);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all registrations: " + e.getMessage());
        }
        return registrations;
    }
    
    /**
     * Check if user is already registered for an event
     * @param email the user's email
     * @param eventId the event ID
     * @return true if already registered, false otherwise
     */
    public boolean isUserRegistered(String email, int eventId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE email = ? AND event_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setInt(2, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user registration: " + e.getMessage());
        }
        return false;
    }
}

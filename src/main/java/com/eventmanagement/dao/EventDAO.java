package com.eventmanagement.dao;

import com.eventmanagement.model.Event;
import com.eventmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Event operations
 */
public class EventDAO {
    
    /**
     * Get all events
     * @return List of all events
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY date ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setName(rs.getString("name"));
                event.setDate(rs.getDate("date"));
                event.setVenue(rs.getString("venue"));
                event.setDescription(rs.getString("description"));
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all events: " + e.getMessage());
        }
        return events;
    }
    
    /**
     * Get event by ID
     * @param eventId the event ID
     * @return Event object if found, null otherwise
     */
    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setEventId(rs.getInt("event_id"));
                    event.setName(rs.getString("name"));
                    event.setDate(rs.getDate("date"));
                    event.setVenue(rs.getString("venue"));
                    event.setDescription(rs.getString("description"));
                    return event;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting event by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Add new event
     * @param event the event to add
     * @return true if successful, false otherwise
     */
    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (name, date, venue, description) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, event.getName());
            stmt.setDate(2, event.getDate());
            stmt.setString(3, event.getVenue());
            stmt.setString(4, event.getDescription());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update existing event
     * @param event the event to update
     * @return true if successful, false otherwise
     */
    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET name = ?, date = ?, venue = ?, description = ? WHERE event_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, event.getName());
            stmt.setDate(2, event.getDate());
            stmt.setString(3, event.getVenue());
            stmt.setString(4, event.getDescription());
            stmt.setInt(5, event.getEventId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating event: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete event
     * @param eventId the event ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }
        return false;
    }
}

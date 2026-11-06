-- Event Management System Database Schema
-- Run this script to create the database and tables

CREATE DATABASE IF NOT EXISTS event_management;
USE event_management;

-- Users table for login credentials
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('user', 'admin') NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Events table
CREATE TABLE events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    date DATE NOT NULL,
    venue VARCHAR(200) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Registrations table
CREATE TABLE registrations (
    reg_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    event_id INT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'admin'),
('user1', 'user123', 'user'),
('john_doe', 'password123', 'user');

INSERT INTO events (name, date, venue, description) VALUES 
('Tech Conference 2024', '2024-03-15', 'Convention Center, New York', 'Annual technology conference featuring the latest innovations in AI, blockchain, and cloud computing.'),
('Music Festival', '2024-04-20', 'Central Park, New York', 'Outdoor music festival with multiple stages and food vendors.'),
('Business Networking Event', '2024-03-25', 'Grand Hotel, New York', 'Professional networking event for entrepreneurs and business leaders.'),
('Art Exhibition', '2024-05-10', 'Modern Art Museum, New York', 'Contemporary art exhibition featuring works from local and international artists.'),
('Sports Tournament', '2024-04-05', 'Sports Complex, New York', 'Annual basketball tournament with teams from across the city.');

-- Insert sample registrations
INSERT INTO registrations (user_name, email, phone, event_id) VALUES 
('John Doe', 'john@email.com', '555-0123', 1),
('Jane Smith', 'jane@email.com', '555-0124', 1),
('Mike Johnson', 'mike@email.com', '555-0125', 2),
('Sarah Wilson', 'sarah@email.com', '555-0126', 3);


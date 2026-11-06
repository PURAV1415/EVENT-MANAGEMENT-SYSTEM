# Event Management System

A complete Event Management System built with HTML, CSS, Java (JDBC), and MySQL. This system allows users to view and register for events, while administrators can manage events and view registrations.

## Features

### User Features
- **Login System**: Separate login for users and administrators
- **Event Browsing**: View all upcoming events with details
- **Event Registration**: Register for events with personal information
- **Responsive Design**: Modern, mobile-friendly interface

### Admin Features
- **Event Management**: Add, edit, and delete events
- **Registration Management**: View all event registrations
- **Dashboard**: Comprehensive admin dashboard

## Technology Stack

- **Frontend**: HTML5, CSS3, JavaScript
- **Backend**: Java Servlets, JDBC
- **Database**: MySQL
- **Build Tool**: Maven
- **Server**:jetty 

## Prerequisites

Before running this project, ensure you have:

1. **Java Development Kit (JDK) 11 or higher**
2. **Apache Maven 3.6 or higher**
3. **MySQL Server 8.0 or higher**
4. **Apache Tomcat 9.0 or higher** (or use Maven Tomcat plugin)

## Installation & Setup

### 1. Database Setup

1. Start your MySQL server
2. Open MySQL command line or MySQL Workbench
3. Run the SQL script to create the database and tables:

```sql
-- Run the contents of database/schema.sql
source database/schema.sql;
```

This will create:
- `event_management` database
- `users` table (with sample admin and user accounts)
- `events` table (with sample events)
- `registrations` table

### 2. Database Configuration

Update the database connection settings in `src/main/java/com/eventmanagement/util/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/event_management";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password";
```

### 3. Project Structure

```
event-management-system/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/eventmanagement/
│       │       ├── model/          # Data models
│       │       ├── dao/            # Data Access Objects
│       │       ├── servlet/        # Servlets
│       │       └── util/           # Utility classes
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml         # Servlet configuration
│           ├── css/
│           │   └── style.css       # Stylesheets  
│           ├── index.html          # Login page
│           ├── events.html         # Events listing page
│           └── admin.html          # Admin dashboard
├── database/
│   └── schema.sql                  # Database schema
├── pom.xml                         # Maven configuration
└── README.md
```

### 4. Build and Run

#### Option 1: Using Maven Tomcat Plugin

```bash
# Navigate to project directory
cd event-management-system

# Clean and compile
mvn clean compile

# Run on Jetty localhost
mvn jetty:run
```

#### Option 2: Deploy to Tomcat Server

```bash
# Build WAR file
mvn clean package

# Deploy the generated WAR file to Tomcat's webapps directory
# Copy target/event-management-system.war to $TOMCAT_HOME/webapps/
```

### 5. Access the Application

Open your web browser and navigate to:
- **Local Maven Tomcat**: `http://localhost:8080/event-management`
- **Deployed Tomcat**: `http://localhost:8080/event-management-system`

## Default Login Credentials

### Admin Account
- **Username**: admin
- **Password**: admin123
- **Access**: Full admin dashboard with event management

### User Account
- **Username**: user1
- **Password**: user123
- **Access**: Event browsing and registration

## Usage Guide

### For Users
1. Login with user credentials
2. Browse available events on the events page
3. Click "Register Now" to register for an event
4. Fill in the registration form with your details
5. Submit to complete registration

### For Administrators
1. Login with admin credentials
2. Access the admin dashboard
3. **Manage Events Tab**:
   - View all events
   - Add new events using "Add New Event" button
   - Edit existing events
   - Delete events
4. **View Registrations Tab**:
   - View all event registrations
   - See registration details

## API Endpoints

- `POST /login` - User/Admin authentication
- `GET /events` - Retrieve all events (JSON)
- `POST /events` - Add/Update/Delete events
- `POST /register` - Register user for an event

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('user', 'admin') NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Events Table
```sql
CREATE TABLE events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    date DATE NOT NULL,
    venue VARCHAR(200) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Registrations Table
```sql
CREATE TABLE registrations (
    reg_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    event_id INT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `DatabaseConnection.java`
   - Ensure the database and tables exist

2. **Servlet Not Found Error**
   - Verify `web.xml` configuration
   - Check servlet mappings
   - Ensure proper package structure

3. **Build Errors**
   - Ensure JDK 11+ is installed
   - Check Maven installation
   - Verify all dependencies in `pom.xml`

4. **Port Already in Use**
   - Change port in `pom.xml` or stop conflicting services
   - Use `mvn tomcat7:run -Dmaven.tomcat.port=8081`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For support and questions, please create an issue in the repository or contact the development team.

package com.eventmanagement.servlet;

import com.eventmanagement.dao.RegistrationDAO;
import com.eventmanagement.model.Registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling registration operations
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private RegistrationDAO registrationDAO = new RegistrationDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        
        if (userName == null || email == null || phone == null) {
            out.println("<script>alert('All fields are required!'); window.location.href='events.html';</script>");
            return;
        }
        
        // Check if user is already registered
        if (registrationDAO.isUserRegistered(email, eventId)) {
            out.println("<script>alert('You are already registered for this event!'); window.location.href='events.html';</script>");
            return;
        }
        
        Registration registration = new Registration();
        registration.setUserName(userName);
        registration.setEmail(email);
        registration.setPhone(phone);
        registration.setEventId(eventId);
        
        if (registrationDAO.registerUser(registration)) {
            out.println("<script>alert('Registration successful!'); window.location.href='events.html';</script>");
        } else {
            out.println("<script>alert('Error registering for event!'); window.location.href='events.html';</script>");
        }
    }
}

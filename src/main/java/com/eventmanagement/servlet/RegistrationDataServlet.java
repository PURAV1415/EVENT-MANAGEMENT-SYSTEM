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
import java.util.List;

/**
 * Servlet for handling registration data requests
 */
@WebServlet("/registration-data")
public class RegistrationDataServlet extends HttpServlet {
    private RegistrationDAO registrationDAO = new RegistrationDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        for (int i = 0; i < registrations.size(); i++) {
            Registration registration = registrations.get(i);
            json.append("{");
            json.append("\"regId\":").append(registration.getRegId()).append(",");
            json.append("\"userName\":\"").append(escapeJson(registration.getUserName())).append("\",");
            json.append("\"email\":\"").append(escapeJson(registration.getEmail())).append("\",");
            json.append("\"phone\":\"").append(escapeJson(registration.getPhone())).append("\",");
            json.append("\"eventId\":").append(registration.getEventId()).append(",");
            json.append("\"registeredAt\":\"").append(registration.getRegisteredAt()).append("\"");
            json.append("}");
            
            if (i < registrations.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]");
        out.print(json.toString());
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}

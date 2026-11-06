package com.eventmanagement.servlet;

import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling user login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        
        if (username == null || password == null || userType == null) {
            out.println("<script>alert('All fields are required!'); window.location.href='index.html';</script>");
            return;
        }
        
        User user = userDAO.authenticateUser(username, password);

        if (user != null && user.getRole().equals(userType)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            
            if ("admin".equals(userType)) {
                response.sendRedirect("admin.html");
            } else {
                response.sendRedirect("events.html");
            }
        } else {
            out.println("<script>alert('Invalid credentials or user type!'); window.location.href='index.html';</script>");
        }
    }
}

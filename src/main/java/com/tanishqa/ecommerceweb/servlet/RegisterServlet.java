package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.User;
import com.tanishqa.ecommerceweb.repository.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    // Show the registration form (GET request)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    // Handle the form submission (POST request)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get form values
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // 2. Basic validation
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // 3. Check if username already exists
        if (userDAO.findByUsername(username) != null) {
            request.setAttribute("error", "Username already taken. Please choose another.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // 4. Save the new user
        User newUser = new User(username, password, email);
        boolean saved = userDAO.save(newUser);

        if (saved) {
            // Fetch the saved user (with ID) from DB
            User savedUser = userDAO.findByUsername(username);
            
            // Auto-login: put user in the session
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", savedUser);
            
            // Redirect to products page — user is now logged in
            response.sendRedirect("products");
            return;
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
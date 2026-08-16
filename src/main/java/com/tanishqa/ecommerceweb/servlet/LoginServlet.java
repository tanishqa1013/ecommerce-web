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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    // Show the login form (GET request)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    // Handle the form submission (POST request)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get form values
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. Basic validation
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Please enter both username and password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // 3. Look up the user in database
        User user = userDAO.findByUsername(username);

        // 4. Check if user exists and password matches
        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // 5. Login successful — save user in session
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);

        // 6. Redirect to products page
        response.sendRedirect("products");
    }
}
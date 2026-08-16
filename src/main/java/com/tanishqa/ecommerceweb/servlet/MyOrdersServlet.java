package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Order;
import com.tanishqa.ecommerceweb.model.User;
import com.tanishqa.ecommerceweb.repository.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Login required
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("login");
            return;
        }

        // Fetch all orders for this user
        List<Order> orders = orderDAO.getOrdersByUserId(loggedInUser.getId());

        request.setAttribute("orders", orders);
        request.setAttribute("loggedInUser", loggedInUser);
        request.getRequestDispatcher("/WEB-INF/views/my-orders.jsp").forward(request, response);
    }
}
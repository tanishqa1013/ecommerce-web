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

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Login required
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("login");
            return;
        }

        // 2. Get order ID from URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("products");
            return;
        }

        // 3. Fetch the order
        int orderId = Integer.parseInt(idParam);
        Order order = orderDAO.getOrderById(orderId);

        // 4. Security check — only show order to the user who placed it
        if (order == null || order.getUserId() != loggedInUser.getId()) {
            response.sendRedirect("products");
            return;
        }

        // 5. Show the confirmation page
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/views/order-confirmation.jsp").forward(request, response);
    }
}

package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Cart;
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

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    // Show the checkout form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 1. Check if user is logged in
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("login");
            return;
        }

        // 2. Check if cart has items
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // 3. Show checkout page
        request.setAttribute("cart", cart);
        request.setAttribute("loggedInUser", loggedInUser);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    // Handle form submission — place the order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 1. Check login
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("login");
            return;
        }

        // 2. Check cart
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // 3. Get form values
        String shippingName = request.getParameter("shippingName");
        String shippingAddress = request.getParameter("shippingAddress");
        String shippingPhone = request.getParameter("shippingPhone");
        String paymentMethod = request.getParameter("paymentMethod");

        // 4. Basic validation
        if (isEmpty(shippingName) || isEmpty(shippingAddress) ||
            isEmpty(shippingPhone) || isEmpty(paymentMethod)) {
            request.setAttribute("error", "Please fill in all fields.");
            request.setAttribute("cart", cart);
            request.setAttribute("loggedInUser", loggedInUser);
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
            return;
        }

        // 5. Build the order
        Order order = new Order();
        order.setUserId(loggedInUser.getId());
        order.setUsername(loggedInUser.getUsername());
        order.setTotal(cart.getTotal());
        order.setShippingName(shippingName.trim());
        order.setShippingAddress(shippingAddress.trim());
        order.setShippingPhone(shippingPhone.trim());
        order.setPaymentMethod(paymentMethod);

        // 6. Place the order (saves to DB + reduces stock in one transaction)
        int orderId = orderDAO.placeOrder(order, cart.getItems());

        if (orderId > 0) {
            // 7. Clear the cart
            cart.clear();
            // 8. Redirect to confirmation page
            response.sendRedirect("order-confirmation?id=" + orderId);
        } else {
            // Something went wrong
            request.setAttribute("error", "Failed to place order. Please try again.");
            request.setAttribute("cart", cart);
            request.setAttribute("loggedInUser", loggedInUser);
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
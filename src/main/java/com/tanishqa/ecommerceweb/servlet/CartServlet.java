package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Cart;
import com.tanishqa.ecommerceweb.model.Product;
import com.tanishqa.ecommerceweb.repository.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get or create the cart from session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Figure out what action to perform (default = view)
        String action = request.getParameter("action");
        if (action == null) action = "view";

        switch (action) {
            case "add":
                handleAdd(request, cart);
                response.sendRedirect("cart");
                return;

            case "update":
                handleUpdate(request, cart);
                response.sendRedirect("cart");
                return;

            case "remove":
                handleRemove(request, cart);
                response.sendRedirect("cart");
                return;

            case "view":
            default:
                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        }
    }

    // Add a product to the cart
    private void handleAdd(HttpServletRequest request, Cart cart) {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(
                    request.getParameter("qty") == null ? "1" : request.getParameter("qty"));

            Product product = productDAO.getProductById(productId);
            if (product != null && quantity > 0) {
                cart.addItem(product, quantity);
            }
        } catch (NumberFormatException e) {
            // Invalid input — silently ignore
        }
    }

    // Update the quantity of a product in the cart
    private void handleUpdate(HttpServletRequest request, Cart cart) {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            int newQuantity = Integer.parseInt(request.getParameter("qty"));
            cart.updateQuantity(productId, newQuantity);
        } catch (NumberFormatException e) {
            // Invalid input — silently ignore
        }
    }

    // Remove a product from the cart
    private void handleRemove(HttpServletRequest request, Cart cart) {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            cart.removeItem(productId);
        } catch (NumberFormatException e) {
            // Invalid input — silently ignore
        }
    }
}
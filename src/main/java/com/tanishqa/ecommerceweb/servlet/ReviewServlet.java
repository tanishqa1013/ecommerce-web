package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Review;
import com.tanishqa.ecommerceweb.model.User;
import com.tanishqa.ecommerceweb.repository.ReviewDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check if user is logged in
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            // Not logged in — redirect to login page
            response.sendRedirect("login");
            return;
        }

        // 2. Get action (add or delete)
        String action = request.getParameter("action");
        if (action == null) action = "add";

        String productIdParam = request.getParameter("productId");
        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendRedirect("products");
            return;
        }

        int productId = Integer.parseInt(productIdParam);

        if (action.equals("delete")) {
            handleDelete(request, loggedInUser);
        } else {
            handleAddOrUpdate(request, loggedInUser, productId);
        }

        // Redirect back to the product page
        response.sendRedirect("product?id=" + productId);
    }

    private void handleAddOrUpdate(HttpServletRequest request, User user, int productId) {
        try {
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            // Basic validation
            if (rating < 1 || rating > 5) return;
            if (comment == null) comment = "";
            comment = comment.trim();

            Review review = new Review(productId, user.getId(), user.getUsername(), rating, comment);
            reviewDAO.saveOrUpdate(review);
        } catch (NumberFormatException e) {
            // Invalid rating — silently ignore
        }
    }

    private void handleDelete(HttpServletRequest request, User user) {
        try {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            reviewDAO.deleteReview(reviewId, user.getId());
        } catch (NumberFormatException e) {
            // Invalid — silently ignore
        }
    }
}
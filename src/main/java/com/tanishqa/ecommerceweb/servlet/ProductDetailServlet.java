package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Product;
import com.tanishqa.ecommerceweb.model.Review;
import com.tanishqa.ecommerceweb.model.User;
import com.tanishqa.ecommerceweb.repository.ProductDAO;
import com.tanishqa.ecommerceweb.repository.ReviewDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get product ID from URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("products");
            return;
        }

        // 2. Fetch the product
        int id = Integer.parseInt(idParam);
        Product product = productDAO.getProductById(id);
        if (product == null) {
            response.sendRedirect("products");
            return;
        }

        // 3. Fetch all reviews for this product
        List<Review> reviews = reviewDAO.getReviewsByProductId(id);
        double avgRating = reviewDAO.getAverageRating(id);
        int reviewCount = reviewDAO.getReviewCount(id);

        // 4. Check if logged-in user has already reviewed this product
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Review userReview = null;
        if (loggedInUser != null) {
            userReview = reviewDAO.getUserReview(id, loggedInUser.getId());
        }

        // 5. Pass everything to the JSP
        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("avgRating", avgRating);
        request.setAttribute("reviewCount", reviewCount);
        request.setAttribute("userReview", userReview);
        request.setAttribute("loggedInUser", loggedInUser);

        request.getRequestDispatcher("/WEB-INF/views/product-detail.jsp").forward(request, response);
    }
}
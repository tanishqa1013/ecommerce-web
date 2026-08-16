package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.Product;
import com.tanishqa.ecommerceweb.repository.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Fetch all products from the database
        List<Product> products = productDAO.getAllProducts();

        // 2. Attach the products list to the request (so the JSP can access it)
        request.setAttribute("products", products);

        // 3. Forward the request to products.jsp for display
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }
}
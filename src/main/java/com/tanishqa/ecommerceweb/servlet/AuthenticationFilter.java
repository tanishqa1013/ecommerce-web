package com.tanishqa.ecommerceweb.servlet;

import com.tanishqa.ecommerceweb.model.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    // URLs that DON'T need login (public pages)
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/login",
        "/register",
        "/logout",
        "/index.jsp",
        "/"
    );

    // File types that are always allowed (CSS, images, JS)
    private static final List<String> PUBLIC_EXTENSIONS = Arrays.asList(
        ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", ".svg", ".woff", ".woff2"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Allow public paths
        if (PUBLIC_PATHS.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Allow static resources (CSS, images, etc.)
        for (String ext : PUBLIC_EXTENSIONS) {
            if (path.toLowerCase().endsWith(ext)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // For all other pages — check login
        HttpSession session = req.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            // Not logged in — redirect to login page
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Logged in — allow access
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Check if user is already logged in
    Object user = session.getAttribute("loggedInUser");
    if (user != null) {
        response.sendRedirect("products");
    } else {
        response.sendRedirect("login");
    }
%>
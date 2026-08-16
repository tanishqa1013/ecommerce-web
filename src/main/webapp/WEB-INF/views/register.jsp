<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Register - Mini E-Commerce</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <div class="header">🛒 Mini E-Commerce Store</div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <div class="user-greeting-left">
            👋 Hi, <strong>${sessionScope.loggedInUser.username}</strong>
        </div>
    </c:if>

    <div class="top-nav">
        <a href="products">🏠 Home</a>
        <a href="cart">🛍️ Cart</a>
        <a href="my-orders">📜 My Orders</a>
        <a href="login">👤 Login</a>
        <a href="register"> 📝 Register</a>
    </div>

    <div class="form-container">
        <h2>✨ Create Your Account</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>

        <form action="register" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required/>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required/>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required/>
            </div>

            <button type="submit" class="register-btn">Register 🎉</button>
        </form>

        <div class="divider"></div>

        <div class="footer-link">
            Already have an account? <a href="login">Sign in</a>
        </div>
        <div class="footer-link">
            <a href="products">← Continue shopping without registering</a>
        </div>
    </div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
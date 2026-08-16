<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order Confirmed - Mini E-Commerce</title>
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
        <a href="register">📝 Register</a>
    </div>

    <div class="container">
        <div class="confirmation-container">
            <div class="confirmation-icon">🎉</div>
            <h1 class="confirmation-title">Order Placed Successfully!</h1>
            <p class="confirmation-subtitle">Thank you, ${order.username}! Your order is confirmed.</p>

            <div class="confirmation-details">
                <div class="detail-row">
                    <span class="detail-label">Order ID:</span>
                    <span class="detail-value">#${order.id}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Order Date:</span>
                    <span class="detail-value">${order.formattedDate}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Status:</span>
                    <span class="detail-value status-badge">${order.status}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Total Amount:</span>
                    <span class="detail-value total-amount">Rs. ${order.total}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Payment Method:</span>
                    <span class="detail-value">${order.paymentMethod}</span>
                </div>
            </div>

            <div class="shipping-details">
                <h3>📦 Shipping To</h3>
                <div class="shipping-info">
                    <div><strong>${order.shippingName}</strong></div>
                    <div>${order.shippingAddress}</div>
                    <div>📞 ${order.shippingPhone}</div>
                </div>
            </div>

            <div class="confirmation-message">
                <p>✨ Your order will be delivered in <strong>3-5 business days</strong>.</p>
                <p>A confirmation email will be sent to you shortly.</p>
            </div>

            <div class="confirmation-actions">
                <a href="products" class="continue-shopping">🛍️ Continue Shopping</a>
            </div>
        </div>
    </div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
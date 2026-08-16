<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>My Orders - Mini E-Commerce</title>
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
        <h2>📜 My Orders</h2>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="empty-cart">
                    <h3>😢 You haven't placed any orders yet</h3>
                    <p>Start shopping to see your orders here!</p>
                    <br/>
                    <a href="products" class="continue-shopping">✨ Browse Products</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="orders-list">
                    <c:forEach var="order" items="${orders}">
                        <div class="order-card">
                            <div class="order-card-header">
                                <div>
                                    <div class="order-id-label">Order ID</div>
                                    <div class="order-id-value">#${order.id}</div>
                                </div>
                                <div>
                                    <div class="order-id-label">Date</div>
                                    <div class="order-date-value">${order.formattedDate}</div>
                                </div>
                                <div>
                                    <div class="order-id-label">Total</div>
                                    <div class="order-total-value">Rs. ${order.total}</div>
                                </div>
                                <div>
                                    <div class="order-id-label">Status</div>
                                    <span class="status-badge">${order.status}</span>
                                </div>
                            </div>

                            <div class="order-card-body">
                                <div class="order-shipping">
                                    <strong>📦 Shipping to:</strong> ${order.shippingName}, ${order.shippingAddress}
                                </div>
                                <div class="order-payment">
                                    <strong>💳 Payment:</strong> ${order.paymentMethod}
                                </div>
                            </div>

                            <div class="order-card-actions">
                                <a href="order-confirmation?id=${order.id}" class="view-order-btn">View Full Details →</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
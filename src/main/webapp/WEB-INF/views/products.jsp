<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Products - Mini E-Commerce</title>
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
        <h2>All Products</h2>

        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <a href="product?id=${product.id}" class="product-card">
                    <img src="${product.mainImage}" alt="${product.name}" class="product-image"/>
                    <div class="product-info">
                        <div class="product-name">${product.name}</div>
                        <span class="product-category">${product.category}</span>
                        <div class="product-price">Rs. ${product.price}</div>
                        <div class="product-stock ${product.stock lt 10 ? 'low' : ''}">
                            <c:choose>
                                <c:when test="${product.stock lt 10}">🔥 Only ${product.stock} left!</c:when>
                                <c:otherwise>✅ In stock (${product.stock} available)</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
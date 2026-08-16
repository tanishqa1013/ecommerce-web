<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Checkout - Mini E-Commerce</title>
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
        <h2>💳 Checkout</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="checkout-layout">
            <!-- LEFT: shipping form -->
            <div class="checkout-form-section">
                <h3>📦 Shipping Details</h3>
                <form action="checkout" method="post" class="checkout-form">
                    <div class="form-group">
                        <label for="shippingName">Full Name</label>
                        <input type="text" id="shippingName" name="shippingName" 
                               value="${loggedInUser.username}" required/>
                    </div>

                    <div class="form-group">
                        <label for="shippingAddress">Delivery Address</label>
                        <textarea id="shippingAddress" name="shippingAddress" rows="3"
                                  placeholder="House no, street, city, state, PIN code" required></textarea>
                    </div>

                    <div class="form-group">
                        <label for="shippingPhone">Phone Number</label>
                        <input type="text" id="shippingPhone" name="shippingPhone"
                               placeholder="10-digit mobile number" pattern="[0-9]{10}" required/>
                    </div>

                    <div class="form-group">
                        <label>Payment Method</label>
                        <div class="payment-options">
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="Cash on Delivery" checked/>
                                <span>💰 Cash on Delivery</span>
                            </label>
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="UPI"/>
                                <span>📱 UPI</span>
                            </label>
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="Credit/Debit Card"/>
                                <span>💳 Credit/Debit Card</span>
                            </label>
                            <label class="payment-option">
                                <input type="radio" name="paymentMethod" value="Net Banking"/>
                                <span>🏦 Net Banking</span>
                            </label>
                        </div>
                    </div>

                    <button type="submit" class="place-order-btn">🎉 Place Order</button>
                </form>
            </div>

            <!-- RIGHT: order summary -->
            <div class="checkout-summary">
                <h3>📋 Order Summary</h3>
                <div class="summary-items">
                    <c:forEach var="item" items="${cart.items}">
                        <div class="summary-item">
                            <img src="${item.product.imageUrl}" alt="${item.product.name}"/>
                            <div class="summary-item-info">
                                <div class="summary-item-name">${item.product.name}</div>
                                <div class="summary-item-qty">Qty: ${item.quantity}</div>
                            </div>
                            <div class="summary-item-price">Rs. ${item.subtotal}</div>
                        </div>
                    </c:forEach>
                </div>

                <div class="summary-totals">
                    <div class="summary-row">
                        <span>Subtotal (${cart.itemCount} items)</span>
                        <span>Rs. ${cart.total}</span>
                    </div>
                    <div class="summary-row">
                        <span>Shipping</span>
                        <span style="color: #10b981;">FREE</span>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <span>Rs. ${cart.total}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
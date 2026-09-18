<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Shopping Cart - Mini E-Commerce</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <div class="header">🛒 Mini E-Commerce Store</div>

    <div class="top-nav">
        <a href="products">🏠 Home</a>
        <a href="cart">🛍️ Cart</a>
        <a href="my-orders">📜 My Orders</a>
        <a href="login">👤 Login</a>
        <a href="register">📝 Register</a>
    </div>

    <div class="container">
        <h2>🛍️ Your Shopping Cart</h2>

        <c:choose>
            <c:when test="${empty cart or empty cart.items}">
                <div class="empty-cart">
                    <h3>😢 Your cart is empty</h3>
                    <p>Add some products to get started!</p>
                    <br/>
                    <a href="products" class="continue-shopping">✨ Browse Products</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-content">
                    <div class="cart-items">
                        <c:forEach var="item" items="${cart.items}">
                            <div class="cart-item">
                                <img src="${item.product.mainImage}" alt="${item.product.name}"/>
                                <div class="item-info">
                                    <h3>${item.product.name}</h3>
                                    <span class="category">${item.product.category}</span>
                                    <div class="price">Rs. ${item.product.price}</div>
                                    <div class="subtotal">Subtotal: Rs. ${item.subtotal}</div>
                                    <div class="item-controls">
                                        <form action="cart" method="get" class="qty-form">
                                            <input type="hidden" name="action" value="update"/>
                                            <input type="hidden" name="id" value="${item.product.id}"/>
                                            <label>Qty:</label>
                                            <input type="number" name="qty" value="${item.quantity}" min="1" max="${item.product.stock}"/>
                                            <button type="submit" class="btn-update">Update</button>
                                        </form>
                                        <a href="#" class="btn-remove" 
   onclick="animateRemove(event, this, ${item.product.id}, '${item.product.mainImage}')">🗑️ Remove</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="cart-summary">
                        <h3>📋 Order Summary</h3>
                        <div>Items: ${cart.itemCount}</div>
                        <div class="total-row">
                            <span>Total:</span>
                            <span class="total-value">Rs. ${cart.total}</span>
                        </div>
                        <a href="checkout" class="checkout-btn">🎉 Proceed to Checkout</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Floating trash bin (only shows during animation) -->
    <div id="trashBin" class="trash-bin">🗑️</div>

    <script>
        function animateRemove(event, linkEl, productId, imgUrl) {
            event.preventDefault();

            // Find the cart-item container
            const cartItem = linkEl.closest('.cart-item');
            const productImg = cartItem.querySelector('img');
            const trashBin = document.getElementById('trashBin');

            // Get position of product image
            const imgRect = productImg.getBoundingClientRect();

            // Create a floating clone
            const clone = productImg.cloneNode(true);
            clone.classList.add('flying-image');
            clone.style.top = imgRect.top + 'px';
            clone.style.left = imgRect.left + 'px';
            clone.style.width = imgRect.width + 'px';
            clone.style.height = imgRect.height + 'px';
            document.body.appendChild(clone);

            // Show the trash bin
            trashBin.classList.add('visible');

            // Wait one frame, then trigger the fly animation
            requestAnimationFrame(() => {
                const trashRect = trashBin.getBoundingClientRect();
                clone.style.top = trashRect.top + 20 + 'px';
                clone.style.left = trashRect.left + 20 + 'px';
                clone.style.width = '20px';
                clone.style.height = '20px';
                clone.style.opacity = '0';
                clone.style.transform = 'rotate(360deg)';
            });

            // After animation, bounce the trash and navigate to remove URL
            setTimeout(() => {
                trashBin.classList.add('bounce');
                clone.remove();
            }, 800);

            setTimeout(() => {
                window.location.href = 'cart?action=remove&id=' + productId;
            }, 1200);
        }
    </script>
</body>
</html>


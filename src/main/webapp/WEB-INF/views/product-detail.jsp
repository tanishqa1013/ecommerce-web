<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${product.name} - Mini E-Commerce</title>
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

    <a href="products" class="back-link">← Back to All Products</a>

    <div class="detail-container">
        <!-- Top row: image on left, name + category + description on right -->
        <div class="detail-top">
            <div class="scroll-gallery">
                <div class="scroll-track" id="scrollTrack">
                    <c:forEach var="img" items="${product.images}">
                        <img src="${img}" alt="${product.name}" class="scroll-slide"/>
                    </c:forEach>
                </div>

                <div class="scroll-dots" id="scrollDots">
                    <c:forEach var="img" items="${product.images}" varStatus="status">
                        <span class="scroll-dot ${status.first ? 'active' : ''}" onclick="scrollToImage(${status.index})"></span>
                    </c:forEach>
                </div>
            </div>

            <div class="detail-title">
                <h1 class="product-name-large">${product.name}</h1>
                <span class="product-category-large">${product.category}</span>

                <!-- Rating summary -->
                <c:if test="${reviewCount > 0}">
                    <div class="rating-summary">
                        <div class="stars-display" data-rating="${avgRating}">
                            <c:forEach begin="1" end="5" var="i">
                                <span class="star ${i <= avgRating ? 'filled' : ''}">★</span>
                            </c:forEach>
                        </div>
                        <span class="rating-text">
                            <fmt:formatNumber value="${avgRating}" maxFractionDigits="1" xmlns:fmt="jakarta.tags.fmt"/>
                            <c:set var="displayRating" value="${avgRating}"/>
                            ${displayRating.toString().substring(0, displayRating.toString().indexOf('.') + 2)}
                            out of 5 · ${reviewCount} review${reviewCount > 1 ? 's' : ''}
                        </span>
                    </div>
                </c:if>
                <c:if test="${reviewCount == 0}">
                    <div class="rating-summary">
                        <div class="stars-display">
                            <span class="star">★</span>
                            <span class="star">★</span>
                            <span class="star">★</span>
                            <span class="star">★</span>
                            <span class="star">★</span>
                        </div>
                        <span class="rating-text">No reviews yet</span>
                    </div>
                </c:if>

                <div class="product-desc-large">${product.description}</div>
            </div>
        </div>

        <!-- Bottom row: price, stock, buttons -->
        <div class="detail-bottom">
            <div class="product-price-large">Rs. ${product.price}</div>
            <div class="product-stock-large ${product.stock lt 10 ? 'low' : ''}">
                <c:choose>
                    <c:when test="${product.stock lt 10}">🔥 Hurry! Only ${product.stock} left in stock</c:when>
                    <c:otherwise>✅ In stock — ${product.stock} available</c:otherwise>
                </c:choose>
            </div>
            <div class="detail-actions">
                <a href="cart?action=add&id=${product.id}&qty=1" class="cart-button">🛒 Add to Cart</a>
                <button class="buy-button" onclick="alert('Buy feature coming soon!')">⚡ Buy Now</button>
            </div>
        </div>
    </div>

    <!-- REVIEWS SECTION -->
    <div class="reviews-section">
        <h2>Customer Reviews</h2>

        <!-- Write a review form (only for logged-in users) -->
        <c:choose>
            <c:when test="${empty loggedInUser}">
                <div class="review-login-prompt">
                    <p>👤 <a href="login">Log in</a> to write a review</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="review-form-container">
                    <h3>
                        <c:choose>
                            <c:when test="${not empty userReview}">✏️ Edit your review</c:when>
                            <c:otherwise>✍️ Write a review</c:otherwise>
                        </c:choose>
                    </h3>
                    <form action="review" method="post" class="review-form">
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <input type="hidden" name="action" value="add"/>

                        <div class="star-selector">
                            <label>Your rating:</label>
                            <div class="star-picker">
                                <c:forEach begin="1" end="5" var="i">
                                    <input type="radio" name="rating" id="star${i}" value="${i}"
                                           ${userReview != null && userReview.rating == i ? 'checked' : (i == 5 && userReview == null ? 'checked' : '')}/>
                                    <label for="star${i}" class="star-label">★</label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="comment">Your review:</label>
                            <textarea name="comment" id="comment" rows="4" required
                                      placeholder="Share your experience with this product...">${userReview.comment}</textarea>
                        </div>

                        <button type="submit" class="cart-button">
                            <c:choose>
                                <c:when test="${not empty userReview}">Update Review</c:when>
                                <c:otherwise>Submit Review</c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- List of all reviews -->
        <div class="reviews-list">
            <c:choose>
                <c:when test="${empty reviews}">
                    <div class="no-reviews">
                        <p>💭 No reviews yet. Be the first to review!</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-card">
                            <div class="review-header">
                                <div class="reviewer-info">
                                    <div class="reviewer-avatar">
                                        ${fn:toUpperCase(fn:substring(review.username, 0, 1))}
                                    </div>
                                    <div>
                                        <div class="reviewer-name">${review.username}</div>
                                        <div class="review-date">${review.formattedDate}</div>
                                    </div>
                                </div>
                                <div class="review-stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <span class="star ${i <= review.rating ? 'filled' : ''}">★</span>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="review-comment">${review.comment}</div>

                            <!-- Show delete button for own review -->
                            <c:if test="${not empty loggedInUser and loggedInUser.id == review.userId}">
                                <form action="review" method="post" class="review-delete-form"
                                      onsubmit="return confirm('Are you sure you want to delete your review?');">
                                    <input type="hidden" name="action" value="delete"/>
                                    <input type="hidden" name="productId" value="${product.id}"/>
                                    <input type="hidden" name="reviewId" value="${review.id}"/>
                                    <button type="submit" class="btn-remove">🗑️ Delete my review</button>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        const track = document.getElementById('scrollTrack');
        const dots = document.querySelectorAll('.scroll-dot');

        function scrollToImage(index) {
            const slideWidth = track.clientWidth;
            track.scrollTo({ left: slideWidth * index, behavior: 'smooth' });
        }

        track.addEventListener('scroll', () => {
            const slideWidth = track.clientWidth;
            const activeIndex = Math.round(track.scrollLeft / slideWidth);
            dots.forEach((d, i) => {
                d.classList.toggle('active', i === activeIndex);
            });
        });

        // Mouse drag support
        let isDown = false;
        let startX;
        let scrollLeft;

        track.addEventListener('mousedown', (e) => {
            isDown = true;
            track.style.cursor = 'grabbing';
            startX = e.pageX - track.offsetLeft;
            scrollLeft = track.scrollLeft;
        });

        track.addEventListener('mouseleave', () => { isDown = false; track.style.cursor = 'grab'; });
        track.addEventListener('mouseup', () => { isDown = false; track.style.cursor = 'grab'; });

        track.addEventListener('mousemove', (e) => {
            if (!isDown) return;
            e.preventDefault();
            const x = e.pageX - track.offsetLeft;
            const walk = (x - startX) * 2;
            track.scrollLeft = scrollLeft - walk;
        });
    </script>
    <c:if test="${not empty sessionScope.loggedInUser}">
        <a href="logout" class="floating-logout">🚪 Logout</a>
    </c:if>
</body>
</html>
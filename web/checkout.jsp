<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <style>
        /* CSS nh? c? */
        .checkout-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            font-family: Arial, sans-serif;
        }
        .order-summary {
            background: #f9f9f9;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .checkout-item {
            display: flex;
            justify-content: space-between;
            padding: 15px 0;
            border-bottom: 1px solid #eee;
        }
        .item-name {
            flex: 2;
        }
        .item-price, .item-quantity, .item-total {
            flex: 1;
            text-align: right;
        }
        .total-section {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 2px solid #ddd;
            text-align: right;
            font-weight: bold;
            font-size: 1.2em;
        }
        .section-title {
            font-size: 1.5em;
            margin-bottom: 20px;
            color: #333;
        }
        .shipping-info {
            margin: 20px 0;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .confirm-button {
            background: #4CAF50;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            float: right;
            margin: 20px 20px;
            font-size: 16px;
            transition: background 0.3s ease;
        }
        .confirm-button:hover {
            background: #45a049;
        }
    </style>
    <script>
        // ??i tài li?u t?i xong
        document.addEventListener("DOMContentLoaded", function() {
            // L?y checkbox và các tr??ng input
            const checkbox = document.getElementById('enableForm');
            const inputs = document.querySelectorAll('input[type="text"], input[type="tel"]');

            // ??t m?c ??nh: các tr??ng input b? vô hi?u hóa
            inputs.forEach(input => {
                input.disabled = true;
            });

            // G?n s? ki?n change cho checkbox
            checkbox.addEventListener('change', function () {
                const isEnabled = checkbox.checked;

                // Kích ho?t ho?c vô hi?u hóa các tr??ng input
                inputs.forEach(input => {
                    input.disabled = !isEnabled;
                });
            });
        });
    </script>
</head>
<body>
    <div class="checkout-container">
        <h1 class="section-title">Order Summary</h1>

        <!-- Hi?n th? l?i n?u có -->
        <c:if test="${not empty sessionScope.error}">
            <div class="error-message">
                ${sessionScope.error}
            </div>
        </c:if>

        <!-- N?u gi? hàng tr?ng -->
        <c:if test="${empty sessionScope.selectedItems}">
            <div class="empty-cart">
                <h2>No items selected for checkout</h2>
                <p>Please return to cart and select items to checkout</p>
                <a href="cart" class="confirm-button" style="float: none; display: inline-block; margin-top: 10px;">Return to Cart</a>
            </div>
        </c:if>

        <!-- N?u có s?n ph?m -->
        <c:if test="${not empty sessionScope.selectedItems}">
            <div class="order-summary">
                <div class="checkout-item">
                    <div class="item-name"><strong>Item</strong></div>
                    <div class="item-price"><strong>Price</strong></div>
                    <div class="item-quantity"><strong>Quantity</strong></div>
                    <div class="item-total"><strong>Total</strong></div>
                </div>

                <c:set var="grandTotal" value="${sessionScope.grandTotal}"/>
                <c:forEach var="selectedItem" items="${sessionScope.selectedItems}">
                    <div class="checkout-item">
                        <div class="item-name">${selectedItem.name}</div>
                        <div class="item-price">$${selectedItem.price}</div>
                        <div class="item-quantity">${selectedItem.quantity}</div>
                        <div class="item-total">$${selectedItem.total}</div>
                    </div>
                </c:forEach>

                <div class="total-section">
                    <div>Grand Total: $${grandTotal}</div>
                </div>
                <form action="checkout" method="post">
                    <input type="hidden" name="action" value="cart_page">
                    <button type="submit" class="confirm-button">Shopping Cart</button>
                </form>
                <form action="order" method="post">
                    <input type="hidden" name="action" value="order_page">
                    <button type="submit" class="confirm-button">Continue Dish</button>
                </form>
            </div>

            <div class="shipping-info">
                <h2>Shipping Information</h2>
                <form action="checkout" method="post">
                    <!-- Checkbox kích ho?t form -->
                    <div>
                        <input type="checkbox" id="enableForm" name="enableForm">
                        <label for="enableForm">Select if you want to use the new information to place your order</label>
                    </div>
                    
                    <!-- Các tr??ng nh?p thông tin giao hàng -->
                    <div class="form-group">
                        <label for="name">Full Name:</label>
                        <input type="text" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Delivery Address:</label>
                        <input type="text" id="address" name="address" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone Number:</label>
                        <input type="tel" id="phone" name="phone" required>
                    </div>

                    <input type="hidden" name="total" value="${grandTotal}">
                    <input type="hidden" name="action" value="order_completed">
                    <button type="submit" class="confirm-button">Order completed</button>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>

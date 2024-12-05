
<%@page import="java.util.GregorianCalendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="styles/header.css" type="text/css"/>
    <link rel="stylesheet" href="styles/footer.css" type="text/css"/>
    <style>
        
        html, body {
            margin: 0;
            padding: 0;
            min-height: 100%;
        }

        .container_cart{
            display: flex;
            justify-content: space-around;
            flex: 1; /* Chiếm toàn bộ không gian còn lại khi có ít dữ liệu */
            padding: 20px;
        }
        .cart-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
            max-width: 900px;
            margin: 0 auto;
            padding-top: 120px;
            display: flex;
            
            min-height: 75vh;
            
        }
        .cart-item {
            display: flex;
            height: 200px;
            align-items: center;
            padding: 15px ;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            
        }
        .item-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-right: 20px;
        }
        .item-details {
            padding-left: 30px;
            flex-grow: 1;
        }
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .action-button {
            padding: 5px 10px;
            margin: 0 5px;
            cursor: pointer;
        }
        .remove-btn {
            padding: 5px 15px;
            background: #ff4444;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        
        .total-section{
            padding-top: 100px;
            width: 300px;
            height: 200px;
            text-align: center;
        }
        .total-section h2{
            padding-bottom: 20px;
        }
        
        
        
        
        .notification {
            position: fixed;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            background-color: #a7eba7; /* Màu nền cho thông báo (có thể tùy chỉnh) */
            color: white;
            padding: 15px 30px;
            border-radius: 5px;
            z-index: 1000; /* Đảm bảo nằm trên mọi thành phần khác */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Tạo đổ bóng */
            transition: opacity 0.5s ease; /* Hiệu ứng mờ dần */
        }

        .notification h3 {
            margin: 0;
            font-size: 16px;
            text-align: center;
        }
        .item-details span {
            color: #e44d26;
            font-weight: bold;
        }
        
        item-details h3 {
            padding-bottom: 30px;
        }

        
        
        .item-details h3 {
    margin: 0; /* Xóa khoảng cách mặc định trên/dưới thẻ h3 */
    font-size: 18px;
    color: #333;
}




        
    </style>
    
    <script>
    // Khi trang tải xong, thiết lập timeout để ẩn thông báo
    document.addEventListener("DOMContentLoaded", function () {
        const notification = document.getElementById("notification");
        if (notification) {
            // Hiển thị thông báo trong 3 giây
            setTimeout(() => {
                notification.style.opacity = "0"; // Tạo hiệu ứng mờ dần
                setTimeout(() => notification.style.display = "none", 500); // Sau đó ẩn hoàn toàn
            }, 3000);
        }
    });
    
    function validateCheckout() {
    const selectedCheckboxes = document.querySelectorAll('.item-checkbox:checked');
    if (selectedCheckboxes.length === 0) {
        alert('Please select at least one item to checkout');
        return false;
    }
    const selectedValues = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);
    document.getElementById('selectedItemsInput').value = selectedValues.join(',');
    
    // Log the values to console for debugging
    console.log('Selected items:', selectedValues);
    return true;
}
</script>
</head>
<body>
    
    <header>
    <div class="header_container">
        <div class="header_image">
            <img src="images/logo.png" class="header_container_logo" alt="logo"/>
        </div>
        <div class="navbar">
            <ul class="navbar_item">
                <li class="navbar_item_2">
                    <form action="navigate" method="get">
                        <input type="hidden" name="section" value="home">
                        <div class="button_log_in">
                            <input type="submit" value="Home" >
                        </div>
                    </form>
                </li>
                <li class="navbar_item_2">
                    <form action="order" method="post">
                        <input type="hidden" name="action" value="order_page">
                        <div class="button_log_in">
                            <input type="submit" value="Order">
                        </div>
                    </form>
                    
                </li>
                <li class="navbar_item_3">
                    <form action="navigate" method="get">
                        <input type="hidden" name="section" value="about">
                        <div class="button_log_in">
                            <input type="submit" value="About Us" class="nav-button">
                        </div>
                    </form>
                </li>
                <li class="navbar_item_2">
                    <form action="cart" method="get">
                        <input type="hidden" name="action" value="view_cart">
                        <div class="button_log_in">
                            <input type="submit" value="cart">
                        </div>
                    </form>
                </li>
                <li class="navbar_item_3">
                    <a href="#contact" class="button">Contact</a>
                </li>
                <li class="navbar_item_4">
                    <form action="login" method="post">
                        <input type="hidden" name="action" value="login_page">
                        <div class="button_log_in">
                            <input type="submit" value="Log in">
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</header>
    
    
    
    <c:if test="${not empty message}">
        <div id="notification" class="notification">
            <h3>${message}</h3>
        </div>
    </c:if>
    
        <div class="container_cart">
        <div class="cart-container">
            <c:forEach var="item" items="${item}">
                <div class="cart-item">
                    <input type="checkbox" name="selectedItems" value="${item.idcart}" class="item-checkbox">
                    <div class="item-details">
                        <h3>${item.name}</h3>
                        <h3>Price:<span> $${item.price} </span></h3>
                        <div class="quantity-controls">
                            <!-- Decrease quantity form -->
                            <form action="cart" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="update_quantity">
                                <input type="hidden" name="cartId" value="${item.idcart}">
                                <input type="hidden" name="change" value="-1">
                                <input type="submit" class="action-button" value="-">
                            </form>
                            <h3>${item.quantity}</h3>
                            <!-- Increase quantity form -->
                            <form action="cart" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="update_quantity">
                                <input type="hidden" name="cartId" value="${item.idcart}">
                                <input type="hidden" name="change" value="1">
                                <input type="submit" class="action-button" value="+">
                            </form>
                        </div>
                        <h3>Total: $${item.total}</h3>
                        <!-- Remove item form -->
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="remove_item">
                            <input type="hidden" name="cartId" value="${item.idcart}">
                            <input type="submit" class="remove-btn" value="Remove" 
                                   onclick="return confirm('Are you sure you want to remove this item?')">
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="total-section">
            <h2>Your Shopping Cart</h2>
            <c:set var="cartTotal" value="0" />
            <c:forEach var="item" items="${item}">
                <c:set var="cartTotal" value="${cartTotal + item.total}" />
            </c:forEach>
            <h2>Cart Total: $${cartTotal}</h2>
            <!-- Checkout form -->
            <form id="checkoutForm" action="cart" method="post" onsubmit="return validateCheckout()">
                <input type="hidden" name="action" value="checkout">
                <input type="hidden" id="selectedItemsInput" name="selectedItems">
                <input type="submit" value="Proceed to Checkout" 
                       style="padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">
            </form>
            </div>
    </div>

    <div class="footer" id="contact">
    <p>Liên hệ: 0862738237</p>
    <p>Email: nhahang@gmail.com</p>
    <p>Địa chỉ: 218 Gò Xoài, P.Bình Hưng Hòa A, Q.Bình Tân, Tp.HCM</p>
    <%
        // Lấy năm hiện tại bằng GregorianCalendar
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(java.util.Calendar.YEAR);
    %>
    <p><strong>&copy; Copyright <%= currentYear %> Thuộc bản quyền của nhahang.com</strong></p>
</div>

</body>
</html>
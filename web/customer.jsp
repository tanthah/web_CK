<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Form</title>
    <style>
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        header {
            position: fixed;
            top: 0;
            left: 0;
            max-width: 1300px;
            width: 100%;
            height: 80px;
            z-index: 100; /* Đảm bảo header nằm trên các phần tử khác */
            background-color: transparent; /* Nền trong suốt */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.5); /* Tạo bóng để phân cách header với nội dung bên dưới */
        }

        .header_container{
            display: flex;
            position: relative;
            width: 100%;
            height: 80px;
            background-color: white;
            opacity: 0.9; /* Làm mờ toàn bộ phần tử */
            /*background-color: transparent;*/
            justify-content: space-between;
            justify-items: center;
            text-align: center
        }

        .header_image{

        }

        .header_container_logo{
            width: 80px;
            height: 80px;
            border-radius: 50%;
        }

        .navbar{
            width: 600px;
            height: 80px;

        }

        .navbar_item{
            display: flex;
            position: relative;
            justify-content: space-between;
            list-style: none; /* Xóa dấu chấm */
            padding: 20px; /* Loại bỏ khoảng cách mặc định */
            margin: 0;

        }

        .navbar_item_1{

        }

        /* Đặt style cơ bản cho danh sách và liên kết */
        .navbar_item_2 {
            position: relative;
        }

        .button {
            color: #000000;
            text-decoration: none;
            padding: 10px;
            width: 120px;
            height: 40px;
            display: inline-block;
            transition: background-color 0.3s;
            text-align: center;
            border-radius: 5px;
        }

        /* Đổi màu nền của button khi di chuột vào */
        .button:hover {
            background-color: #ffcc00; /* Đổi màu thành màu mong muốn */
        }

        /* Ẩn danh sách con ban đầu */
        .navbar_item_2_menu {
            display: none;
            position: absolute;
            top: 100%;
            left: 0;
            width: 120px;
            font-size: 13px;
            background-color: #fff;
            padding: 5px;
            border: 1px solid #ccc;
            list-style: none;
        }

        /* Hiển thị danh sách con khi di chuột vào button */
        .navbar_item_2:hover .navbar_item_2_menu {
            display: block;
        }

        .navbar_item_2_menu li a {
            display: block;
            padding: 8px;
            color: #333;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .navbar_item_2_menu li a:hover {
            background-color: #e0e0e0; /* Đổi màu của các <li> khi di chuột vào */
        }
        
        .form-container {
            background-color: #fff;
            padding: 10px 30px;
            margin-top: 200px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            font-size: 14px;
            margin-bottom: 5px;
            color: #555;
        }
        input, select {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            width: 100%;
            box-sizing: border-box;
        }
        button {
            background-color: #007bff;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <header>
        <div class="header_container">

            <div class="header_image">
                <img src="images/logo.png" class="header_container_logo" alt="logo"/>

            </div>

            <div class="navbar">
                <ul class="navbar_item">
                    <li class="navbar_item_1">
                        <a href="body.jsp" class="button">Home</a>
                    </li>

                    <li class="navbar_item_2">
                        <a href="vidu.jsp" class="button">Categories</a>
                        <ul class="navbar_item_2_menu">
                            <li>
                                <a href="vidu.jsp">mon trang mieng</a>
                            </li>
                            <li>
                                <a href="vidu.jsp">mon khai vi</a>
                            </li>
                            <li>
                                <a href="vidu.jsp">mon trang mieng</a>
                            </li>
                        </ul>
                    </li>

                    <li class="navbar_item_3">
                        <a href="#contact" class="button">Contact</a>

                    </li>

                    <li class="navbar_item_4">
                        <a href="log_in.jsp" class="button">Log in</a>
                    </li>
                </ul>
            </div>


        </div>
    </header>
    <div class="form-container">
        <form action="processCustomer.jsp" method="post">
            <h1>Customer Information</h1>

            <label for="customerName">Customer Name:</label>
            <input type="text" id="customerName" name="customerName" value="${customer.customerName}" placeholder="Enter your name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${customer.email}" placeholder="Enter your email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${customer.password}" placeholder="Enter your password" required>

            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" value="${customer.phone}" placeholder="Enter your phone number" required>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${customer.address}" placeholder="Enter your address" required>

            <label for="city">City:</label>
            <select id="city" name="city" required>
                <option value="Hanoi" ${customer.city == 'Hanoi' ? 'selected' : ''}>Hà Nội</option>
                <option value="HCMC" ${customer.city == 'HCMC' ? 'selected' : ''}>TP.Hồ Chí Minh</option>
            </select>

            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>

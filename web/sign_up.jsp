

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <style>
        /* CSS cho form đăng ký */
        /* CSS cho form đăng ký */
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .signup-container {
            max-width: 400px;
            margin: 50px auto;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .signup-container h2 {
            text-align: center;
            color: #343a40;
            margin-bottom: 20px;
        }

        .signup-container h3 {
            text-align: center;
            color: #6c757d;
            margin-bottom: 10px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            color: #495057;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-group input:focus,
        .form-group select:focus {
            border-color: #80bdff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
        }

        .signup-button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            color: #ffffff;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
            transition: background-color 0.3s;
        }

        .signup-button:hover {
            background-color: #0056b3;
        }

        .signup-button:active {
            background-color: #004085;
        }

        .signup-container select {
            appearance: none;
            background: #fff url('data:image/svg+xml;charset=US-ASCII,%3Csvg xmlns%3D%27http%3A//www.w3.org/2000/svg%27 viewBox%3D%270 0 4 5%27%3E%3Cpath fill%3D%27%23000%27 d%3D%27M2 0L0 2h4zm0 5L0 3h4z%27/%3E%3C/svg%3E') no-repeat right 10px top 50%;
            background-size: 8px 10px;
        }

        .signup-container select::-ms-expand {
            display: none;
        }


    </style>
</head>
<body>
<div class="body_sigup">
    <div class="signup-container">
        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>
        <h2>Đăng Ký</h2>
        <form action="sigup" method="post">
            <input type="hidden" name="action" value="signup">
            <div class="form-group">
                <label for="customername">Họ tên:</label>
                <input type="text" id="customername" name="customername" value="${customer.customerName}" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${customer.email}" required>
            </div>
            
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" value="${customer.password}" required>
            </div>
            
            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="tel" id="phone" name="phone" value="${customer.phone}" required>
            </div>
            
            <div class="form-group">
                <label for="address">Địa chỉ:</label>
                <input type="text" id="address" name="address" value="${customer.address}" required>
            </div>
            
            <div class="form-group">
                <label for="city">Thành phố:</label>
                <select id="city" name="city" required>
                    <option>${customer.city}</option>
                    <option value="ho-chi-minh">Thành phố Hồ Chí Minh</option>
                    <option value="ho-chi-minh">Hà Nội</option>
                </select>
            </div>
            
            <input type="submit" value="Đăng Ký" class="signup-button">
            
        </form>
        <form action="sigup" method="post">
            <input type="hidden" name="action" value="login">
            <input type="submit" class="signup-button" value="Đăng nhập">
        </form>
    </div>
</div>
</body>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <style>
        /* General Body and Page Styles */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fb;
            margin: 0;
            padding: 0;
        }

        /* Container for centering login box */
        .login-wrapper {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* The main login box */
        .login-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 40px 60px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        /* Title and Message */
        .message {
            color: #ff4d4d;
            font-size: 16px;
            margin-bottom: 20px;
        }

        .login-title {
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
        }

        /* Form and Input Styles */
        .login-form {
            width: 100%;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        .form-group label {
            font-size: 14px;
            color: #555;
            display: block;
            margin-bottom: 8px;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-group input:focus {
            border-color: #0056b3;
            outline: none;
        }

        /* Submit Button */
        .login-button {
            width: 100%;
            padding: 14px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .login-button:hover {
            background-color: #218838;
        }

        /* Signup link */
        .signup-link {
            margin-top: 15px;
        }

        .signup-link a {
            color: #007bff;
            font-size: 14px;
            text-decoration: none;
        }

        .signup-link a:hover {
            text-decoration: underline;
        }

        /* Responsive Design */
        @media (max-width: 480px) {
            .login-container {
                padding: 30px 40px;
            }

            .login-title {
                font-size: 20px;
            }

            .login-button {
                font-size: 14px;
                padding: 12px;
            }
        }

    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="login-container">
            <c:if test="${not empty message}">
                <h3 class="message">${message}</h3>
            </c:if>
            <h2 class="login-title">Đăng Nhập</h2>
            <form action="login" method="post" class="login-form">
                <input type="hidden" name="action" value="login">
                
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${user.email}" placeholder="Nhập email" required>
                </div>

                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" value="${user.password}" placeholder="Nhập mật khẩu" required>
                </div>

                <input type="submit" class="login-button" value="Đăng nhập">

                <div class="signup-link">
                    <a href="sign_up.jsp">Chưa có tài khoản? Đăng ký ngay</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

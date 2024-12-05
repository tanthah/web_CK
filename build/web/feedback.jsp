<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gửi Phản Hồi</title>
    <style>
        /* CSS cho toàn bộ form */
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

        form {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            width: 400px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
        }

        input[type="text"], select, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[readonly] {
            background-color: #f9f9f9;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <form action="SubmitFeedbackServlet" method="post">
       <!-- <label for="phone">Số điện thoại khách hàng:</label>
        <input type="text" id="phone" name="phone" value="${customer.phone}" readonly>
        <br>-->

        <label for="rating">Đánh giá:</label>
        <select id="rating" name="rating" required>
            <option value="1">1 - Rất kém</option>
            <option value="2">2 - Kém</option>
            <option value="3">3 - Bình thường</option>
            <option value="4">4 - Tốt</option>
            <option value="5">5 - Rất tốt</option>
        </select>
        <br>

        <label for="comments">Nhận xét:</label>
        <textarea id="comments" name="comments" rows="5" placeholder="Nhập nhận xét của bạn..."></textarea>
        <br>

        <input type="submit" value="Phản hồi">
    </form>
</body>
</html>

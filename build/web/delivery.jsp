<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delivery Service Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 400px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            
        }
        h2{
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 375px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .form-group textarea {
            width: 390px;
            height: 100px;
        }
        .container_button{
            width: 400px;
            text-align: center;
            padding-bottom: 30px;
        }
        .button {
            padding: 10px 20px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        .button:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
    <div class="container">
        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>
        <c:if test="${hien != 0}">
            
            <form action="delivery" method="post">
                <h2>Add Delivery Service</h2>
                <input type="hidden" name="action" value="add_delivery">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${delivery.name}" placeholder="Name" required >
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${delivery.phone}" placeholder="Phone" required >
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${delivery.address}" placeholder="Address" required >
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" placeholder="Description">${delivery.description}</textarea>
                </div>
                <div class="container_button"> 
                    <input type="submit" class="button" value="thêm">
                </div>
            </form>
        </c:if>
            
            
        <c:if test="${hien == 0}">
            
            <form action="delivery" method="post">
                <h2>Update Delivery Service</h2>
                <h2>ID #${deliveryId}</h2>
                <input type="hidden" name="deliveryId" value="${deliveryId}">
                
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${delivery.name}" placeholder="Name" required >
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${delivery.phone}" placeholder="Phone" required >
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${delivery.address}" placeholder="Address" required >
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" placeholder="Description">${delivery.description}</textarea>
                </div>
                <div class="container_button"> 
                    <input type="hidden" name="action" value="update_delivery">
                    <input type="submit" class="button" value="sửa">
                </div>
            </form>
        </c:if>
        <div class="container_button">
            <form action="admin" method="POST">
                <input type="hidden" name="action" value="previous">
                <input type="submit" class="button" value="Previous">
            </form>
        </div>
</div>
</body>
</html>

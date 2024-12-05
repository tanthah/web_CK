<%@page import="business.Category"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dish Form</title>
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
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input,
        .form-group textarea{
            width: 95%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .form-group select{
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        #preview {
            margin-top: 15px;
            max-width: 100%;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn {
            padding: 10px 20px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #4cae4c;
        }
        h2 {
            margin-bottom: 20px;
        }
    </style>
    <script>
        function handleImageUpload(input, previewId, inputContainerId) {
            const file = input.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const preview = document.getElementById(previewId);
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                    document.getElementById(inputContainerId).style.display = 'none';
                };
                reader.readAsDataURL(file);
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <c:if test="${hien == 0}"  >
        <h2>Update Dish</h2>
        <h4>${message}</h4>
        <form action="dish" method="post" enctype="multipart/form-data">
            
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${dish.name}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="6">${dish.description}</textarea>
            </div>

            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${dish.price}" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="category">Category</label>
                <select name="categoryId" required>
                    <option value="">${dish.categoryId}</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="" 
                                ${category.id_category == dish.categoryId ? 'selected' : ''}>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="image">New Image</label>
                <input type="file" id="image" name="image" accept="image/*" 
                       onchange="handleImageUpload(this, 'imagePreview', 'currentImageContainer')">
                <div id="currentImageContainer">
                    <c:if test="${dish.image != null}">
                        <img src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(dish.image)}"
                             alt="${dish.name}" 
                             style="max-width: 200px; margin-top: 10px;">
                    </c:if>
                </div>
                <img id="imagePreview" alt="Preview" style="max-width: 200px; margin-top: 10px; display: none;">
                
            </div>
                    
            <input type="hidden" name="action" value="update_dish">
            <input type="hidden" name="dishId" value="${dishId}">
            <input type="submit" class="btn" value="Update">
        </form>
        </c:if>
        
        
        <c:if test="${hien != 0}"  > 

        <h2>Add Dish</h2>
        <form action="dish" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add_dish">
            <input type="hidden" name="dishId" value="${dish.dishId}">

            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${dish.name}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="6">${dish.description}</textarea>
            </div>

            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${dish.price}" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="category">Category</label>
                <select name="category_id" class="form-control">
                    <option value="">-- Select Category --</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id_category}" 
                                ${category.id_category == dish.categoryId ? 'selected' : ''}>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="image">New Image (optional)</label>
                <input type="file" id="image" name="image" accept="image/*" 
                       onchange="handleImageUpload(this, 'imagePreview', 'currentImageContainer')">
                <div id="currentImageContainer">
                    <c:if test="${dish.image != null}">
                        <img src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(dish.image)}"
                            alt="${dish.name}" 
                            style="max-width: 200px; margin-top: 10px;">
                    </c:if>
                </div>
                <img id="imagePreview" alt="Preview" style="max-width: 200px; margin-top: 10px; display: none;">
            </div>
            <input type="submit" class="btn" value="add Dish">
        </form>
        
        </c:if>
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="previous">
            <input type="submit" class="btn" value="Previous">
        </form>
    </div>
        
        
</body>
</html>

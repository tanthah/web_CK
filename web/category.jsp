
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm hoặc Sửa Category</title>
    <style>
        
        body {
            display: flex;
            margin: 0;
            padding: 20px;
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            height: 100vh;
            box-sizing: border-box;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .container_insert {
            height: auto;
            width: 400px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            height: fit-content;
        }
        .btn{
            margin-top: -17px;
            margin-left: -40px;
            width: 360px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        label {
            font-weight: bold;
            margin-bottom: 5px;
            display: inline-block;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        textarea {
            resize: vertical;
            min-height: 80px;
        }

        input[type="submit"]{
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        @media (max-width: 480px) {
            body {
                padding: 10px;
            }

            form {
                padding: 15px;
            }

            input[type="submit"] {
                font-size: 14px;
            }
        }
        
        
        
        
        
       
        
        .container_list_categories {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            overflow-y: auto;
            justify-content: center;
        }

        .categories {
            width: 560px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 10px;
        }

        .category-details {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }

        .category-details:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }

        .category-id {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 10px;
        }

        .category-name {
            color: #333;
            font-size: 1.4em;
            font-weight: bold;
            margin-bottom: 15px;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 8px;
        }

        .category-description {
            color: #555;
            line-height: 1.5;
        }

        @media (max-width: 500px) {
            body {
                flex-direction: column;
                align-items: center;
                gap: 20px;
            }

            .container_insert, .container_list_categories {
                max-width: 95%;
            }
        }
    </style>
</head>
<body>
    
    <div class="container_insert">
        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>
        <c:if test="${hien != 0}">
        <form action="category" method="post">
            <input type="hidden" name="action" value="add_category">
            
                <h1>Add Category</h1>
            
            <label for="name">Tên danh mục:</label><br>
            <input type="text" id="name" name="name" value="${category.name}" required><br><br>
            <label for="description">Mô tả:</label><br>
            <textarea id="description" rows="6" name="description">${category.description}</textarea><br><br>
            <input type="submit" value="thêm">
        </form>
            
        </c:if>
        
        <c:if test="${hien == 0}">
        <form action="category" method="post">
            <input type="hidden" name="action" value="update_category">
            <h1>Update Category</h1>
            <h1>ID #${id_category}</h1>
            <input type="hidden" name="id_category" value="${id_category}">
            <label for="name">Tên danh mục:</label><br>
            <input type="text" id="name" name="name" value="${category.name}" required><br><br>
            <label for="description">Mô tả:</label><br>
            <textarea id="description" rows="6" name="description">${category.description}</textarea><br><br>
            <input type="submit" value="sửa">
        </form>
        
        </c:if>
        
    </div>
    <div class="btn">
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="previous">
            <input type="submit"  value="Previous">
        </form>
    </div>
</body>
</html>

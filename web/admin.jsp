
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form với 2 nút Input</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            display: flex;
            height: auto;
            margin: 0;
        }

        .form-container {
            background-color: #ffffff;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 150px;
            height: 500px;
            justify-items: center;
        }
        
        
        form {
            display: flex;
            flex-direction: column;
            width: 150px;
            height: 30px;
            padding-bottom: 20px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: white;
        }


        input[type="submit"]:hover {
            background-color: #0d990d;
        }

        
        
        
        
        .view{
            width: 1000px;
            padding-left: 30px;
            height: auto;
            
        }
        
        
        /*view dish */
        .view-dish {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            height: auto;
        }

        .dish {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            border: 1px solid #ddd;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            height: auto;
        }
       
        
        .debug-info-all{
            display: flex;
            background-color: #cccccc;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-family: monospace;
            justify-content: space-around;
        }        

        .dish-details {
            height: auto;
            display: flex;
            gap: 20px;
            justify-content: space-around;
            background-color: white;
            border-radius: 10px;
            height: auto;
            transition: transform 0.3s ease;
        }
        
        .dish-details:hover{
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }

        .dish-image {
            padding: 15px 15px;
            flex: 0 0 110px;
        }

        .dish-img {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 4px;
        }

        .dish-info {
            flex: 1;
            width: auto;
        }

        .dish-info strong {
            font-size: 1.2em;
            display: block;
            margin-bottom: 10px;
        }

        .dish-info p {
            margin: 5px 0;
            max-width: 240px;
            height: auto; /* Chiều cao tự động mở rộng */
            word-wrap: break-word; /* Tự động xuống dòng khi từ quá dài */
            white-space: normal; /* Cho phép xuống dòng ở vị trí hợp lý */
            color: #555; /* Màu chữ */
            font-size: 14px; /* Kích thước chữ */
            line-height: 1.5; /* Khoảng cách giữa các dòng */
        }

        .dish-info span {
            color: #e44d26;
            font-weight: bold;
        }
        
        .debug-info {
            background-color: #cccccc;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-family: monospace;
            width: 80px;
        }
        
         
        
        
        
        /*view category */
        .view_category {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
        }

        .categories {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .category-details {
            height: auto;
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
            width: 80px;
            height: 25px;
            color: #666;
            font-size: 0.9em;
            margin-bottom: 10px;
            background-color: #cccccc;
            border-radius: 8px;
            padding-left: 20px;
            padding-top: 10px;
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
            max-width: 260px; /* Đặt chiều rộng tối đa cho mô tả */
            height: auto; /* Chiều cao tự động mở rộng */
            word-wrap: break-word; /* Tự động xuống dòng khi từ quá dài */
            white-space: normal; /* Cho phép xuống dòng ở vị trí hợp lý */
            color: #555; /* Màu chữ */
            font-size: 14px; /* Kích thước chữ */
            line-height: 1.5; /* Khoảng cách giữa các dòng */
            padding-bottom: 20px;
        }
        
        
        
        
        
        
        /* view delivery*/
        
        .view-delivery{
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .delivery {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        .delivery-details {
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 16px;
            height: auto;
            background-color: #f9f9f9;
            transition: transform 0.3s ease;
            
        }
        .delivery-details:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .delivery-details h3 {
            margin: 0 0 10px 0;
            font-size: 20px;
            color: #333;
        }
        .delivery-details p {
            margin: 5px 0;
            max-width: 250px;
            height: auto; /* Chiều cao tự động mở rộng */
            word-wrap: break-word; /* Tự động xuống dòng khi từ quá dài */
            white-space: normal; /* Cho phép xuống dòng ở vị trí hợp lý */
            color: #555; /* Màu chữ */
            font-size: 16px; /* Kích thước chữ */
            line-height: 1.5; /* Khoảng cách giữa các dòng */
            
        }
        .delivery_id{
            width: 70px;
            height: 20px;
            background-color: #cccccc;
            border-radius: 7px;
            padding-left:  15px;
        }
        .delivery-description{
            padding-top: 15px;
            border-top: 2px solid #4CAF50;
        }
        
        
        
    </style>
</head>

<body>
    <div class="form-container">
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="add_dish_page">
            <input type="submit" class="nut1" value="Add Dish">
        </form>
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="view_dish">
            <input type="submit" class="nut1" value="View All Dish">
        </form>
        
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="add_category_page">
            <input type="submit" class="nut2" value="Add Category">
        </form>
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="view_category">
            <input type="submit" class="nut1" value="View All Category">
        </form>
        
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="add_delivery_page">
            <input type="submit" class="nut2" value="Add Delivery">
        </form>
        <form action="admin" method="POST">
            <input type="hidden" name="action" value="view_delivery">
            <input type="submit" class="nut1" value="View All Delivery">
        </form>
    </div>
    
    <div class="view">
        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>
        
        <div class="view-dish">
        <c:if test="${vao !=0}">
        <c:if test="${empty categories}">
        <c:if test="${empty deliveries}">
        <c:if test="${not empty dishesList}">
            <div class="debug-info-all">
                <div>
                    <h2>Dish List</h2>
                    Total dishes: ${dishesList.size()}
                </div>
                
            </div>
            
            <div class="dish">
            <c:forEach var="dish" items="${dishesList}" >
                
                    <div class="dish-details">
                        <div class="dish-image">
                            <c:if test="${dish.image != null}">
                                <img src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(dish.image)}"
                                     alt="${dish.name}" 
                                     class="dish-img"
                                     onerror="this.src='path/to/default-image.jpg'">
                            </c:if>
                        </div>
                        <div class="dish-info">
                            <p class="debug-info">ID: #${dish.dishId}</p>
                            <strong>${dish.name} </strong>
                            <p>Price: <span>$${dish.price}</span></p>
                            <p>${dish.description}</p>
                            
                            <form action="admin" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="dishId" value="${dish.dishId}">
                                <input type="hidden" name="categoryId" value="${dish.categoryId}">
                                <input type="hidden" name="name" value="${dish.name}">
                                <input type="hidden" name="price" value="${dish.price}">
                                <input type="hidden" name="description" value="${dish.description}">
                                <input type="hidden" name="image"  accept="image/*" value="${Base64.getEncoder().encodeToString(dish.image)}" ${dishId == null ? 'required' : ''}>

                                <input type="hidden" name="action" value="update_dish">
                                <input type="submit" class="nut1" value="Update dish">
                            </form>

                            <form action="dish" method="POST">
                                <input type="hidden" name="dishId" value="${dish.dishId}">
                                <input type="hidden" name="action" value="delete_dish">
                                <input type="submit" class="nut1" value="Delete dish">
                            </form>
                        </div>
                    </div>                        
                
            </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty dishesList}">
            <p>No dishes available.</p>
        </c:if>
        </c:if>
        </c:if>
        </div>
       
        <div class="view-category">
            
            <c:if test="${empty deliveries}">
            <c:if test="${empty dishesList}">
            <c:if test="${not empty categories}">
                <div class="debug-info-all">
                    <div>
                        <h2>Categories List</h2>
                        Total dishes: ${categories.size()}
                    </div>
                    
                </div>
                    
                <div class="categories">
                    <c:forEach var="category" items="${categories}">
                        <div class="category-details">
                            <div class="category-id">ID: #${category.id_category}</div>
                            <div class="category-name">${category.name}</div>
                            <div class="category-description"> ${category.description} </div>
                            
                            <form action="admin" method="POST">
                                <input type="hidden" name="id_category" value="${category.id_category}">
                                <input type="hidden" name="name" value="${category.name}">
                                <input type="hidden" name="description" value="${category.description}">
                                
                                <input type="hidden" name="action" value="update_category">
                                <input type="submit" class="nut1" value="Update category">
                            </form>
                                
                            <form action="category" method="POST">
                                <input type="hidden" name="id_category" value="${category.id_category}">
                                <input type="hidden" name="action" value="delete_category">
                                <input type="submit" class="nut1" value="Delete category">
                            </form>
                    
                        </div>
                        
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty categories}">
                <p>No categories available.</p>
            </c:if>
            </c:if>
            </c:if>
        </div>
        
        <div class="view-delivery">
            
            <c:if test="${empty categories}">
            <c:if test="${empty dishesList}">
            <c:if test="${not empty deliveries}">
                <div class="debug-info-all">
                    <div>
                        <h2>Deliveries Service List</h2>
                        Total dishes: ${deliveries.size()}
                    </div>
                    
                </div>
                    
                <div class="delivery">
                    <c:forEach var="delivery" items="${deliveries}">
                        <div class="delivery-details">
                            <p class="delivery_id"><strong>ID #${delivery.deliveryId}</strong></p>
                            <h3>${delivery.name}</h3>
                            <p><strong>Số điện thoại:</strong> ${delivery.phone}</p>
                            <p><strong>Địa chỉ:</strong> ${delivery.address}</p>
                            <p class="delivery-description">${delivery.description}</p>
                            
                            <form action="admin" method="POST">
                                <input type="hidden" name="deliveryId" value="${delivery.deliveryId}">
                                <input type="hidden" name="name" value="${delivery.name}">
                                <input type="hidden" name="phone" value="${delivery.phone}">
                                <input type="hidden" name="address" value="${delivery.address}">
                                <input type="hidden" name="description" value="${delivery.description}">
                                
                                <input type="hidden" name="action" value="update_delivery">
                                <input type="submit" class="nut1" value="Update delivery">
                            </form>
                            <form action="delivery" method="POST">
                                <input type="hidden" name="deliveryId" value="${delivery.deliveryId}">
                                <input type="hidden" name="action" value="delete_delivery">
                                <input type="submit" class="nut1" value="Delete delivery">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty deliveries}">
                <p>No deliveries service available.</p>
            </c:if>
            </c:if>
            </c:if>
        </div>
        </c:if>
    </div>
</body>
</html>


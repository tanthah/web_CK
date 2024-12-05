<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Delivery List</title>
   <style>
       .delivery-item {
           border: 1px solid #ddd;
           margin: 10px 0;
           padding: 15px;
           border-radius: 5px;
       }
       .message {
           color: green;
           margin: 10px 0;
       }
       .field-label {
           font-weight: bold;
           margin-right: 10px;
       }
   </style>
</head>
<body>
   <h2>Delivery Services List</h2>
   
   <c:if test="${not empty message}">
       <p class="message">${message}</p>
   </c:if>

   <div class="delivery-list">
       <c:forEach var="delivery" items="${deliveries}">
           <div class="delivery-item">
               <p><span class="field-label">ID:</span> ${delivery.deliveryId}</p>
               <p><span class="field-label">Name:</span> ${delivery.name}</p>
               <p><span class="field-label">Phone:</span> ${delivery.phone}</p>
               <p><span class="field-label">Address:</span> ${delivery.address}</p>
               <p><span class="field-label">Description:</span> ${delivery.description}</p>
           </div>
       </c:forEach>
   </div>
</body>
</html>
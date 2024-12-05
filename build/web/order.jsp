<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>

    <%@include file ="header.html" %>
    <c:if test="${not empty message}">
        <div id="notification" class="notification">
            <h3>${message}</h3>
        </div>
    </c:if>
    <div class="container">
        <c:if test="${not empty dishesList}">
            <c:forEach var="dish" items="${dishesList}">
                <div class="dish">
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
                            <p><strong>${dish.name}</strong></p>
                            <p>Price: <span>$${dish.price}</span></p>
                            <p>${dish.description}</p>
                        </div>
                        <form action="cart" method="post">
                            <input type="hidden" name="name" value="${dish.name}">
                            <input type="hidden" name="price" value="${dish.price}">
                            <input type="hidden" name="dishId" value="${dish.dishId}">
                            
                            <input type="hidden" name="action" value="add_cart">
                            <input type="submit"  class="them" value="+">
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty dishesList}">
            <p>No dishes available.</p>
        </c:if>
    </div>
<%@include file ="footer.jsp" %>
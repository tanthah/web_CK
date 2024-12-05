
<%@page import="java.util.GregorianCalendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="footer" id="contact">
    <p>Liên hệ: 0862738237</p>
    <p>Email: nhahang@gmail.com</p>
    <p>Địa chỉ: 218 Gò Xoài, P.Bình Hưng Hòa A, Q.Bình Tân, Tp.HCM</p>
    <%
        // Lấy năm hiện tại bằng GregorianCalendar
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(java.util.Calendar.YEAR);
    %>
    <p><strong>&copy; Copyright <%= currentYear %> Thuộc bản quyền của nhahang.com</strong></p>
</div>

</body>
</html>

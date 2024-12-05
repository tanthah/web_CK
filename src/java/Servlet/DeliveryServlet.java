
package Servlet;

import business.*;
import data.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


public class DeliveryServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url ="admin.jsp";

        if (action == null){
            action = "home";
        }
        
        if (action.equals("home")) {
            url = "/body.jsp";
        }else if (action.equals("add_delivery")) {
            // Lấy các tham số từ form
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String description = request.getParameter("description");

            // Biến để lưu thông báo lỗi
            String message = "";

            // Kiểm tra đầu vào
            if (name == null || name.trim().isEmpty()) {
                message = "Tên không được để trống.";
                url = "delivery.jsp"; // Đường dẫn quay lại form
            } else if (phone == null || phone.trim().isEmpty()) {
                message = "Số điện thoại không được để trống.";
                url = "delivery.jsp";
            } else if (!phone.matches("\\d{10}")) { // Kiểm tra số điện thoại (10 chữ số)
                message = "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.";
                url = "delivery.jsp";
            } else if (address == null || address.trim().isEmpty()) {
                message = "Địa chỉ không được để trống.";
                url = "delivery.jsp";
            } else if (DeliveryDB.deliveryServiceExists(name)) {
                message = "Danh mục này đã tồn tại";
                url = "/admin.jsp";
            }

            // Nếu không có lỗi
            if (message.isEmpty()) {
                // Tạo đối tượng Delivery mới
                Delivery delivery = new Delivery();
                delivery.setName(name);
                delivery.setPhone(phone);
                delivery.setAddress(address);
                delivery.setDescription(description);

                // Thêm vào cơ sở dữ liệu
                DeliveryDB.insert(delivery);

                // Gửi thông báo thành công
                message = "Thêm dịch vụ giao hàng thành công.";
                url = "/admin.jsp"; // Chuyển đến trang xác nhận
            }

            // Gửi thông báo và cập nhật danh sách
            request.setAttribute("message", message);
            List<Delivery> deliveries = DeliveryDB.getAllDeliveries(); // Lấy danh sách dịch vụ giao hàng
            request.setAttribute("deliveries", deliveries);
        }else if (action.equals("update_delivery")) {
            try {
                long deliveryId = Long.parseLong(request.getParameter("deliveryId"));
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String description = request.getParameter("description");

                // Add validation here
                if (!phone.matches("\\d{10}")) {
                    request.setAttribute("message", "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.");
                    url = "/delivery.jsp";

                    // Preserve the form data
                    Delivery delivery = new Delivery(name, phone, address, description);
                    request.setAttribute("delivery", delivery);
                    request.setAttribute("deliveryId", deliveryId);
                    request.setAttribute("hien", 0);
                } else {
                    Delivery delivery = new Delivery(name, phone, address, description);
                    delivery.setDeliveryId(deliveryId);
                    boolean success = DeliveryDB.update(deliveryId, delivery);

                    if (success) {
                        request.setAttribute("message", "delivery updated successfully");
                    } else {
                        request.setAttribute("message", "Error updating delivery");
                    }
                    url = "/admin.jsp";
                    List<Delivery> deliveries = DeliveryDB.getAllDeliveries();
                    request.setAttribute("deliveries", deliveries);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid delivery ID");
            }
        }else if (action.equals("delete_delivery")) {
            try{
                long deliveryId = Long.parseLong(request.getParameter("deliveryId"));
                Delivery delivery = new Delivery();
                delivery.setDeliveryId(deliveryId);
                // delete category in database
                boolean success = DeliveryDB.delete(delivery);
                if (success) {
                    request.setAttribute("message", "Category delete successfully");
                    
                } else {
                    request.setAttribute("message", "Error delete category");
                    
                }
            } catch (NumberFormatException e){
                request.setAttribute("message", "lỗi rồi không thể xóa");
            }
            url = "/admin.jsp";
            List<Delivery> deliveries = DeliveryDB.getAllDeliveries();
            request.setAttribute("deliveries", deliveries);
}

      
        getServletContext().getRequestDispatcher(url)
                        .forward(request, response);
    }

    
}

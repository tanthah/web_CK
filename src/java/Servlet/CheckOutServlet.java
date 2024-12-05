package Servlet;

import Email.MailUtilGmail;
import business.Cart;
import business.Customer;
import business.Dishes;
import data.CartDB;
import data.CustomersDB;
import data.DishesDB;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import javax.mail.MessagingException;


public class CheckOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String url = "/body.jsp";

        if (action == null) {
            action = "home";
        }

        if (action.equals("home")) {
            response.sendRedirect("cart.jsp"); // Hoặc URL chính xác của trang chủ

        }else if (action.equals("cart_page")) {
            response.sendRedirect("cart.jsp");
            String email = (String) session.getAttribute("email");
            List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
            session.setAttribute("item", updatedCartItems);
        /*}else if (action.equals("dish_page")) {
            response.sendRedirect("order.jsp");
            // Lấy danh sách món ăn từ cơ sở dữ liệu
            List<Dishes> dishesList = DishesDB.getAllDishes(); // DishesDB.getAllDishes() lấy tất cả món ăn từ cơ sở dữ liệu
            // Đưa dữ liệu vào request để JSP có thể sử dụng
            request.setAttribute("dishesList", dishesList);  */
        }else if (action.equals("order_completed")) {
            // Lấy thông tin từ form
            String total = request.getParameter("total");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            String[] selectedIds = (String[]) session.getAttribute("selectedIds");

            // Lấy email từ session
            String email = (String) session.getAttribute("email");

            if (email != null && !email.isEmpty()) {
                // Kiểm tra nếu các thông tin cần thiết bị null, lấy từ cơ sở dữ liệu
                if (name == null || name.isEmpty() || address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
                    Customer customer = CustomersDB.selectCustomer(email);
                    if (customer != null) {
                        if (name == null || name.isEmpty()) {
                            name = customer.getCustomerName();
                        }
                        if (address == null || address.isEmpty()) {
                            address = customer.getAddress();
                        }
                        if (phone == null || phone.isEmpty()) {
                            phone = customer.getPhone();
                        }
                    } else {
                        url = "/error.jsp";
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                        return;
                    }
                }

                // Thiết lập nội dung email
                String to = email;
                String from = "thanhndt2004@gmail.com";
                String subject = "Order Confirmation";
                String body = "Kính gửi " + name + ",\n\n"
                        + "Chúng tôi xin chân thành cảm ơn quý khách đã đặt hàng tại cửa hàng của chúng tôi.\n\n"
                        + "Thông tin đơn hàng:\n"
                        + "Tổng tiền: $" + total + "\n"
                        + "Tên: " + name + "\n"
                        + "Địa chỉ giao hàng: " + address + "\n"
                        + "Số điện thoại: " + phone + "\n\n"
                        + "Nếu cần hỗ trợ thêm, quý khách vui lòng liên hệ với chúng tôi qua [số hotline hoặc email hỗ trợ].\n"
                        + "Đội ngũ của chúng tôi luôn sẵn sàng phục vụ quý khách!\n\n"
                        + "Trân trọng,\n"
                        + "Đội ngũ hỗ trợ.";

                boolean isBodyHTML = false;

                try {
                    // Gửi email
                    MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                    
                     // Xóa các sản phẩm đã thanh toán
                    for (String id : selectedIds) {
                        try {
                            long cartId = Long.parseLong(id); // Chuyển đổi từ String sang long
                            CartDB.delete(cartId);   // Gọi phương thức xóa với kiểu long
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // Nếu có ID không hợp lệ, bạn có thể log lỗi hoặc bỏ qua
                            System.out.println("Invalid ID format: " + id);
                        }
                    }


                    response.sendRedirect("cart.jsp"); // Hoặc URL chính xác của trang chủ
                    List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
                    session.setAttribute("item", updatedCartItems);
                } catch (MessagingException e) {
                    response.sendRedirect("cart.jsp"); // Hoặc URL chính xác của trang chủ

                }
            } else {
                // Nếu email không tồn tại trong session
                response.sendRedirect("log_in.jsp"); // Hoặc URL chính xác của trang chủ

            }
        }

       
    }
}

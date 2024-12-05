
package Servlet;

import Email.Email;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import business.*;
import data.*;


public class sigupServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        String url = "/body.jsp";
        
        if (action == null){
            action ="home";
        }
        
        if (action.equals("home")) {
            url = "/body.jsp";
        }else if (action.equals("signup")) {
            String customername = request.getParameter("customername");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String message;

            Customer customer = new Customer(customername, password, email, phone, address, city);

            // Kiểm tra số điện thoại và mật khẩu
            if (phone.length() != 10 || !phone.matches("\\d+")) {
                message = "Số điện thoại không hợp lệ. Vui lòng nhập đúng 10 chữ số.";
                request.setAttribute("customer", customer);
                url = "/sign_up.jsp";
            } else if (password.length() < 6) {
                message = "Mật khẩu phải có độ dài ít nhất 6 ký tự.";
                request.setAttribute("customer", customer);
                url = "/sign_up.jsp";
            } else {
                if (CustomersDB.emailExists(customer.getEmail())) {
                    message = "Email đã được đăng ký. Hãy đăng nhập.";
                    url = "/log_in.jsp";
                } else {
                    CustomersDB.insert(customer);
                    url = "/body.jsp";
                    
                    session.setAttribute("email", email);

                    // Gửi email chào mừng
                    boolean emailSent = Email.sendEmail(email);
                    if (emailSent) {
                        message = "bạn đã đăng ký thành công và chúng tôi đã gửi 1 email đến cho bạn hãy kiểm tra ";
                    } else {
                        message = "bạn đã đăng ký thành công nhưng chúng tôi không thể gửi mail cho bạn ";
                    }
                }
            }

            // Đặt thông báo cho người dùng
            request.setAttribute("message", message);
        } else if (action.equals("login")) {
            url = "/log_in.jsp";
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    
    }
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
    }


}

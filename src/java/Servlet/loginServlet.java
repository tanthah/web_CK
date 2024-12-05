
package Servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import business.*;
import data.*;
import java.util.List;


public class loginServlet extends HttpServlet {
@Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String url = "/body.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

       // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/body.jsp";    // the "join" page
        } else if (action.equals("login_page")) {
            url = "/log_in.jsp";
        } else if (action.equals("login")) {
            // Lấy tham số từ yêu cầu
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String message = null;

            // Khởi tạo đối tượng người dùng
            Customer customers = new Customer(email, password);

            // Kiểm tra nếu là admin
            if (email.equals("admin@gmail.com") && password.equals("1")) {
                request.setAttribute("vao", 0);
                // Nếu là admin
                url = "admin.jsp";
                message = "Welcome, Admin!";
                session.setAttribute("email", email);
            } else {
                // Nếu không phải admin
                //url = "user_home.jsp"; // URL khác cho người dùng thường (ví dụ user_home.jsp)
                //message = "Welcome, " + email;
                if(CustomersDB.emailExists(customers.getEmail())) {
                    if (CustomersDB.checkuserExists(customers.getEmail(), customers.getPassword())) { 
                        session.setAttribute("email", email);
                        
                        String pendingAction = (String) session.getAttribute("pendingAction");
                        
                        if ("view_cart".equals(pendingAction)) {
                            url = "/cart.jsp";
                            List<Cart> cartItems = CartDB.getCartsByEmail(email);
                            session.setAttribute("item", cartItems);
                        }else if (action.equals("add_cart")) {
                            String name = (String) session.getAttribute("pendingName");
                            double price = (Double) session.getAttribute("pendingPrice");
                            long dishId = (Long) session.getAttribute("pendingdishId");
                            
                            boolean itemExists = CartDB.cartExists(name);

                            if (itemExists) {
                                Cart existingCart = CartDB.getCartByNameAndEmail(name, email);
                                if (existingCart != null) {
                                    long newQuantity = existingCart.getQuantity() + 1;
                                    existingCart.setQuantity(newQuantity);
                                    existingCart.setTotal(price * newQuantity);
                                    CartDB.update(existingCart.getIdcart(), existingCart);
                                }
                                request.setAttribute("message", "thêm món thành công");
                            } else {
                                Cart cart = new Cart();
                                cart.setEmail(email);
                                cart.setName(name);
                                cart.setPrice(price);
                                cart.setQuantity(1L);
                                cart.setTotal(price);

                                Dishes dish = DishesDB.selectDishById(dishId);
                                if (dish != null && dish.getImage() != null) {
                                    cart.setImage(dish.getImage());
                                }
                                request.setAttribute("message", "thêm món thành công");
                                CartDB.insert(cart);
                            }
                            url = "/order.jsp";
                            List<Dishes> dishesList = DishesDB.getAllDishes(); // DishesDB.getAllDishes() lấy tất cả món ăn từ cơ sở dữ liệu
                            // Đưa dữ liệu vào request để JSP có thể sử dụng
                            request.setAttribute("dishesList", dishesList);
                            // Reload cart items after any modification
                            List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
                            session.setAttribute("item", updatedCartItems);
                        }
                       
                        message = "Đăng nhập thành công.";

                    }else {
                        message = "Mật khẩu và email không đúng. Vui lòng nhập lại.";
                        url = "/log_in.jsp";
                    }
                    
                }else {
                    message = "Tài khoản chưa đăng ký. Vui lòng đăng ký.";
                    url = "/sign_up.jsp";
                }

                

            }  
            request.setAttribute("customer", customers);
            request.setAttribute("message", message);

        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }   
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
                         throws ServletException, IOException {
        doPost(request, response);
    }
}

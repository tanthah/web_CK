package Servlet;

import business.Dishes;
import data.DishesDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;



public class OrderServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String action = request.getParameter("action");
        String url = "/body.jsp";
        
        if (action == null) {
            action = "home";
        }
        
        if(action.equals("home")) {
            url = "/body.jsp";
        }else if (action.equals("order_page")) {
            // Lấy danh sách món ăn từ cơ sở dữ liệu
            List<Dishes> dishesList = DishesDB.getAllDishes(); // DishesDB.getAllDishes() lấy tất cả món ăn từ cơ sở dữ liệu
            // Đưa dữ liệu vào request để JSP có thể sử dụng
            request.setAttribute("dishesList", dishesList);
            url = "/order.jsp";//dishesList
        }
        
        getServletContext().getRequestDispatcher(url)
                           .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
                         throws ServletException, IOException {
        doPost(request, response);
    }

}

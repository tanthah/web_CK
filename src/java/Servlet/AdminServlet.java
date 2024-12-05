
package Servlet;


import business.*;
import data.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;




@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String url ="/form/admin.jsp";
        
        if (action == null){
            action = "home";
        }
        
        if (action.equals("home")) {
            url = "/body.jsp";
        }else if (action.equals("add_dish_page")) {
            // Get updated list of categories
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);
            url = "/dish.jsp";
        }else if (action.equals("view_dish")) {
            // Cập nhật danh sách món ăn
            List<Dishes> dishesList = DishesDB.getAllDishes();
            request.setAttribute("dishesList", dishesList);
            url = "/admin.jsp";
        }else if (action.equals("update_dish")) {
            // Get parameters from request
            long dishId = Long.parseLong(request.getParameter("dishId"));
            long categoryId = Long.parseLong(request.getParameter("categoryId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            String description = request.getParameter("description");
            
            Dishes existingDish = DishesDB.selectDishById(dishId);
            byte[] imageData = existingDish.getImage();
            /*byte[] imageData = null;
                // Handle image update if provided
                Part filePart = request.getPart("image");
                if (filePart != null && filePart.getSize() > 0) {
                    try (InputStream fileContent = filePart.getInputStream()) {
                        imageData = new byte[(int) filePart.getSize()];
                        fileContent.read(imageData);
                    }
                }
*/
            Dishes dish = new Dishes (categoryId, name, description, price, imageData);

            request.setAttribute("dish", dish);
            request.setAttribute("dishId", dishId);
            url = "/dish.jsp";
            // Get categories for the form
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);

            request.setAttribute("hien", 0);

        }else if (action.equals("add_category_page")){
            url = "/category.jsp";
        }else if (action.equals("view_category")) {
            // Get updated list of categories
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);
            url = "/admin.jsp";
        }else if (action.equals("update_category")){
            String id_category = request.getParameter("id_category");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            Category category = new Category( name, description);
            request.setAttribute("category", category);
            request.setAttribute("id_category", id_category);
            url = "/category.jsp";
            
            request.setAttribute("hien", 0);
            
        }else if (action.equals("add_delivery_page")) {
            url = "/delivery.jsp";
        }else if  (action.equals("view_delivery")) {
            List<Delivery> deliveries = DeliveryDB.getAllDeliveries(); // Lấy danh sách dịch vụ giao hàng
            request.setAttribute("deliveries", deliveries);
            url = "/admin.jsp";
        }else if (action.equals("update_delivery")){
            String deliveryId = request.getParameter("deliveryId");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String description = request.getParameter("description");
            
            Delivery delivery = new Delivery (name, phone, address, description);
            request.setAttribute("delivery", delivery);
            request.setAttribute("deliveryId", deliveryId);
            url = "/delivery.jsp";
            
            request.setAttribute("hien", 0);
        }else if (action.equals("previous")){
            url = "/admin.jsp";
            request.setAttribute("vao", 0);
        }
        getServletContext().getRequestDispatcher(url)
                            .forward(request, response);
    }
    
  
}


package Servlet;

import business.Category;
import business.Dishes;
import data.CategoryDB;
import data.DishesDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.List;


@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class DishServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        String action = request.getParameter("action");
        String url ="/admin.jsp";

        if (action == null){
            action = "home";
        }
        
        if (action.equals("home")) {
            url = "/body.jsp";
        }else if (action.equals("add_dish")) {
            // Lấy dữ liệu từ form
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String priceStr = request.getParameter("price");
                String categoryIdStr = request.getParameter("category_id"); // Lấy ID category từ form

                Double price = null;
                Long categoryId = null;
                // Validate inputs
            String message = "";
            if (name == null || name.isEmpty()) {
                message = "Tên danh mục không được để trống";
                url = "/dish.jsp";
            } else if (DishesDB.dishExists(name)) {
                message = "Danh mục này đã tồn tại";
                url = "/admin.jsp";
            }
            if (message.isEmpty()) {
                try {


                    // Validate giá
                    if (priceStr != null && !priceStr.isEmpty()) {
                        try {
                            price = Double.valueOf(priceStr);
                        } catch (NumberFormatException e) {
                            message = "Giá không hợp lệ!";
                            request.setAttribute("message", message);
                            url = "/dish.jsp";
                            return;
                        }
                    }

                    // Validate category ID
                    if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                        categoryId = Long.valueOf(categoryIdStr);
                    }

                    // Xử lý upload ảnh
                    Part filePart = request.getPart("image");
                    byte[] imageData = null;

                    if (filePart != null && filePart.getSize() > 0) {
                        try (InputStream fileContent = filePart.getInputStream()) {
                            imageData = new byte[(int) filePart.getSize()];
                            fileContent.read(imageData);
                        }
                    } else {
                        message = "Vui lòng chọn ảnh!";
                        request.setAttribute("message", message);
                        url = "/dish.jsp";
                        return;
                    }

                    // Tạo đối tượng Dish (chỉ sử dụng categoryId)
                    Dishes dish = new Dishes(categoryId, name, description, price, imageData);

                    // Lưu vào database và kiểm tra lỗi
                    String error = DishesDB.insert(dish);
                    if (error != null) {
                        // Nếu có lỗi
                        message = "Lỗi khi thêm món: " + error;
                        request.setAttribute("message", message);
                        // Lưu lại các giá trị đã nhập để người dùng không phải nhập lại
                        request.setAttribute("name", name);
                        request.setAttribute("description", description);
                        request.setAttribute("price", priceStr);
                        request.setAttribute("category_id", categoryIdStr);
                        // Lấy lại danh sách categories để hiển thị form
                        List<Category> categories = CategoryDB.getAllCategories();
                        request.setAttribute("categories", categories);
                        url = "/dish.jsp";
                    } else {

                        
                        message = "Thêm món thành công!";
                        request.setAttribute("message", message);
                        url = "/admin.jsp";
                    }

                } catch (Exception e) {
                    message = "Lỗi: " + e.getMessage();
                    request.setAttribute("message", message);
                    url = "/dish.jsp";
                }
            }

            // Cập nhật danh sách món ăn
            List<Dishes> dishesList = DishesDB.getAllDishes();
            request.setAttribute("dishesList", dishesList);
            // Thông báo thành công
        }else if (action.equals("update_dish")) {
            String message;
            try {
                // Get dish details from the request
                Long dishId = Long.parseLong(request.getParameter("dishId"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Double price = Double.parseDouble(request.getParameter("price"));
                String categoryIdStr = request.getParameter("category_id");
                Long categoryId = null;
                
                
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    categoryId = Long.parseLong(categoryIdStr);
                }

                // Get the current dish from database
                Dishes existingDish = DishesDB.selectDishById(dishId);
                
                if (existingDish == null) {
                    message = "Không tìm thấy món ăn!";
                    url = "/admin.jsp";
                } else {
                    // Update the dish properties
                    existingDish.setName(name);
                    existingDish.setDescription(description);
                    existingDish.setPrice(price);
                    if (categoryId != null) {
                        existingDish.setCategoryId(categoryId);
                    }

                    // Handle image update if new image is uploaded
                    Part filePart = request.getPart("image");
                    if (filePart != null && filePart.getSize() > 0) {
                        try (InputStream fileContent = filePart.getInputStream()) {
                            byte[] imageData = new byte[(int) filePart.getSize()];
                            fileContent.read(imageData);
                            existingDish.setImage(imageData);
                        }
                    }

                    // Update the dish in database
                    DishesDB.update(existingDish);
                    message = "Cập nhật món ăn thành công!";
                }
                
            } catch (NumberFormatException e) {
                message = "Lỗi: Dữ liệu không hợp lệ " + e.getMessage();
                url = "/admin.jsp";
            } catch (Exception e) {
                message = "Lỗi khi cập nhật món ăn: " + e.getMessage();
                url = "/admin.jsp";
            }
            List<Dishes> dishesList = DishesDB.getAllDishes();
            request.setAttribute("dishesList", dishesList);
            request.setAttribute("message", message);
            
        } else if (action.equals("delete_dish")) {
            String message;
            try {
                // Get dish ID from request
                Long dishId = Long.parseLong(request.getParameter("dishId"));
                
                // Get the dish from database
                Dishes dishToDelete = DishesDB.selectDishById(dishId);
                
                if (dishToDelete == null) {
                    message = "Không tìm thấy món ăn để xóa!";
                } else {
                    // Delete the dish
                    DishesDB.delete(dishToDelete);
                    message = "Xóa món ăn thành công!";
                }
                
            } catch (NumberFormatException e) {
                message = "Lỗi: ID món ăn không hợp lệ";
            } catch (Exception e) {
                message = "Lỗi khi xóa món ăn: " + e.getMessage();
            }
            // Update dishes list and set attributes
            List<Dishes> dishesList = DishesDB.getAllDishes();
            request.setAttribute("dishesList", dishesList);
            request.setAttribute("message", message);
        }


        
        getServletContext().getRequestDispatcher(url)
                            .forward(request, response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

       
    }

}


package Servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import business.Category;
import data.*;



public class CategoryServlet extends HttpServlet {

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
        } else if (action.equals("add_category")) {
            // Get parameters from the form
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            // Validate inputs
            String message = "";
            if (name == null || name.isEmpty()) {
                message = "Tên danh mục không được để trống";
                url = "/category.jsp";
            } else if (CategoryDB.categoryExists(name)) {
                message = "Danh mục này đã tồn tại";
                url = "/admin.jsp";
            }
            
            if (message.isEmpty()) {
                // Create and insert new category
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                
                CategoryDB.insert(category);
                
                message = "Thêm danh mục thành công";
                url = "/admin.jsp";
            }
            
            request.setAttribute("message", message);
            // Get updated list of categories
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);
        }else if (action.equals("update_category")) {
            try {
                // Get parameters from request
                long id_category = Long.parseLong(request.getParameter("id_category"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                
                // Create category object
                Category category = new Category(name, description);
                category.setId_category(id_category);
                
                // Update category in database
                boolean success = CategoryDB.update(id_category, category);
                
                if (success) {
                    request.setAttribute("message", "Category updated successfully");
                    
                } else {
                    request.setAttribute("message", "Error updating category");
                    
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid category ID");
            }
            url ="/admin.jsp";
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);
        }else if (action.equals("delete_category")) {
            try{
                long id_category = Long.parseLong(request.getParameter("id_category"));
                Category category = new Category();
                category.setId_category(id_category);
                // delete category in database
                boolean success = CategoryDB.delete(category);
                if (success) {
                    request.setAttribute("message", "Category delete successfully");
                    
                } else {
                    request.setAttribute("message", "Error delete category");
                    
                }
            } catch (NumberFormatException e){
                request.setAttribute("message", "lỗi rồi không thể xóa");
            }
            url ="/admin.jsp";
            List<Category> categories = CategoryDB.getAllCategories();
            request.setAttribute("categories", categories);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }
}

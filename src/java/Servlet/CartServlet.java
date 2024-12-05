package Servlet;

import business.*;
import data.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

public class CartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String url = "/body.jsp";
        HttpSession session = request.getSession();
        
        if (action == null) {
            action = "home";
        }
        
        // Get user email from session
        String email = (String) session.getAttribute("email");
        if (email == null && !action.equals("home")) {
            response.sendRedirect("log_in.jsp");
            session.setAttribute("pendingAction", "view_cart");
            return;
        }

        if (action.equals("home")) {
            url = "/body.jsp";
        } 
        else if (action.equals("view_cart")) {
            url = "/cart.jsp";
            List<Cart> cartItems = CartDB.getCartsByEmail(email);
            session.setAttribute("item", cartItems);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String url = "/body.jsp";
        HttpSession session = request.getSession();
        
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        long dishId = Long.parseLong(request.getParameter("dishId"));
            
        
        // Get user email from session
        String email = (String) session.getAttribute("email");
        if (email == null) {
            response.sendRedirect("log_in.jsp");
            
            session.setAttribute("pendingAction", "add_Cart");
            session.setAttribute("pendingName", name);
            session.setAttribute("pendingPrice", price);
            session.setAttribute("pendingdishId", dishId);
            return;
        }

        if (action.equals("add_cart")) {
            
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
        else if (action.equals("update_quantity")) {
            long cartId = Long.parseLong(request.getParameter("cartId"));
            int changeAmount = Integer.parseInt(request.getParameter("change"));
            
            Cart cart = CartDB.selectCart(cartId);
            if (cart != null && cart.getEmail().equals(email)) {
                long newQuantity = cart.getQuantity() + changeAmount;
                
                if (newQuantity <= 0) {
                    CartDB.delete(cartId);
                } else {
                    cart.setQuantity(newQuantity);
                    cart.setTotal(cart.getPrice() * newQuantity);
                    CartDB.update(cartId, cart);
                }
            }
            url = "/cart.jsp";
            // Reload cart items after any modification
            List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
            session.setAttribute("item", updatedCartItems);
        }
        else if (action.equals("remove_item")) {
            long cartId = Long.parseLong(request.getParameter("cartId"));
            CartDB.delete(cartId);
            url = "/cart.jsp";
            request.setAttribute("message", "xóa món ăn thành công");
            // Reload cart items after any modification
            List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
            session.setAttribute("item", updatedCartItems);
        }
        else if (action.equals("remove_selected")) {
            String[] selectedItems = request.getParameterValues("selectedItems");
            if (selectedItems != null) {
                for (String itemId : selectedItems) {
                    long cartId = Long.parseLong(itemId);
                    CartDB.delete(cartId);
                }
            }
            url = "cart.jsp";
            // Reload cart items after any modification
            List<Cart> updatedCartItems = CartDB.getCartsByEmail(email);
            session.setAttribute("item", updatedCartItems);
        }else if ("checkout".equals(action)) {
    try {
        // Debug print
        System.out.println("Starting checkout process");
        
        String selectedIdsParam = request.getParameter("selectedItems");
        System.out.println("Selected items parameter: " + selectedIdsParam);
        
        if (selectedIdsParam == null || selectedIdsParam.trim().isEmpty()) {
            System.out.println("No items selected");
            request.setAttribute("message", "Please select items to checkout");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        // Split the selected IDs
        String[] selectedIds = selectedIdsParam.split(",");
        System.out.println("Number of selected items: " + selectedIds.length);
        
        // Get cart items
        List<Cart> selectedItems = CartDB.getCartsByIds(selectedIds);
        System.out.println("Retrieved items from database: " + (selectedItems != null ? selectedItems.size() : "null"));
        
        if (selectedItems == null || selectedItems.isEmpty()) {
            System.out.println("No items found in database");
            request.setAttribute("message", "No items selected for checkout");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }

        // Calculate total with null checks
        double grandTotal = 0.0;
        for (Cart item : selectedItems) {
            if (item != null) {
                Double itemPrice = item.getPrice();
                Long itemQuantity = item.getQuantity();
                if (itemPrice != null && itemQuantity != null) {
                    grandTotal += itemPrice * itemQuantity;
                }
                System.out.println("Processing item: " + item.getName() + ", Price: " + itemPrice + ", Quantity: " + itemQuantity);
            }
        }

        // Store in session
        session.setAttribute("selectedIds", selectedIds);
        session.setAttribute("selectedItems", selectedItems);
        session.setAttribute("grandTotal", grandTotal);
        
        System.out.println("Redirecting to checkout page");
        response.sendRedirect(request.getContextPath() + "/checkout.jsp");
        
    } catch (Exception e) {
        System.out.println("Error in checkout process:");
        e.printStackTrace();
        request.setAttribute("message", "An error occurred during checkout: " + e.getMessage());
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    return;
}
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}

package Servlet;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class NavigationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String section = request.getParameter("section");
        request.setAttribute("scrollTo", section);
        request.getRequestDispatcher("body.jsp").forward(request, response);
    }
}

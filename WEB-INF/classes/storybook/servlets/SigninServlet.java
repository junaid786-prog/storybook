package storybook.servlets;

import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import storybook.db.*;
import storybook.utility.*;
public class SigninServlet extends HttpServlet{
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        Connection conn = DBConnection.getConnection();
        ResultSet res = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE gmail = ? and password = ?");

            stmt.setString(1, gmail);
            stmt.setString(2, password);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if the user already exists
            if (rs.next()) {
                // Cookie c1 = new Cookie("user", gmail);
                // Cookie c2 = new Cookie("password", password);
                // c1.setMaxAge(60*60*12*30);
                // c2.setMaxAge(60*60*12*30);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", gmail);
                session.setAttribute("password", password);
                session.setAttribute("type", rs.getString("type"));
                session.setMaxInactiveInterval(12 * 60 * 60); // 12 hours
                response.setStatus(200);
                // response.addCookie(c1);
                // response.addCookie(c2);

                if(rs.getString("type").equals("admin")){
                   response.sendRedirect("Dashboard.jsp");
                }
                else{
                   response.sendRedirect("Posts.jsp");
                }
            } else {
                ApiError.sendRequestDispatch(request, response, 404, "Incorrect email or password" + gmail + password);
            }
            stmt.close();
            conn.close();
        } catch(Exception ex){
            ApiError.sendRequestDispatch(request, response, 500, "Server Error Occured");
        }
        out.close();
    }
}
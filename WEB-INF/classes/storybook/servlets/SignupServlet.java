package storybook.servlets;

import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import storybook.db.*;
import storybook.utility.*;

public class SignupServlet extends HttpServlet{
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");

        String error = null;
        if (fname == null || lname == null || gmail == null || password == null){
            ApiError.sendRequestDispatch(request, response, 403, "Please enter all fields");
            return;
        }
        else{
            if(!password.equals(cpassword)){
                ApiError.sendRequestDispatch(request, response, 401, "Passwords must be same");
                return;
            }
            else if (fname.length() < 3 || fname.length() > 10) error = "first name must be between 3 - 10 chars";
            else if (lname.length() < 3 || lname.length() > 10) error = "last name must be between  3 - 10 chars";
            else if (password.length() < 6 || password.length() > 15) error = "password must be between 6 - 15 chars";
            else if (gmail.length() < 5 || gmail.length() > 20) error = "enter valid gmail";

            if(error != null){
                ApiError.sendRequestDispatch(request, response, 401, error);
                return;
            }
        }
        // sql
        Connection conn = DBConnection.getConnection();
        int res = 1;
        try{
            // to check if already exists
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE gmail = ?");
            statement.setString(1, gmail);
            ResultSet users = statement.executeQuery();

            if(users.next()){
                ApiError.sendRequestDispatch(request, response, 400, gmail + " is already present");
            } else {
                String query = "INSERT INTO USER (fname, lname, gmail, password, type) VALUES(?, ?, ?, ?, ?);";
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, fname);
                st.setString(2, lname);
                st.setString(3, gmail);
                st.setString(4, password);
                st.setString(5, "user");
                res = st.executeUpdate();
                
                if(res > 0){
                    // Cookie c1 = new Cookie("user", gmail);
                    // Cookie c2 = new Cookie("password", password);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", gmail);
                    session.setAttribute("password", password);
                    session.setAttribute("type", "user");
                    session.setMaxInactiveInterval(12 * 60 * 60); // 12 hours
                    response.setStatus(200);
                    // response.addCookie(c1);
                    // response.addCookie(c2);
                    response.sendRedirect("Posts.jsp");
                }else{
                    ApiError.sendRequestDispatch(request, response, 500, "User is not saved into database");
                }
                st.close();
            }
            statement.close();
            conn.close();
        } catch(Exception ex){
            ApiError.sendRequestDispatch(request, response, 500, "Error occured on server" + ex.getMessage());
        }
        out.close();
    }
}
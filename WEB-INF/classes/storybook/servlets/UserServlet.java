package storybook.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

import storybook.db.*;
import storybook.utility.*;

public class UserServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Connection conn = DBConnection.getConnection();
        String userId = request.getParameter("id");
        ResultSet user = UserUtility.getProfile(request, response);
        if( user == null){
            ApiError.sendRequestDispatch(request, response, 401, "Login first to access this");
            return;
        }
        if(userId == null){
            ApiError.sendRequestDispatch(request, response, 403, "Enter user id");
            return;
        }
        try{
            int i = Integer.parseInt(userId);
            UserUtility.deleteUser(request, response, user, conn, i);
            return;
        }catch(Exception e){
            ApiError.sendRequestDispatch(request, response, 403, "Enter valid id");
            return;
        }
    }
}
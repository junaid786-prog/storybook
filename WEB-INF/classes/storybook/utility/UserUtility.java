package storybook.utility;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

import storybook.db.*;
public class UserUtility{
    public static ResultSet getProfileByCookies(HttpServletRequest request, HttpServletResponse response){
        Connection conn = DBConnection.getConnection();
        String gmail = "";
        String password = "";

        try {
            PrintWriter out = response.getWriter();
            ResultSet rs = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE gmail = ? and password = ?");
            Cookie [] cookies = request.getCookies();

            if(cookies == null){
                return null;
            }
            else {
                for (Cookie c: cookies){
                    if(c.getName().equals("user")){
                        gmail = c.getValue();
                    }
                    if(c.getName().equals("password")){
                        password = c.getValue();
                    }
                }
            }

            stmt.setString(1, gmail);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs;
            } else {
                return null;
            }
        } catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
    public static ResultSet getProfile(HttpServletRequest request, HttpServletResponse response){
        Connection conn = DBConnection.getConnection();
        String gmail = "";
        String password = "";

        try {
            PrintWriter out = response.getWriter();
            ResultSet rs = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE gmail = ? and password = ?");
            HttpSession session = request.getSession(false);
            if (session == null) {
                return null;
            }
            else {
                gmail = (String)session.getAttribute("user");
                password = (String)session.getAttribute("password");
            }
            if (gmail == null || password == null) return null;

            stmt.setString(1, gmail);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs;
            } else {
                return null;
            }
        } catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
    public static ResultSet getUser(int id){
        Connection conn = DBConnection.getConnection();
        try {
            ResultSet rs = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs;
            } else {
                return null;
            }
        } catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }

    public static boolean isUserLikedPost(int postId, String author){
        Connection conn = DBConnection.getConnection();
        try {
            int authorId;
            ResultSet rs = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE  gmail = ?");

            stmt.setString(1, author);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authorId = rs.getInt("id");
            } else {
                return true;
            }

            stmt = conn.prepareStatement("SELECT * FROM likes WHERE userId = ? AND postId = ?");
            stmt.setInt(1, authorId);
            stmt.setInt(2, postId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch(Exception ex){
            System.out.println(ex);
            return true;
        }
    }

    public static int getPostAuthorid(int postId){
        Connection conn = DBConnection.getConnection();
        int res = -1;
        try {
            ResultSet rs = null;
            String author;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM posts WHERE  id = ?");
            stmt.setInt(1, postId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                author = rs.getString("author");
            } else {
                return -1;
            }
            if(author != null){
                res = getAuthorIdFromName(author);
            }
        } catch(Exception ex){
            return -1;
        }
        return res;
    }

    public static int getAuthorIdFromName(String author){
        Connection conn = DBConnection.getConnection();
        int authorId;
        ResultSet rs = null;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE  gmail = ?");
            stmt.setString(1, author);
            rs = stmt.executeQuery();
            if (rs.next()) {
               authorId = rs.getInt("id");
               return authorId;
            } else {
                return -1;
            }
        }catch(Exception e){
            return -1;
        }
    }
}
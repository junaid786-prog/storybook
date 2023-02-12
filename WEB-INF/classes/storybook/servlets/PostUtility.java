package storybook.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

import storybook.db.*;
import storybook.utility.*;

public class PostUtility extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Connection conn = DBConnection.getConnection();
        PrintWriter out = response.getWriter();
        ResultSet user = UserUtility.getProfile(request, response);

        if( user == null){
            ApiError.sendRequestDispatch(request, response, 401, "Login first to access this");
        }
        String action = request.getParameter("action_name");
        String sId = request.getParameter("id");
        int id = Integer.parseInt(sId);

        if(action.equals("like_post")){
            String query = "SELECT * FROM likes WHERE userId = ? AND postId = ?";
            try{
                int userId = user.getInt("id");
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, id);
                ResultSet r = pstmt.executeQuery();
                if(r.next()){
                    ApiError.sendRequestDispatch(request, response, 403, "You have liked it already");
                    return;
                }
            }catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error " + e);
                return;
            }

            query = "INSERT INTO likes (postId, userId) VALUES (?, ?)";
            try{
                int userId = user.getInt("id");
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                pstmt.setInt(2, userId);
                int r = pstmt.executeUpdate();

                if(r > 0){
                    response.sendRedirect("/StoryBook/Posts.jsp");
                    return;
                }else{
                    ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error occured");
                    return;
                }
            }catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error " + e);
                return;
            }
        }else if(action.equals("comment_post")){
            String comment = request.getParameter("comment");
            String query = "INSERT INTO comments (postId, userId, comment) VALUES (?, ?, ?)";
            try{
                int userId = user.getInt("id");
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                pstmt.setInt(2, userId);
                pstmt.setString(3, comment);
                int r = pstmt.executeUpdate();
                if(r > 0){
                    response.sendRedirect("/StoryBook/Posts.jsp");
                }else{
                    ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error ");
                }
            }catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error " + e);
            }
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Connection conn = DBConnection.getConnection();
        PrintWriter out = response.getWriter();
        ResultSet user = UserUtility.getProfile(request, response);
        if( user == null){
            ApiError.sendRequestDispatch(request, response, 401, "Login first to access this");
        }
        String postId = request.getParameter("postId");
        String query = "SELECT * FROM comments WHERE postId = ?";
        ResultSet comments = null;
        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            int pId = Integer.parseInt(postId);
            pstmt.setInt(1, pId);
            comments = pstmt.executeQuery();
            int length = 0;
            while(comments.next()){
                length += 1;
                int authorId = comments.getInt("userId");
                ResultSet author = UserUtility.getUser(authorId);
                out.println("<div class = 'single_comment'>");
                out.println("<p>" + author.getString("fname") + " " + author.getString("lname") + "</p>");
                out.println("<p>" + comments.getString("comment") + "</p>");
                out.println("</div>");
            }
            if (length == 0) out.println("<div class = 'no_comment_found'> <p> No comments found. Comment first</p> </div>");
        }catch(Exception e){
            ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error " + e);
        }finally{
            out.close();
        }
    }
}
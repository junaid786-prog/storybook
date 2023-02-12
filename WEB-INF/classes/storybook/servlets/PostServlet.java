package storybook.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.nio.file.Paths;
import java.nio.file.*;
import jakarta.servlet.annotation.MultipartConfig;

import storybook.db.*;
import storybook.utility.*;

@MultipartConfig(fileSizeThreshold=1024*1024*10,
    maxFileSize=1024*1024*3,
    maxRequestSize=1024*1024*100)
public class PostServlet extends HttpServlet{
    // 1. create new post
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // DataBase connectivity
        Connection conn = DBConnection.getConnection();
        PrintWriter out = response.getWriter();
        ResultSet user = UserUtility.getProfile(request, response);
        if( user == null){
            ApiError.sendRequestDispatch(request, response, 401, "Login First");
            return;
        }
        String action_type = request.getParameter("action");
        // saving file to images folder

        if (action_type == null || action_type.equals("create_post")){
            String title = request.getParameter("postTitle");
            String description = request.getParameter("postDescription");
            Part filePart = request.getPart("image");
            if (title == null || description == null || filePart == null){
                ApiError.sendRequestDispatch(request, response, 403, "Please enter all fields");
                return;
            }else {
                String error = null;
                if (title.length() < 3 || title.length() > 50) error = "title must be between 3 - 50 chars";
                else if (description.length() < 20 || description.length() > 200) error = "description must be between  20 - 200 chars";
                else if (filePart.getSize() > 1024*1024*2) error = "max file size is 2MB";
                
                if(error != null){
                    ApiError.sendRequestDispatch(request, response, 401, error);
                    return;
                }
            }
            PostsUtils.createPost(request, response, user, conn, title, description, filePart);
            return;
        }
        else if (action_type.equals("delete_post")){
            String id = request.getParameter("id");
            int postId = -1;
            try{
                postId = Integer.parseInt(id);
            } catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 400, "Enter correct id" + id);
                return;
            }
            PostsUtils.deletePost(request, response, user, conn, postId);
            return;
        }
        else if (action_type.equals("update_post")){
            String id = request.getParameter("id");
            int postId = -1;
            try{
                postId = Integer.parseInt(id);
            } catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 400, "Enter correct id" + id);
                return;
            }
            String title = request.getParameter("postTitle");
            String description = request.getParameter("postDescription");
            if (id == null || title == null || description == null){
                ApiError.sendRequestDispatch(request, response, 403, "Please enter all fields");
                return;
            }else {
                String error = null;
                if (title.length() < 3 || title.length() > 50) error = "title must be between 3 - 50 chars";
                else if (description.length() < 20 || description.length() > 200) error = "description must be between  20 - 200 chars";
                if(error != null){
                    ApiError.sendRequestDispatch(request, response, 401, error);
                    return;
                }
            }
            PostsUtils.updatePost(request, response, user, conn, postId, title, description);
            return;
        }
    }
    // 2. read all posts
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        Connection conn = DBConnection.getConnection();

        ResultSet user = UserUtility.getProfile(request, response);
        if( user == null){
            out.println("Login First");
            response.setStatus(401);
            return;
        }
        String searchTerm = request.getParameter("title");
        String targetUser = request.getParameter("author");
        if(conn != null){
            try{
                PreparedStatement pstmt;
                if (searchTerm == null && targetUser == null) pstmt = conn.prepareStatement("SELECT * FROM posts");
                else if (searchTerm != null && targetUser != null){
                    pstmt = conn.prepareStatement("SELECT * FROM posts WHERE title = ? AND author = ?");
                    pstmt.setString(1, searchTerm);
                    pstmt.setString(2, user.getString("gmail"));
                }
                else if (searchTerm == null && targetUser != null){
                    pstmt = conn.prepareStatement("SELECT * FROM posts WHERE author = ?");
                    pstmt.setString(1, user.getString("gmail"));
                }
                else{
                    pstmt = conn.prepareStatement("SELECT * FROM posts WHERE title = ?");
                    pstmt.setString(1, searchTerm);
                }
                ResultSet posts = pstmt.executeQuery();
                int length = 0;
                while(posts.next()){
                    length += 1;
                    int postId = posts.getInt("id");
                    boolean isPostLiked = UserUtility.isUserLikedPost(posts.getInt("id"), user.getString("gmail"));
                    String className;
                    if (isPostLiked) className = "like_post_btn liked_post";
                    else className = "like_post_btn";
                    out.println("<div class = 'single_post' id = " + postId + " onclick = 'showComments(" + postId + ")' isLiked = " + isPostLiked +  ">");
                    out.println("<div class = 'post_content'>");
                    out.println("<div class = 'post_author'> <div id = 'author_profile'> </div>");
                    out.println("<p>" + posts.getString("author") + "</p> </div>");
                    out.println("<h2>" + posts.getString("title") + "</h2>");
                    out.println("<p>" + posts.getString("description") + "</p> </div>");
                    out.println("<img src = '" + posts.getString("path") + "'> </img>");
                    out.println("<div class = 'post_below_section'>");
                    out.println("<p class = '" + className + "' alreadyLiked = '" + isPostLiked + "' onclick = 'doLike(" + postId + ")'> Like </p>");
                    out.println("<p class = 'comment_post_btn' onclick = 'doComment(" + postId + ")'> Comment </p> <p> Share </p>");
                    out.println("</div> </div>");
                }
                if (length == 0){
                    out.println("<div class = 'no_post_found'> <h3> No post found! Create post first to see.</h3> </div>");
                }
                response.setStatus(200);
            }catch(Exception e){
                ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error" + e);
                return;
            }
        }else{
            ApiError.sendRequestDispatch(request, response, 500, "Internal Server Error - connection not established");
            return;
        }
       // out.println("</body>");
    }
    // 3. delete post
    // 4. update a post
}
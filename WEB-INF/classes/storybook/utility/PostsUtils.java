package storybook.utility;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.nio.file.Paths;
import java.nio.file.*;

import storybook.db.*;
import storybook.servlets.*;

public class PostsUtils{
    // createPost()
    // getPosts()
    public static void createPost(HttpServletRequest request, HttpServletResponse response, ResultSet user, Connection conn, String title, String description, Part filePart) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String applicationPath = request.getServletContext().getRealPath(""); // full address
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        long timestamp = System.currentTimeMillis();
        String savePath = applicationPath + "images/posts/" + timestamp + "-" + fileName;
        // save file
        InputStream fileContent = filePart.getInputStream();
        byte [] data = new byte[fileContent.available()];
        fileContent.read(data);
        FileOutputStream fos = new FileOutputStream(savePath);
        fos.write(data);
        fos.close();
        fileContent.close();
        // save image to db
        try{
            String sql = "INSERT INTO posts ( title, description, path, author) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, "images/posts/" +  timestamp + "-" + fileName);
            pstmt.setString(4, user.getString("gmail"));
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
            response.setStatus(201);
            response.sendRedirect("Posts.jsp");
        }catch(Exception e){
            ApiError.sendRequestDispatch(request, response, 500 , "Internal Server Error");
        }
    }

    public static void deletePost(HttpServletRequest request, HttpServletResponse response, ResultSet user, Connection conn, int id) throws IOException, ServletException{
        try{
            //1.  check post author
            int aId = UserUtility.getPostAuthorid(id);
            if(aId == -1){
                ApiError.sendRequestDispatch(request, response, 404 , "Error - post not found");
                return;
            }
            //2. Find Post
            String sql = "SELECT * FROM posts WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet specificPost = pstmt.executeQuery();
            if (!specificPost.next())
            {
                ApiError.sendRequestDispatch(request, response, 404 , "Post not found with this id");
                return;
            }
            //3. Check requesting user (logged in user)
            ResultSet postAuthor = UserUtility.getUser(aId);
            // if user is admin or author can delete this
            if ((UserUtility.getProfile(request, response) != null && UserUtility.getProfile(request, response).getString("type").equals("admin")) || (UserUtility.getProfile(request, response) != null && UserUtility.getProfile(request, response).getString("gmail").equals(specificPost.getString("author")))){
                sql = "DELETE FROM posts WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }else{
                ApiError.sendRequestDispatch(request, response, 403 , "You are not authorized to this");
                return;
            }
            pstmt.close();
            conn.close();
            response.setStatus(201);
            response.sendRedirect("Posts.jsp");
        }catch(Exception e){
            ApiError.sendRequestDispatch(request, response, 500 , "Internal Server Error - " + e);
        }
    }

    public static void updatePost(HttpServletRequest request, HttpServletResponse response, ResultSet user, Connection conn, int id, String title, String description) throws IOException, ServletException{
        try{
            //1.  check post author
            int aId = UserUtility.getPostAuthorid(id);
            if(aId == -1){
                ApiError.sendRequestDispatch(request, response, 404 , "Error - post not found");
                return;
            }
            //2. Find Post
            String sql = "SELECT * FROM posts WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet specificPost = pstmt.executeQuery();
            if (!specificPost.next())
            {
                ApiError.sendRequestDispatch(request, response, 404 , "Post not found with this id");
                return;
            }
            //3. Check requesting user (logged in user)
            ResultSet postAuthor = UserUtility.getUser(aId);
            // if user is admin or author can delete this
            if ((UserUtility.getProfile(request, response) != null && UserUtility.getProfile(request, response).getString("type").equals("admin")) || (UserUtility.getProfile(request, response) != null && UserUtility.getProfile(request, response).getString("gmail").equals(specificPost.getString("author")))){
                sql = "UPDATE posts SET title = ?, description = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(3, id);
                pstmt.setString(1, title);
                pstmt.setString(2, description);
                pstmt.executeUpdate();
                pstmt.executeUpdate();
            }else{
                ApiError.sendRequestDispatch(request, response, 403 , "You are not authorized to this");
                return;
            }
            pstmt.close();
            conn.close();
            response.setStatus(201);
            response.sendRedirect("Posts.jsp");
        }catch(Exception e){
            ApiError.sendRequestDispatch(request, response, 500 , "Internal Server Error - " + e);
        }
    }

    public static ResultSet getAllPosts(){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt;
        ResultSet posts;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM posts");
            posts = pstmt.executeQuery();
        } catch(Exception exc){
            return null;
        }
        return posts;
    }

    public static ResultSet getMyPosts(String gmail){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt;
        ResultSet posts;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM posts WHERE author = ?");
            pstmt.setString(1, gmail);
            posts = pstmt.executeQuery();
        } catch(Exception exc){
            return null;
        }
        return posts;
    }

    public static ResultSet getSinglePost(int id){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt;
        ResultSet post;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM posts WHERE id = ?");
            pstmt.setInt(1, id);
            post = pstmt.executeQuery();
        } catch(Exception exc){
            return null;
        }
        return post;
    }

    public static ResultSet getPostComments(int pId){
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM comments WHERE postId = ?";
        ResultSet comments = null;
        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pId);
            comments = pstmt.executeQuery();
            return comments;
        }catch(Exception e){
            return null;
        }
    }
    // deletePost()
    // updatePost()
    // getPostLikes()
    // getPostComments
}
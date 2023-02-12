package storybook.utility;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.nio.file.Paths;
import java.nio.file.*;

import storybook.db.*;
import storybook.servlets.*;

public class PostUtility{
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

    public static int me(int a){
        return 3;
    }
    // deletePost()
    // updatePost()
    // getPostLikes()
    // getPostComments
}
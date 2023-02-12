<%@ page
  import="java.sql.*, storybook.utility.*"
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StoryBook - posts</title>
    <link rel="stylesheet" href="css/post.css">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<% 
String isValidSession = (String)session.getAttribute("type");
if (isValidSession == null) response.sendRedirect("index.jsp"); 
%>
<% 
  String postId = request.getParameter("postId");
  if (postId == null){
    response.sendRedirect("Posts.jsp"); 
    return;
  }
  int id = Integer.parseInt(postId);
  ResultSet user = UserUtility.getProfile(request, response);
  ResultSet post = PostsUtils.getSinglePost(id);
  if(post.next()){
    String author = post.getString("author");
    String title = post.getString("title");
    String description = post.getString("description");
    String path = post.getString("path");
    String className;
    boolean isPostLiked = UserUtility.isUserLikedPost(post.getInt("id"), user.getString("gmail"));
    if (isPostLiked) className = "like_post_btn liked_post";
    else className = "like_post_btn";
%>
<jsp:include page="components/reusables/navbar.jsp">
  <jsp:param name="type" value="<%= isValidSession %>" />
</jsp:include>
<div class="posts_container">
<div class="posts_container_ single_post_container">
    <jsp:include page="/components/reusables/post_.jsp">
        <jsp:param name="title" value="<%= title %>" />
        <jsp:param name="description" value="<%= description %>" />
        <jsp:param name="author" value="<%= author %>" />
        <jsp:param name="path" value="<%= path %>" />
        <jsp:param name="id" value="<%= id %>" />
        <jsp:param name="classname" value="<%= className %>" />
    </jsp:include>
</div>
<div class='comments_section'>
<%
  // post comments
  ResultSet comments = PostsUtils.getPostComments(id);
  int length = 0;
    while(comments.next()){
        length += 1;
        int authorId = comments.getInt("userId");
        ResultSet commentor = UserUtility.getUser(authorId);
        out.println("<div class = 'single_comment'>");
        out.println("<p>" + commentor.getString("fname") + " " + commentor.getString("lname") + "</p>");
        out.println("<p>" + comments.getString("comment") + "</p>");
        out.println("</div>");
    }
    if (length == 0) out.println("<div class = 'no_comment_found'> <p> No comments found. Comment first</p> </div>");
    
}
   else{
    out.println("No post found with this is");
   }
%>
</div>
</div>


</body>
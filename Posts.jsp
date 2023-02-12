<%@ page
  language="java"
  import="java.sql.*, storybook.db.*, storybook.servlets.*, storybook.utility.*"
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
   <% String type = (String) session.getAttribute("type");%>
   <% if (type == null) response.sendRedirect("index.jsp");
    else if(type.equals("admin")) response.sendRedirect("Dashboard.jsp");
    %>
    <div class="posts_container" id = "posts_container">
        <jsp:include page="components/reusables/sidebar.jsp">
          <jsp:param name="type" value="<%= type %>" />
        </jsp:include>
        
        <div class = "posts_section">
          <form class="search_post" action = "SinglePost.jsp">
            <input type="text" name = "postId" id = "search_post"></input>
            <button>Search</button>
          </form>
          <div class = "all_posts">
            <% if (type != null){%>
              <jsp:include page="components/user/Posts.jsp"/>
              <%} %>
          </div>
        </div>
        <div id = "login_error"></div>
    </div>
    
    <%-- <script src="js/posts.js"></script> --%>
</body>
</html>
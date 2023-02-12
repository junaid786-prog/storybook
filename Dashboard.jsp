<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StoryBook - ADMIN</title>
    <link rel="stylesheet" href="css/post.css">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
   <% String type = (String) session.getAttribute("type");%>
   <% if (type == null) response.sendRedirect("index.jsp");
    else if(!type.equals("admin")) response.sendRedirect("Posts.jsp");
    %>
    <jsp:include page = "components/reusables/navbar.jsp">
           <jsp:param name="type" value= "<%= type %>" />
    </jsp:include>
    <div class = "main_section_">
      <div class="main_section_heading">
         <h3> POSTS </h3>
      </div>
      <div class = 'admin_posts'>
        <jsp:include page="components/admin/Posts.jsp"/>
      </div>
    </div>

</body>
</html>
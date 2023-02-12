<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StoryBook - posts</title>
    <link rel="stylesheet" href="css/post.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
   <% String type = (String) session.getAttribute("type");%>
   <% if (type == null) response.sendRedirect("index.jsp");
    else if(type.equals("admin")) response.sendRedirect("Dashboard.jsp");
    %>
    <div class="container">
        <jsp:include page = "components/reusables/navbar.jsp">
           <jsp:param name="type" value= "<%= type %>" />
        </jsp:include>
        <div class = "post_form form">
           <%
            if (type != null){
            %>
                <jsp:include page="components/forms/DeletePostForm.jsp" />
            <%} else out.println("Login first to delete post");
            %>
        </div>
    </div>
</body>
</html>
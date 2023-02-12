<%@ page
 import = "storybook.db.DBConnection"
 language = "java"
  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Story Book</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
    <div class="container">
        <%
         String type = (String)session.getAttribute("type");
        %> 
        <jsp:include page = "components/reusables/navbar.jsp">
           <jsp:param name="type" value= "<%= type %>" />
        </jsp:include>
        <div class="main_section">
            <h1> Welcome 
            <% if (type != null) out.println("as " + type); %>
             To Story Book </h1>
            <div>
            </div>
        </div>
    </div>
    <script src="js/index.js"></script>
</body>
</html>
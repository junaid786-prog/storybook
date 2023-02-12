<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StoryBook - Sign In</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
    <%
      String type = (String) session.getAttribute("type");
    %>
    <div class="container">
        <jsp:include page="components/reusables/navbar.jsp">
          <jsp:param name="type" value="<%= type %>" />
        </jsp:include>
        <div class="form">
            <%@ include file = "components/forms/SignupForm.jsp" %>
        </div>
    </div>
    <script src="js/registeration.js"></script>
</body>
</html>
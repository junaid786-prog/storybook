<%@ page
  language="java"
  isErrorPage="true"
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Storybook - Error</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<% 
 String message = (String)request.getAttribute("message");
%>
<body>
  <div class="container">
        <%
         String type = (String)session.getAttribute("type");
        %> 
        <jsp:include page = "components/reusables/navbar.jsp">
          <jsp:param name="type" value= "<%= type %>" />
        </jsp:include>
        <div class="main_section">
         <div class = "error_message_show">
        <%
          if (message == null){
            if(response.getStatus() == 404){
              out.println("404 - Page Not Found! ");
            } else {
              out.println("No error expected" + response.getStatus());
            }
          }
          else {%>
          <%= "Error: code - " + response.getStatus() + " " + message  %>

          <% 
            if(exception != null){
              out.println(exception);
            }
          } %>
         </div>
        </div>
    </div>
</body>
</html>
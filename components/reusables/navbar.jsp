<%
 String type = (String)session.getAttribute("type");
%>
<div class="nav_bar">
    <div class="logo">STORYBOOK</div>
        <div class="links">
            <a href = "index.jsp">Home</a>
            <%
              if (type == null ){
                out.println("<a href = 'Signin.jsp'>Sign In</a>");
                out.println("<a href = 'Signup.jsp'>Sign Up</a>");
              } else{
                if (type.equals("user")) out.println("<a href = 'Posts.jsp'>Dashboard</a>");
                else if(type.equals("admin")){
                  out.println(("<a href = 'Dashboard.jsp'>Dashboard</a>"));
                  out.println(("<a href = 'CreatePost.jsp'>Create Post</a>"));
                }
                out.println("<a href = 'processjsp/signout.jsp'>Log Out</a>");
              }
            %>
        </div>
    </div>
</div>
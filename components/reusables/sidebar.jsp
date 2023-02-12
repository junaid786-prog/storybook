<% String type = (String)session.getAttribute("type"); %>
<div class="sidebar">
    <div class="st_logo">STORYBOOK</div>
    <div class="sidebar_combo">
        <a href="index.jsp">Home</a>
    </div>
    <div class="sidebar_combo">
        <% if(type != null) out.println("<a href='CreatePost.jsp'>Create Post</a>"); %>
    </div>
    <div class="sidebar_combo">
        <% if(type != null) out.println("<a href='UpdatePost.jsp'>Update Posts</a>"); %>
    </div>
    <div class="sidebar_combo">
        <% if(type != null) out.println("<a href='Posts.jsp'>All Posts</a>"); %>
    </div>
    <div class="sidebar_combo">
        <% if(type != null) out.println("<a href='DeletePost.jsp'>Delete Post</a>"); %>
    </div>
    <div class="sidebar_combo">
        <% if(type != null) out.println("<a href='processjsp/signout.jsp'>Log Out</a>"); %>
    </div>
    <div class="sidebar_combo">
         <% if(type == null) out.println("<a href='Signin.jsp'>Sign In</a>"); %>
    </div>
</div>
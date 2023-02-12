<%
  String author = request.getParameter("author");
  String title = request.getParameter("title");
  String description = request.getParameter("description");
  String id = request.getParameter("id");
  String path = request.getParameter("path");
  String className = request.getParameter("classname");
%>
<div class='single_post'>
    <div class='post_content'>
        <div class='post_author'>
            <div id='author_profile'> </div>
            <p><%= author %></p>
        </div>
        <h2>ID: <%= id + " " %> <%= title %></h2>
        <p><%= description %></p>
    </div>
    <img src="<%= path %>"> </img>
    <div class='post_below_section'>
        <form action = "posts/like" method = "POST">
          <input class = "hidden" name = "action_name" value = "like_post"></input>
          <button class="<%= className %>" name = "id" value = "<%= id %>">Like </button>
        </form>
        <form action = "posts/comment" method = "POST">
          <input class = "hidden" name = "action_name" value = "comment_post"></input>
          <input class = "hidden" name = "comment" value = "Nice Looking"></input>
          <button class='comment_post_btn' name = "id" value = "<%= id %>"> Comment </button>
        </form>
        <form action = "SinglePost.jsp">
          <button class='comment_post_btn' name = "postId" value = "<%= id %>"> Details </button>
        </form>
    </div>
</div>
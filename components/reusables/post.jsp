<%
  String author = request.getParameter("author");
  String title = request.getParameter("title");
  String description = request.getParameter("description");
  String id = request.getParameter("id");
%>

<div class = "admin_post">
  <p> <%= id %> </p>
  <p> <%= author %> </p>
  <p> <%= title %> </p>
  <%-- <p> <%= description %> </p> --%>
  <div>
  <form id="delete_post_form" action="create" method="post">
    <input class = "hidden" name = "id" value = "<%= id %>"> </input>
    <button type="submit" name = "action" value="delete_post">Delete</button>
  </form>
  </div>
  <div>
  <form id="delete_post_form" action="UpdatePost.jsp">
    <button type="submit">Update</button>
  </form>
  </div>
</div>
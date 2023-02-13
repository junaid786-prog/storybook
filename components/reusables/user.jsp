<%
  String user = request.getParameter("username");
  String id = request.getParameter("id");
%>

<div class = "admin_post">
  <p> <%= id %> </p>
  <p> <%= user %> </p>
  <div>
  <form id="delete_user_form" action="delete" method="post">
    <input class = "hidden" name = "id" value = "<%= id %>"> </input>
    <button type="submit" name = "action" value="delete_post">Delete</button>
  </form>
  </div>
</div>
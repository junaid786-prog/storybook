<%@ page
 import="storybook.servlets.*, storybook.utility.*, java.sql.*"
 %>
 <% 
 ResultSet res = PostsUtils.getAllPosts();
 while (res.next()){
    String author = res.getString("author");
    String title = res.getString("title");
    String description = res.getString("description");
    String path = res.getString("path");
    String id = res.getString("id");
 %>
  <jsp:include page="/components/reusables/post.jsp">
    <jsp:param name="title" value="<%= title %>" />
    <jsp:param name="description" value="<%= description %>" />
    <jsp:param name="author" value="<%= author %>" />
    <jsp:param name="path" value="<%= path %>" />
    <jsp:param name="id" value="<%= id %>" />
  </jsp:include>
 <% } %>

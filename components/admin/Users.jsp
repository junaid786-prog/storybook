<%@ page
 import="storybook.servlets.*, storybook.utility.*, java.sql.*"
 %>
 <% 
 ResultSet res = UserUtility.getAllUsers();
 while (res.next()){
    String username = res.getString("gmail");
    String id = res.getString("id");
 %>
  <jsp:include page="/components/reusables/user.jsp">
    <jsp:param name="username" value="<%= username %>" />
    <jsp:param name="id" value="<%= id %>" />
  </jsp:include>
 <% } %>

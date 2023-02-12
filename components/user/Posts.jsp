<%@ page
  language="java"
  import="java.sql.*, storybook.db.*, storybook.servlets.*, storybook.utility.*"
%>    
<%
              int length = 0;
              ResultSet res = PostsUtils.getAllPosts();
              ResultSet user = UserUtility.getProfile(request, response);
              while(res.next()){
                String author = res.getString("author");
                String title = res.getString("title");
                String description = res.getString("description");
                String path = res.getString("path");
                String id = res.getString("id");
                length += 1;
                int postId = res.getInt("id");
                boolean isPostLiked = UserUtility.isUserLikedPost(res.getInt("id"), user.getString("gmail"));
                String className;
                if (isPostLiked) className = "like_post_btn liked_post";
                else className = "like_post_btn";
                %>
                <jsp:include page="/components/reusables/post_.jsp">
                    <jsp:param name="title" value="<%= title %>" />
                    <jsp:param name="description" value="<%= description %>" />
                    <jsp:param name="author" value="<%= author %>" />
                    <jsp:param name="path" value="<%= path %>" />
                    <jsp:param name="id" value="<%= id %>" />
                    <jsp:param name="classname" value="<%= className %>" />
                </jsp:include>
                <%}
                if(length == 0){
                    out.println("<div class = 'no_post_found'> <h3> No post found! Create post first to see.</h3> </div>");
                }
%>
package storybook.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

import storybook.utility.*;

public class SignoutServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if(session == null){
            response.setStatus(401);
            out.print("session expired");
        }
        session.invalidate();
        response.setStatus(200);
        out.println("logged out successfully");
        response.sendRedirect("/StoryBook/index.jsp");
    }
}
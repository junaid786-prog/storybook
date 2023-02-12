package storybook.utility;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

public class ApiError extends Exception{
    int status;
    String message;

    public ApiError(String msg, int status){
        super(msg);
        this.message = msg;
        this.status = status;
    }
    public ApiError(String msg, int status, HttpServletResponse response){
        super(msg);
        // PrintWriter out = response.getWriter();
        this.message = msg;
        this.status = status;
        response.setStatus(status);
        // out.println(message);
        return;
    }

    public String getMessage(){
        return this.message;
    }

    public int getStatus(){
        return this.status;
    }

    public void print(HttpServletResponse response) throws IOException, ServletException{
        response.getWriter().println(this.message);
        response.setStatus(this.status);
    }

    public static void sendRequestDispatch(HttpServletRequest request, HttpServletResponse response, int status, String message) throws ServletException, IOException{
        request.setAttribute("message", message);
        response.setStatus(status);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
        dispatcher.forward(request, response);
        return;
    }
}
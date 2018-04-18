package com.ivantk.skproj.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="second", urlPatterns = "/second")
public class SecondServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("country", request.getParameter("country"));
        session.setAttribute("address", request.getParameter("address"));
        session.setAttribute("telephone", request.getParameter("telephone"));
        if("Prev".equals(request.getParameter("secondButton")))
            response.sendRedirect("index.jsp");
        else
            response.sendRedirect("thirdPage.jsp");
    }
}

package com.ivantk.skproj.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="third", urlPatterns = "/third")
public class ThirdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.setAttribute("age", request.getParameter("age"));
        session.setAttribute("about", request.getParameter("about"));
        if("Prev".equals(request.getParameter("thirdButton")))
            response.sendRedirect("secondPage.jsp");
        else
            response.sendRedirect("finishPage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SecondPost");
    }
}

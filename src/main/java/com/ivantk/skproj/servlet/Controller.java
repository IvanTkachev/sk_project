package com.ivantk.skproj.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/welcome");
        if("delete".equals(req.getParameter("action")))
            requestDispatcher = req.getRequestDispatcher("/delete");
        else if("add".equals(req.getParameter("action")))
            requestDispatcher = req.getRequestDispatcher("/add");
        requestDispatcher.forward(req, resp);
    }

}

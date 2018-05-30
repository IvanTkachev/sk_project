package com.ivantk.skproj.servlet;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.services.ProductService;
import com.ivantk.skproj.services.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "add", urlPatterns = "/add")
public class AddServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        ProductService productService = new ProductServiceImpl();
        HttpSession session = request.getSession();
        String addProduct = request.getParameter("add_product");
        int countProduct =0;
        if(request.getParameter("count_product") != null)
         countProduct = Integer.valueOf(request.getParameter("count_product"));
        String store;
        if(session.getAttribute("name_store") == null)
            store = "RootStore";
        else store = session.getAttribute("name_store").toString();
        if(addProduct != null && productService.findProduct(addProduct, store) == null){
            productService.addProduct(new Product(0, addProduct, countProduct), store);
            session.setAttribute("Error", "");
        }
        else
            session.setAttribute("Error", "Something wrong");
        response.sendRedirect("/welcome");
    }
}

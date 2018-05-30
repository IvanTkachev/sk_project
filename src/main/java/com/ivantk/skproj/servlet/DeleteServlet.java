package com.ivantk.skproj.servlet;

import com.ivantk.skproj.services.ProductService;
import com.ivantk.skproj.services.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        ProductService productService = new ProductServiceImpl();
        HttpSession session = request.getSession();
        String deleteProduct = request.getParameter("delete_product");
        String store;
        if(session.getAttribute("name_store") == null)
            store = "RootStore";
        else store = session.getAttribute("name_store").toString();
        if(deleteProduct != null && productService.findProduct(deleteProduct, store) != null){
            productService.deleteProduct(deleteProduct, store);
            session.setAttribute("Error", "");
        }
        else
            session.setAttribute("Error", "Something wrong");
        response.sendRedirect("/welcome");
    }
}

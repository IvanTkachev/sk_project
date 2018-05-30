package com.ivantk.skproj.servlet;


import com.ivantk.skproj.connectionPool.DbConnectionPool;
import com.ivantk.skproj.services.ProductService;
import com.ivantk.skproj.services.StoreService;
import com.ivantk.skproj.services.impl.ProductServiceImpl;
import com.ivantk.skproj.services.impl.StoreServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The Class Controller.
 */
@WebServlet(name="welcome", urlPatterns = "/welcome")
public class MainController extends HttpServlet {

    /**
     * Inits the servlet.
     *
     * @param config the config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductServiceImpl();
        StoreService storeService = new StoreServiceImpl();
        HttpSession session = req.getSession();
        String store = req.getParameter("store");
        if(!"".equals(store) && store != null){
            session.setAttribute("Error", "");
            session.setAttribute("name_store", store);
        }

        if("".equals(session.getAttribute("name_store")) || session.getAttribute("name_store") == null){
            session.setAttribute("products", productService.getAllProducts("RootStore"));
            session.setAttribute("name_store", "RootStore");
        }
        else
            session.setAttribute("products", productService.getAllProducts(session.getAttribute("name_store").toString()));
        session.setAttribute("stores", storeService.getAllStores());
        resp.sendRedirect("welcome.jsp");
    }

}

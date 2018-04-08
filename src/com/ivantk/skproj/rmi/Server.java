package com.ivantk.skproj.rmi;

import com.ivantk.skproj.services.impl.ProductServiceImpl;
import com.ivantk.skproj.services.impl.StoreServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Class that realise server which works on the basis of rmi.
 *
 * @author Ivan Tkachev
 */
public class Server {
    public static void main(String[] args) throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        Context context = new InitialContext();
        context.rebind("rmi://localhost:1099/database", new ProductServiceImpl());
        context.rebind("rmi://localhost:1099/database1", new StoreServiceImpl());
        System.out.println("Ready!");
    }
}

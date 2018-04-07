package com.ivantk.skproj.rmi;

import com.ivantk.skproj.stax.StAXServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Server {
    public static void main(String[] args) throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        Context context = new InitialContext();
        context.rebind("rmi://localhost:1099/stax", new StAXServiceImpl());
        System.out.println("Ready!");
    }
}

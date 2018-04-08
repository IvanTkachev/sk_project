package com.ivantk.skproj.rmi;

import com.ivantk.skproj.stax.StAXServiceImpl;
import org.xml.sax.SAXException;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Context context = new InitialContext();
            context.rebind("rmi://localhost:1099/stax", new StAXServiceImpl());
            System.out.println("Ready!");
        }
        catch ( SAXException e){
            System.out.println("Incorrect data in products.xml, Server shutted down!");
            System.exit(1);
        }

    }
}

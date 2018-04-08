package com.ivantk.skproj.stax;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface XMLService extends Remote {

    File productsFile = new File("src/resources/products.xml");
    String schemaLocation = "/products.xsd";

    void addProduct(Product product, String storeName) throws RemoteException;

    List<Product> getAllProducts(String storeName) throws RemoteException;

    void deleteProduct(String productName, String storeName) throws RemoteException;

    Product findProduct(String productName, String storeName) throws RemoteException;

    List<Store> getAllStores() throws RemoteException;

}

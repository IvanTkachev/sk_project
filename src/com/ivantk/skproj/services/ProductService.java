package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {
    //Done
    void addProduct(Product product, String storeName) throws RemoteException;

    //Done
    List<Product> getAllProducts(String storeName) throws RemoteException;

    //Done
    void updateProduct(Product oldProduct, Product newProduct, String oldStoreName, String newStoreName) throws RemoteException;

    //Done
    void deleteProduct(String productName, String storeName) throws RemoteException;

    //Done
    Product findProduct(String productName, String storeName) throws RemoteException;
}

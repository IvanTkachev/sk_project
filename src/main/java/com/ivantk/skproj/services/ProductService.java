package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Product;

import java.rmi.RemoteException;
import java.util.List;

public interface ProductService {

    void addProduct(Product product, String storeName);

    List<Product> getAllProducts(String storeName);

    void updateProduct(Product oldProduct, Product newProduct, String oldStoreName, String newStoreName) throws RemoteException;

    void deleteProduct(String productName, String storeName) throws RemoteException;

    Product findProduct(String productName, String storeName) throws RemoteException;
}

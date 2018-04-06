package com.ivantk.skproj.stax;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface XMLService extends Remote {

    File productFile = new File("src/resources/product.xml");
    File storeFile = new File("src/resources/store.xml");
    File schemaLocation = new File("src/resources/products.xsd");

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


    //Done
    List<Store> getAllStores() throws RemoteException;

    //Done
    void addStore(String name) throws RemoteException;

    //Done
    Store findStore(String name) throws RemoteException;

    //Done
    void deleteStore(String name) throws RemoteException;

    //Done
    void updateStore(String oldStore, String newStore) throws RemoteException;

}

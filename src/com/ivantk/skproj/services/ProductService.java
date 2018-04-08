package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service class for {@link Product} that extend rmi interface {@link Remote}.
 *
 *
 * @author Ivan Tkachev
 */
public interface ProductService extends Remote {

    /**
     * Function that adds a product to database in store with specified name.
     *
     * @param product product to add
     * @param storeName store in which the product is placed
     *
     * @throws RemoteException check remote exception
     */
    void addProduct(Product product, String storeName) throws RemoteException;

    /**
     * Function that returns a list of products by specified store.
     *
     * @param storeName store on which products are located
     * @return list of {@link Product}
     *
     * @throws RemoteException check remote exception
     */
    List<Product> getAllProducts(String storeName) throws RemoteException;

    /**
     * Function that update a product or replace it from other store.
     *
     *
     * @param oldProduct product to update
     * @param newProduct product after the update
     * @param oldStoreName store in which the product is placed
     * @param newStoreName store in which the product is placed after update
     *
     * @throws RemoteException check remote exception
     */
    void updateProduct(Product oldProduct, Product newProduct, String oldStoreName, String newStoreName) throws RemoteException;

    /**
     * Function that delete a product from database in store with specified name.
     *
     * @param productName product name to delete
     * @param storeName store name in which the product is placed
     *
     * @throws RemoteException check remote exception
     */
    void deleteProduct(String productName, String storeName) throws RemoteException;

    /**
     * Function that returns a product in specified store. Return null, in case there is no product.
     *
     * @param productName product are looking for
     * @param storeName store on which products are located
     * @return {@link Product}
     *
     * @throws RemoteException check remote exception
     */
    Product findProduct(String productName, String storeName) throws RemoteException;
}

package com.ivantk.skproj.dao;


import com.ivantk.skproj.entities.Product;

import java.util.List;

/**
 * Basic Data Access Object interface.
 * Provide operations with {@link Product}.
 */
public interface ProductDao {
    //Done
    void addProduct(Product product, int storeId);

    //Done
    List<Product> getAllProducts(int idStore);

    //Done
    void updateProduct(Product oldProduct, Product newProduct, int oldStoreId, int newStoreId);

    //Done
    void deleteProduct(String productName, int storeId);

    //Done
    Product findProduct(String productName, int idStore);

    //Done
    void checkProductName(String productName);

    //Done
    int getCountOfProduct(String productName);

    //Done
    void deleteProductName(String productName);

    //Done
    void deleteProductsByStore(int idStore);

}

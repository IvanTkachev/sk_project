package com.ivantk.skproj.dao;


import com.ivantk.skproj.entities.Product;

import java.util.List;

/**
 * Basic Data Access Object interface.
 * Provide operations with {@link Product}.
 *
 * @author Ivan Tkachev
 */
public interface ProductDao {

    void addProduct(Product product, int storeId);

    List<Product> getAllProducts(int idStore);

    void updateProduct(Product oldProduct, Product newProduct, int oldStoreId, int newStoreId);

    void deleteProduct(String productName, int storeId);

    Product findProduct(String productName, int idStore);

    void checkProductName(String productName);

    int getCountOfProduct(String productName);

    void deleteProductName(String productName);

    void deleteProductsByStore(int idStore);

}

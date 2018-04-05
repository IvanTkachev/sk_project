package com.ivantk.skproj.services.impl;

import com.ivantk.skproj.dao.ProductDao;
import com.ivantk.skproj.dao.StoreDao;
import com.ivantk.skproj.dao.impl.ProductDaoImpl;
import com.ivantk.skproj.dao.impl.StoreDaoImpl;
import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.services.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    private StoreDao storeDao = new StoreDaoImpl();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public ProductServiceImpl() throws RemoteException {
    }


    @Override
    public void addProduct(Product product, String storeName) {
        if (productDao.findProduct(product.getName(), storeDao.getStoreIdByStoreName(storeName)) == null) {
            storeDao.checkStoreName(storeName);
            productDao.checkProductName(product.getName());
            productDao.addProduct(product, storeDao.getStoreIdByStoreName(storeName));
            System.out.println(ANSI_GREEN + "Product " + product.getName() + " added." + ANSI_RESET);
        } else
            System.out.println(ANSI_RED + product.getName() + " - this product already exists" + ANSI_RESET);

    }

    @Override
    public List<Product> getAllProducts(String storeName) {
        List<Product> products = productDao.getAllProducts(storeDao.getStoreIdByStoreName(storeName));
        if (products.isEmpty())
            System.out.println(ANSI_RED + "There is no products in " + storeName + ANSI_RESET);
        return products;
    }

    @Override
    public void updateProduct(Product oldProduct, Product newProduct, String oldStoreName, String newStoreName) {
        if (productDao.findProduct(oldProduct.getName(), storeDao.getStoreIdByStoreName(oldStoreName)) != null &&
                (productDao.findProduct(newProduct.getName(), storeDao.getStoreIdByStoreName(oldStoreName)) == null
                        || newProduct.getName().equals(oldProduct.getName()))) {
            //
            storeDao.checkStoreName(newStoreName);
            productDao.updateProduct(oldProduct, newProduct, storeDao.getStoreIdByStoreName(oldStoreName),
                    storeDao.getStoreIdByStoreName(newStoreName));
            System.out.println(ANSI_GREEN + "Product " + newProduct.getName() + " updated." + ANSI_RESET);
            //
        } else System.out.println(ANSI_RED + "There is no " + oldProduct.getName() + " in " + oldStoreName + " \n" +
                "of " + newProduct.getName() + " already exist" + ANSI_RESET);
    }

    @Override
    public void deleteProduct(String productName, String storeName) {
        Product findProduct = productDao.findProduct(productName, storeDao.getStoreIdByStoreName(storeName));
        if (findProduct != null) {
            if (productDao.getCountOfProduct(productName) == findProduct.getCount()) {
                productDao.deleteProduct(productName, storeDao.getStoreIdByStoreName(storeName));
                productDao.deleteProductName(productName);
                System.out.println(ANSI_GREEN + "Product " + productName + " deleted from " + storeName + ANSI_RESET);
            } else {
                productDao.deleteProduct(productName, storeDao.getStoreIdByStoreName(storeName));
                System.out.println(ANSI_GREEN + "Product " + productName + " deleted from " + storeName + ANSI_RESET);
            }

        } else System.out.println(ANSI_RED + "There is no " + productName + " in " + storeName + "!" + ANSI_RESET);

    }

    @Override
    public Product findProduct(String productName, String storeName) {
        return productDao.findProduct(productName, storeDao.getStoreIdByStoreName(storeName));
    }

}

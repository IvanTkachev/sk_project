package com.ivantk.skproj.services.impl;

import com.ivantk.skproj.dao.ProductDao;
import com.ivantk.skproj.dao.StoreDao;
import com.ivantk.skproj.dao.impl.ProductDaoImpl;
import com.ivantk.skproj.dao.impl.StoreDaoImpl;
import com.ivantk.skproj.entities.Store;
import com.ivantk.skproj.services.StoreService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StoreServiceImpl implements StoreService {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private StoreDao storeDao = new StoreDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();

    public StoreServiceImpl() {
    }

    @Override
    public List<Store> getAllStores() {
        return storeDao.getAllStores();
    }

    @Override
    public void addStore(String storeName) {
        if (storeDao.findStore(storeName) == null) {
            storeDao.addStore(storeName);
            System.out.println(ANSI_GREEN + storeName + " added." + ANSI_RESET);
        } else
            System.out.println(ANSI_RED + storeName + " - this store already exists" + ANSI_RESET);
    }

    @Override
    public Store findStore(String storeName) {
        return storeDao.findStore(storeName);
    }

    @Override
    public void deleteStore(String storeName) {
        if (storeDao.findStore(storeName) != null) {
            productDao.deleteProductsByStore(storeDao.getStoreIdByStoreName(storeName));
            storeDao.deleteStore(storeName);
            System.out.println(ANSI_GREEN + storeName + " and all products at this store deleted." + ANSI_RESET);
        } else System.out.println(ANSI_RED + storeName + " - there is no such store" + ANSI_RESET);
    }

    @Override
    public void updateStore(String oldStore, String newStore) {
        if (storeDao.findStore(oldStore) != null && storeDao.findStore(newStore) == null) {
            storeDao.updateStore(oldStore, newStore);
            System.out.println(ANSI_GREEN + newStore + " updated." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + oldStore + " - - there is no such store \n" +
                    " or " + newStore + "already exist" + ANSI_RESET);
        }

    }
}

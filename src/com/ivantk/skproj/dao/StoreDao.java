package com.ivantk.skproj.dao;

import com.ivantk.skproj.entities.Store;

import java.util.List;

public interface StoreDao {
    //Done
    List<Store> getAllStores();

    //Done
    void addStore(String name);

    //Done
    Store findStore(String name);

    //Done
    void deleteStore(String name);

    //Done
    void updateStore(String oldStore, String newStore);

    //Done
    int getStoreIdByStoreName(String storeName);

    //Done
    void checkStoreName(String storeName);
}

package com.ivantk.skproj.dao;

import com.ivantk.skproj.entities.Store;

import java.util.List;

/**
 * Basic Data Access Object interface.
 * Provide operations with {@link Store}.
 *
 * @author Ivan Tkachev
 */
public interface StoreDao {

    List<Store> getAllStores();

    void addStore(String name);

    Store findStore(String name);

    void deleteStore(String name);

    void updateStore(String oldStore, String newStore);

    int getStoreIdByStoreName(String storeName);

    void checkStoreName(String storeName);
}

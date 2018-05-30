package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Store;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface StoreService {

    List<Store> getAllStores();

    void addStore(String name);

    Store findStore(String name);

    void deleteStore(String name);

    void updateStore(String oldStore, String newStore);

}

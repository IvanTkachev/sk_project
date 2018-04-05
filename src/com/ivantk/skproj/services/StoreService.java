package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Store;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StoreService extends Remote {
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

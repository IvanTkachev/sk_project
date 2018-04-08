package com.ivantk.skproj.services;

import com.ivantk.skproj.entities.Store;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service class for {@link Store} that extend rmi interface {@link Remote}.
 *
 * @author Ivan Tkachev
 */
public interface StoreService extends Remote {

    /**
     * Function that returns a list of existing stores.
     *
     * @return list of {@link Store}
     *
     * @throws RemoteException check remote exception
     */
    List<Store> getAllStores() throws RemoteException;

    /**
     * Function that adds a store to database in store with specified name.
     *
     * @param name store name of new store
     *
     * @throws RemoteException check remote exception
     */
    void addStore(String name) throws RemoteException;

    /**
     * Function that returns a store if it's exist. In other case return null.
     *
     * @param name store are looking for
     * @return {@link Store}
     *
     * @throws RemoteException check remote exception
     */
    Store findStore(String name) throws RemoteException;

    /**
     * Function that delete a store if it's exist. In other case do nothing.
     *
     * @param name store name to delete
     *
     * @throws RemoteException check remote exception
     */
    void deleteStore(String name) throws RemoteException;

    /**
     * Function that update a product or replace it from other store.
     *
     * @param oldStore store name to update
     * @param newStore store name after the update
     *
     * @throws RemoteException check remote exception
     */
    void updateStore(String oldStore, String newStore) throws RemoteException;

}

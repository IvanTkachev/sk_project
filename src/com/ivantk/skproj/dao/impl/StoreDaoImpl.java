package com.ivantk.skproj.dao.impl;

import com.ivantk.skproj.dao.DAO;
import com.ivantk.skproj.dao.StoreDao;
import com.ivantk.skproj.entities.Store;
import com.ivantk.skproj.services.SqlService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDaoImpl extends DAO implements StoreDao {

    @Override
    public List<Store> getAllStores() {
        Connection connection = poolInst.getConnection();
        List<Store> allStores = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_ALL_STORES));
            ResultSet stores = statement.executeQuery();
            while (stores.next()) {
                allStores.add(new Store(stores.getInt(1), stores.getString(2)));
            }
            stores.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return allStores;
    }

    @Override
    public void addStore(String storeName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_ADD_STORE));
            statement.setString(1, storeName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public Store findStore(String name) {
        Connection connection = poolInst.getConnection();
        Store store = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_STORE));
            statement.setString(1, name);
            ResultSet storeSet = statement.executeQuery();
            while (storeSet.next()) {
                store = new Store(storeSet.getInt(1), storeSet.getString(2));
            }

            storeSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return store;
    }

    @Override
    public void deleteStore(String storeName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_DELETE_STORE));
            statement.setString(1, storeName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public void updateStore(String oldStore, String newStore) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_UPDATE_STORE));
            statement.setString(1, newStore);
            statement.setString(2, oldStore);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public int getStoreIdByStoreName(String storeName) {
        Connection connection = poolInst.getConnection();
        int storeId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_STORE_ID));
            statement.setString(1, storeName);
            ResultSet storeSet = statement.executeQuery();
            while (storeSet.next()) {
                storeId = storeSet.getInt(1);
            }

            storeSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return storeId;
    }

    @Override
    public void checkStoreName(String storeName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement selectCountStatement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_STORE_NAME));
            selectCountStatement.setString(1, storeName);
            ResultSet productNameResultSet = selectCountStatement.executeQuery();
            String result = null;
            while (productNameResultSet.next())
                result = productNameResultSet.getString(1);

            if (result == null) {
                PreparedStatement addCountStatement = connection.prepareStatement(sql.getProperty(SqlService.SQL_ADD_STORE));
                addCountStatement.setString(1, storeName);
                addCountStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {

        ProductDaoImpl productDao = new ProductDaoImpl();
        StoreDaoImpl storeDao = new StoreDaoImpl();

        storeDao.checkStoreName("Store4");
    }

}

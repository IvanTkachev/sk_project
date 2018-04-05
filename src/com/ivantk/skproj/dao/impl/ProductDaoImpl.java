package com.ivantk.skproj.dao.impl;


import com.ivantk.skproj.dao.DAO;
import com.ivantk.skproj.dao.ProductDao;
import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.services.SqlService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends DAO implements ProductDao {

    @Override
    public void addProduct(Product product, int storeId) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.getProperty(SqlService.SQL_SELECT_PRODUCT_ID));
            statement.setString(1, product.getName());
            ResultSet resultSet = statement.executeQuery();

            PreparedStatement addStatement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_ADD_PRODUCT));

            while (resultSet.next())
                addStatement.setInt(1, resultSet.getInt(1));
            addStatement.setInt(2, storeId);
            addStatement.setInt(3, product.getCount());

            addStatement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public List<Product> getAllProducts(int idStore) {
        Connection connection = poolInst.getConnection();
        List<Product> allProducts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_GET_ALL_PRODUCTS));
            statement.setInt(1, idStore);
            ResultSet products = statement.executeQuery();
            while (products.next()) {
                allProducts.add(new Product(products.getInt(1),
                        products.getString(2),
                        products.getInt(3)));
            }

            products.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return allProducts;
    }


    @Override
    public void updateProduct(Product oldProduct, Product newProduct, int oldStoreId, int newStoreId) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_UPDATE_PRODUCT));
            statement.setString(1, newProduct.getName());
            statement.setInt(2, newStoreId);
            statement.setInt(3, newProduct.getCount());
            statement.setString(4, oldProduct.getName());
            statement.setInt(5, oldStoreId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public void deleteProduct(String productName, int storeId) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_DELETE_PRODUCT));
            statement.setString(1, productName);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public Product findProduct(String productName, int idStore) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_PRODUCT));
            statement.setString(1, productName);
            statement.setInt(2, idStore);
            ResultSet product = statement.executeQuery();
            Product resultProduct = null;
            while (product.next())
                resultProduct = new Product(Integer.parseInt(product.getString(1)),
                        product.getString(2),
                        Integer.parseInt(product.getString(3)));
            return resultProduct;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return null;
    }

    @Override
    public void checkProductName(String productName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement selectCountStatement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_SELECT_PRODUCT_NAME));
            selectCountStatement.setString(1, productName);
            ResultSet productNameResultSet = selectCountStatement.executeQuery();
            String result = null;
            while (productNameResultSet.next())
                result = productNameResultSet.getString(1);

            if (result == null) {
                PreparedStatement addCountStatement = connection.prepareStatement(sql.getProperty(SqlService.SQL_ADD_PRODUCT_NAME));
                addCountStatement.setString(1, productName);
                addCountStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public int getCountOfProduct(String productName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_GET_COUNT_OF_PRODUCT));
            statement.setString(1, productName);
            ResultSet product = statement.executeQuery();
            int resultProduct = 0;
            while (product.next())
                resultProduct = product.getInt(1);
            return resultProduct;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
        return 0;
    }

    @Override
    public void deleteProductName(String productName) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_DELETE_PRODUCT_NAME));
            statement.setString(1, productName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

    @Override
    public void deleteProductsByStore(int idStore) {
        Connection connection = poolInst.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.
                    getProperty(SqlService.SQL_DELETE_PRODUCTS_BY_STORE));
            statement.setInt(1, idStore);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolInst.footConnection(connection);
        }
    }

}
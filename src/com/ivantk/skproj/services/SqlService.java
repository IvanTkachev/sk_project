package com.ivantk.skproj.services;


import com.ivantk.skproj.connectionPool.DbConnectionPool;

import java.util.ResourceBundle;

public class SqlService {

    private static SqlService instance;
    private static ResourceBundle bundle;



    private static final String SQL_FILE = "sql";

    public static final String SQL_GET_ALL_PRODUCTS =           "SQL_GET_ALL_PRODUCTS";
    public static final String SQL_SELECT_PRODUCT =             "SQL_SELECT_PRODUCT";
    public static final String SQL_SELECT_PRODUCT_NAME =        "SQL_SELECT_PRODUCT_NAME";
    public static final String SQL_ADD_PRODUCT_NAME =           "SQL_ADD_PRODUCT_NAME";
    public static final String SQL_ADD_PRODUCT =                "SQL_ADD_PRODUCT";
    public static final String SQL_SELECT_PRODUCT_ID =          "SQL_SELECT_PRODUCT_ID";
    public static final String SQL_GET_COUNT_OF_PRODUCT =       "SQL_GET_COUNT_OF_PRODUCT";
    public static final String SQL_DELETE_PRODUCT_NAME =        "SQL_DELETE_PRODUCT_NAME";
    public static final String SQL_UPDATE_PRODUCT =             "SQL_UPDATE_PRODUCT";
    public static final String SQL_DELETE_PRODUCT =             "SQL_DELETE_PRODUCT";

    public static final String SQL_ADD_STORE =                  "SQL_ADD_STORE";
    public static final String SQL_SELECT_STORE =               "SQL_SELECT_STORE";
    public static final String SQL_SELECT_STORE_NAME =          "SQL_SELECT_STORE_NAME";
    public static final String SQL_SELECT_STORE_ID =            "SQL_SELECT_STORE_ID";
    public static final String SQL_DELETE_STORE =               "SQL_DELETE_STORE";
    public static final String SQL_UPDATE_STORE =               "SQL_UPDATE_STORE";
    public static final String SQL_SELECT_ALL_STORES =          "SQL_SELECT_ALL_STORES";
    public static final String SQL_DELETE_PRODUCTS_BY_STORE =   "SQL_DELETE_PRODUCTS_BY_STORE";



    private SqlService(){}

    public static SqlService getInstance(){
        if(instance == null){
            synchronized (DbConnectionPool.class){
                instance = new SqlService();
                bundle = ResourceBundle.getBundle(SQL_FILE);
            }
        }
        return instance;
    }

    public String getProperty(String key){
        return bundle.getString(key);
    }

}

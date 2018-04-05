package com.ivantk.skproj.dao;


import com.ivantk.skproj.connectionPool.DbConnectionPool;
import com.ivantk.skproj.services.SqlService;

/**
 * The class that initializes {@link DbConnectionPool} and {@link SqlService}
 */
public abstract class DAO {

    protected static DbConnectionPool poolInst;
    protected static SqlService sql;

    protected DAO() {
        poolInst = DbConnectionPool.getInstance();
        sql = SqlService.getInstance();
    }
}

package com.ivantk.skproj.entities;

import java.io.Serializable;

/**
 * This class realize product that can be added to database.
 *
 * @author Ivan Tkachev
 */
public class Product implements Serializable {

    /**
     * id of product
     */
    private int id;

    /**
     * name of product
     */
    private String name;

    /**
     * count of product
     */
    private int count;

    /**
     * This constructs a product with specified name and count.
     *
     * @param name     name of product
     * @param count   count of product
     */
    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    /**
     * This constructs a product with specified id, name and count.
     *
     * @param id     id of product
     * @param name   name of product
     * @param count  count of product
     */
    public Product(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    /**
     * @return id of product
     */
    public int getId() {
        return id;
    }

    /**
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * @return count of product
     */
    public int getCount() {
        return count;
    }

    /**
     * This sets new name to product.
     *
     * @param name new name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This sets new count to product.
     *
     * @param count new count of product
     */
    public void setCount(int count) {
        this.count = count;
    }
}

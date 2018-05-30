package com.ivantk.skproj.entities;

import java.io.Serializable;

/**
 * This class realize product that can be added to database.
 *
 * @author Ivan Tkachev
 */
public class Product implements Serializable {

    private int id;

    private String name;

    private int count;

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Product(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

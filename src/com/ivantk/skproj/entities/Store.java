package com.ivantk.skproj.entities;

import java.io.Serializable;

/**
 * This class realize store that can be added to database.
 *
 * @author Ivan Tkachev
 */
public class Store implements Serializable {

    /**
     * id of store
     */
    private int id;

    /**
     * name of store
     */
    private String name;

    /**
     * This constructs a store with specified id and name.
     *
     * @param id     id of store
     * @param name   name of store
     */
    public Store(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of store
     */
    public int getId() {
        return id;
    }

    /**
     * This sets new id to store.
     *
     * @param id new id of store
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name of store
     */
    public String getName() {
        return name;
    }

    /**
     * This sets new name to store.
     *
     * @param name new name of store
     */
    public void setName(String name) {
        this.name = name;
    }
}

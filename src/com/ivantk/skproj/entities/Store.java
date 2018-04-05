package com.ivantk.skproj.entities;

import java.io.Serializable;

public class Store implements Serializable {
    int id;
    String name;

    public Store(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.search;

/**
 *
 * @author Caique
 */
public enum SearchTypeInstance {
    POSTS (1, "posts"),
    TAGS  (2, "tags");
    
    private int id;
    private String name;

    private SearchTypeInstance (int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

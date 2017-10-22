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
public enum SearchTypeParameter {
    BY_RAW   (0, ""),
    BY_ID    (1, "id"),
    BY_TAGS  (2, "tags"),
    BY_MD5   (3, "md5");
    
    private int id;
    private String name;

    private SearchTypeParameter (int id, String name){
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

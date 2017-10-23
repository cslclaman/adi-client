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
public enum SearchFormat {
    FORMAT_DEFAULT  (0, "null"),
    FORMAT_JSON     (1, "json"),
    FORMAT_XML      (2, "xml");

    private int id;
    private String name;

    private SearchFormat(int id, String name) {
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

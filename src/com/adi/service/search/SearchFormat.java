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
    
    /**
     * Indica que resultados serão obtidos como JSON e lidos por meio do Google GSON.
     */
    FORMAT_JSON     (1, "json"),
    
    /**
     * Indica que resultados serão obtidos como XML e lidos por meio do SAX Parser.
     */
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

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.view.models;

/**
 *
 * @author Caique
 */
public enum StatusMonitorScale {
    SCALE_DEFAULT   (0, "null"),
    SCALE_PERCENT   (1, "Percent"),
    SCALE_ABSOLUTE  (2, "Absolute value");
    
    private int id;
    private String name;

    private StatusMonitorScale(int id, String name) {
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

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

/**
 *
 * @author Caique
 */
public class Serie extends Info {
    private String altName;

    public Serie(AdiTag adiTag) {
        super(adiTag);
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }
    
    
}

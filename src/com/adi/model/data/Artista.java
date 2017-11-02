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
public class Artista extends Info {
    private boolean active;

    public Artista(AdiTag adiTag) {
        super(adiTag);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}

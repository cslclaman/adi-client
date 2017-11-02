/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.adi.data.entity.AdiTag;

/**
 *
 * @author Caique
 */
public class Item extends Info {
    private int category;

    public Item(AdiTag adiTag) {
        super(adiTag);
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}

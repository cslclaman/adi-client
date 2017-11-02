/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.adi.data.entity.AdiTag;
import java.util.Objects;

/**
 *
 * @author Caique
 */
public abstract class Info {
    private String name;
    private String comment;
    private AdiTag adiTag; 
    
    public Info(AdiTag adiTag){
        this.adiTag = adiTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AdiTag getAdiTag() {
        return adiTag;
    }

    public void setAdiTag(AdiTag adiTag) {
        this.adiTag = adiTag;
    }

    @Override
    public String toString() {
        return adiTag.getPrettyType() + ": " + name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.adiTag);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        } else {
            if (!(obj instanceof Info)){
                return false;
            } else {
                Info oth = (Info) obj;
                return oth.name.equalsIgnoreCase(this.name) && oth.adiTag.equals(this.adiTag);
            }
        }
    }
}

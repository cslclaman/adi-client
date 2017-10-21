/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance.model;

import java.util.Arrays;

/**
 *
 * @author Caique
 */
public class TagLists {

    private String[] tagsQAC;
    private String[] tagsOPP;
    private String[] tagsPFNV;

    public String[] getTagsQAC() {
        return tagsQAC;
    }

    public void setTagsQAC(String[] tagsQAC) {
        this.tagsQAC = tagsQAC;
    }

    public String[] getTagsOPP() {
        return tagsOPP;
    }

    public void setTagsOPP(String[] tagsOPP) {
        this.tagsOPP = tagsOPP;
    }

    public String[] getTagsPFNV() {
        return tagsPFNV;
    }

    public void setTagsPFNV(String[] tagsPFNV) {
        this.tagsPFNV = tagsPFNV;
    }
    
    public static TagLists defaultTagLists() {
        TagLists t = new TagLists();
        
        t.tagsQAC = new String[]{
            "undressing",
            "topless",
            "ass",
            "bottomless",
            "cameltoe",
            "partially_visible_vulva",
            "nude",
            "vibrator",
            "covered_nipples",
            "bdsm"
        };
        t.tagsOPP = new String[]{
            "bandaids_on_nipples",
            "nipples",
            "pussy",
            "penis",
            "implied_sex",
            "object_insertion",
            "pussy_juice",
            "cum",
            "areolae"
        };
        t.tagsPFNV = new String[]{
            "sex",
            "vaginal",
            "anal",
            "ejaculation",
            "female_ejaculation",
            "guro",
            "rape"
        };
        
        return t;
    }

    @Override
    public String toString() {
        return "TagLists{" + "tagsQAC=" + Arrays.toString(tagsQAC) + ", tagsOPP=" + Arrays.toString(tagsOPP) + ", tagsPFNV=" + Arrays.toString(tagsPFNV) + '}';
    }
    
    
    
}

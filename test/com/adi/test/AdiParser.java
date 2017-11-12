/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.service.tags.AdiTagsModel;

/**
 *
 * @author Caique
 */
public class AdiParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String ts = "(ADI) (sd)2741429 (r)tlb (a)hillly_(maiwetea) (c)touhou (np)1 (p)remilia_scarlet (i)bluehair (i)dress (i)hat (i)openmouth (i)ribbon (i)shorthair (i)wings";
        AdiTagsModel m = new AdiTagsModel(ts);
        System.out.println(m.getTagString());
        System.out.println(m.getTagString(80));
        System.out.println(m.getArtistString());
        System.out.println(m.getCopyrightString());
        System.out.println(m.getItemString());
        System.out.println(m.getPersonaString());
    }
    
}

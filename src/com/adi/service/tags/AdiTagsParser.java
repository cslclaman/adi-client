/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.tags;

/**
 *
 * @author Caique
 */
public class AdiTagsParser {
    public AdiTagsParser(){
        
    }
    
    public AdiTagsModel toAdiTags(String tagString){
        AdiTagsModel model = new AdiTagsModel();
        String[] tags = tagString.split(" ");
        for (String tag : tags){
            tag = tag.trim();
            if (isValid(tag)){
                String par = getParameter(tag);
                String val = getValue(tag);
                switch (getType(tag)){
                    //(ADI) (s1)2 (r)1 (a)1 (c)1 (np)1 (p)1 (i)1 (x)1
                    case "s":
                        model.setSource(par, val);
                        break;
                    case "r":
                        model.setRating(val);
                        break;
                    case "a":
                        model.addArtist(val);
                        break;
                    case "c":
                        model.addCopyright(val);
                        break;
                    case "n":
                        model.setPersonaCount(val);
                        break;
                    case "p":
                        model.addPersona(val);
                        break;
                    case "i":
                        model.addItem(val);
                        break;
                    case "x":
                        model.addError(val);
                        break;
                }
            }
        }
        return model;
    }
    
    public String fromAdiTags(AdiTagsModel model){
        return model.getTagString();
    }
    
    private boolean isValid(String tag) {
        if (!tag.isEmpty()){
            return tag.equals("(ADI)") || tag.matches("(\\([sacipnx]?.\\))([\\(\\)a-zA-Z0-9_\\-]+)");
        } else {
            return false;
        }
    }
    
    private String getType(String tag) {
        int ap = tag.indexOf("(");
        return tag.substring(ap + 1, ap + 2);
    }
    
    private String getParameter(String tag) {
        int ap = tag.indexOf("(");
        int fp = tag.indexOf(")");
        return tag.substring(ap + 2, fp);
    }
    
    private String getValue(String tag) {
        int fp = tag.indexOf(")");
        return tag.substring(fp + 1);
    }
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.data.entity;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Caique
 */
public class AdiTag {
    public static final String TIPO_ARTISTA = "a";
    public static final String TIPO_PERSONAGEM = "p";
    public static final String TIPO_ITEM = "i";
    public static final String TIPO_SERIE = "c";
    
    private Integer id;
    private String type;
    private String tag;
    private List<Image> imageList;
    private List<Tag> tagList;
    
    public AdiTag() {
    }

    public AdiTag(String type, String tag) {
        this.type = type;
        this.tag = tag;
    }

    public AdiTag(String adiTag) {
        int ap = adiTag.indexOf("(");
        int fp = adiTag.indexOf(")");
        
        if (ap > -1 && fp == ap + 2){
            this.type = adiTag.substring(ap + 1, fp);
            this.tag = adiTag.substring(ap + 1, fp);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setType(int type, String source) {
        if (source.equals("Danbooru")){
            switch (type){
                case 0:
                    this.type = "i";
                    break;
                case 1:
                    this.type = "a";
                    break;
                case 3:
                    this.type = "c";
                    break;
                case 4:
                    this.type = "p";
                    break;
                default:
                    break;
            }
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "(" + type + ")" + tag;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        } else {
            if (!(obj instanceof AdiTag)){
                return false;
            } else {
                AdiTag a = this;
                AdiTag b = (AdiTag)obj;
                return (a.type.equals(b.type) && a.tag.equalsIgnoreCase(b.tag));
            }
        }
    }

    public String getPrettyType() {
        switch (type){
            case "c":
                return "Série";
            case "p":
                return "Personagem";
            case "i":
                return "Item";
            case "a":
                return "Artista";
            default:
                return "";
        }
    }

}

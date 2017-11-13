/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.google.gson.annotations.SerializedName;
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
    
    private int id;
    private String type;
    private String ident;
    private String param;
    
    @SerializedName(value = "name", alternate = {"tag"})
    private String tag;
    private List<Image> imageList;
    private List<Tag> tagList;
    
    public AdiTag(String type, String tag) {
        this.type = type;
        this.tag = tag;
        initType();
    }

    public AdiTag(String adiTag) {
        int ap = adiTag.indexOf("(");
        int fp = adiTag.indexOf(")");
        if (ap > -1 && fp > ap + 1){
            type = adiTag.substring(ap + 1, fp);
            tag = adiTag.substring(fp + 1);
        } else {
            type = adiTag;
            tag = "";
        }
        initType();
    }

    public AdiTag(String ident, String param, String tag) {
        this.type = ident + param;
        this.tag = tag;
        this.ident = ident;
        this.param = param;
    }
    
    public static boolean isValid(String tag){
        if (!tag.isEmpty()){
            //\(([sacipnx])(.?.?)\)([\(\)a-zA-Z0-9_\+\-]+)?
            //G1 - Ident  G2 - Param  G3 - Value
            return tag.matches("\\(([sracipnx])(.?.?)\\)([\\(\\)a-zA-Z0-9_\\+\\-]+)?");
        } else {
            return false;
        }
    }
    
    private void initType(){
        if (!type.isEmpty()){
            if (type.equalsIgnoreCase("ADI")){
                ident = type;
                param = "";
            } else {
                ident = type.substring(0, 1);
                param = type.substring(1);
            }
        } else {
            ident = param = "";
        }
    }
    
    public boolean isTransient(){
        return ident.equals("a") || ident.equals("c") || ident.equals("i") || ident.equals("p");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        initType();
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
        initType();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTypeIdent() {
        if (ident == null || ident.isEmpty()){
            initType();
        }
        return ident;
    }

    public void setTypeIdent(String ident) {
        this.ident = ident;
    }

    public String getTypeParameter() {
        if (param == null || param.isEmpty()){
            initType();
        }
        return param;
    }

    public void setTypeParameter(String param) {
        this.param = param;
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

    public String getPrettyType(){
        return getPrettyType(ident);
    }
    
    public String getPrettyType(String type) {
        switch (type.toLowerCase()){
            case "ADI":
                return "ADI";
            case "s":
                return "Origem";
            case "r":
                return "Classif. Etária";
            case "a":
                return "Artista";
            case "c":
                return "Série";
            case "p":
                return "Personagem";
            case "i":
                return "Item";
            case "n":
                return "Número de tags" + (param.isEmpty() ? "" : " de " + getPrettyType(param));
            case "x":
                return "Observações";
            default:
                return "";
        }
    }

}

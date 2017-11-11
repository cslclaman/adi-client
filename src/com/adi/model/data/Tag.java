/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.adi.service.function.StringTag;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Caique
 */
public class Tag {
    private Integer id;
    
    @SerializedName(value = "name", alternate = {"tag"})
    private String tag;
    private String url;
    private List<ImageSource> imageSourceList;
    private AdiTag adiTag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
        this.url = StringTag.urlify(tag);
    }

    public Tag(String tag, String url) {
        this.tag = tag;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ImageSource> getImageSourceList() {
        return imageSourceList;
    }

    public void setImageSourceList(List<ImageSource> imageSourceList) {
        this.imageSourceList = imageSourceList;
    }

    public AdiTag getAdiTag() {
        return adiTag;
    }

    public void setAdiTag(AdiTag adiTag) {
        this.adiTag = adiTag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null){
            return false;
        } else {
            if (!(object instanceof Tag)){
                return false;
            } else {
                Tag other = (Tag)object;
                return this.tag.equalsIgnoreCase(other.tag);
            }
        }
    }

    @Override
    public String toString() {
        return "com.adi.entity.Tag[ id=" + id + " ]";
    }
    
}

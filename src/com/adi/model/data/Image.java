/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Caique
 */
public class Image {
    private Integer id;
    private String md5;
    private String filePath;
    private String tagString;
    private String rating;
    private boolean active;
    private long fileSize;
    private String fileSource;
    private Date creationDate;
    private Date lastUpdate;
    private String sourceName;
    private List<AdiTag> adiTagList;
    private ImageSource primarySource;
    private List<ImageSource> imageSourceList;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSource() {
        return fileSource;
    }

    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<AdiTag> getAdiTagList() {
        return adiTagList;
    }

    public void setAdiTagList(List<AdiTag> adiTagList) {
        this.adiTagList = adiTagList;
    }

    public ImageSource getPrimarySource() {
        return primarySource;
    }

    public void setPrimarySource(ImageSource primarySource) {
        this.primarySource = primarySource;
    }

    public List<ImageSource> getImageSourceList() {
        return imageSourceList;
    }

    public void setImageSourceList(List<ImageSource> imageSourceList) {
        this.imageSourceList = imageSourceList;
    }

    @Override
    public String toString() {
        return id + " - " + filePath;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.md5);
        hash = 43 * hash + Objects.hashCode(this.creationDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        } else {
            if (!(obj instanceof Image)){
                return false;
            } else {
                Image ins = (Image)obj;
                return (this.md5.equals(ins.md5));
            }
        }
    }
}

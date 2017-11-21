/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Caique
 */
public class Image {
    /*
    self.__md5 = dict['md5']
    self.__filePath = dict['file_path']
    self.__tagString = dict['tag_string']
    self.__rating = dict['rating']
    self.__active = dict['active']
    self.__fileSize = dict['file_size']
    self.__fileSource = dict['file_source']
    self.__sourceName = dict['source_name']
    */
    private Integer id;
    
    private String md5;
    
    @SerializedName(value = "file_path")
    private String filePath;
    
    @SerializedName(value = "tag_string")
    private String tagString;
    
    private String rating;
    
    private boolean active;
    
    @SerializedName(value = "file_size")
    private long fileSize;
    
    @SerializedName(value = "file_size")
    private String fileSource;
    
    private Date creationDate;
    
    private Date lastUpdate;
    
    @SerializedName(value = "source_name")
    private String sourceName;
    
    private List<AdiTag> adiTagList;
    private ImageSource primarySource;
    private List<ImageSource> imageSourceList;

    public Image() {
        adiTagList = new LinkedList<>();
        imageSourceList = new LinkedList<>();
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

    public String getAbstractFilePath() {
        return filePath;
    }
    
    public String getFilePath() {
        String f = File.separator;
        return filePath.replaceAll("$", f);
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

    public void addAdiTag(AdiTag adiTag) {
        adiTagList.add(adiTag);
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

    public void addImageSource(ImageSource imageSource) {
        imageSourceList.add(imageSource);
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

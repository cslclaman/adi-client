/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source;

import static com.adi.model.source.Searchable.TYPE_POST;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Representa um post como visto em diversos imageboards.
 * <br>Como nota, esse formato de post é compatível com sistemas baseados em Danbooru 1.
 * @author Caique
 */
public class Post implements Searchable {
    protected int id;
    protected String md5;
    protected String source;
    protected String rating;
    
    @SerializedName(value = "file_url", alternate = "file-url")
    protected String fileUrl;
    
    @SerializedName(value = "created_at", alternate = "created-at")
    protected Date createdAt;
    
    @SerializedName(value = "image_width", alternate = {"width", "image-width"})
    protected int width;
    
    @SerializedName(value = "image_height", alternate = {"height", "image-height"})
    protected int height;
    
    @SerializedName(value = "tag_string", alternate = {"tags", "tag-string"})
    protected String tagString;
    
    @SerializedName(value = "file_size", alternate = "file-size")
    protected long fileSize;
    
    @SerializedName(value = "status", alternate = "status")
    protected String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private static String[] deserialize(String tagString) {
        if (tagString.isEmpty()){
            return new String[0];
        } else {
            return tagString.split(" ");
        }
    }

    @Override
    public String[] supportedSourceTypeList() {
        return new String[]{
            "Danbooru",
            "Danbooru2",
            "Gelbooru",
            "Moebooru",
        };
    }
    
    @Override
    public String getSearchableType() {
        return TYPE_POST;
    }
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source;

import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.instance.model.TagLists;
import com.adi.service.search.SearchTypeInstance;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.util.Date;

/**
 * Representa um post como visto em diversos imageboards.
 * <br>Como nota, esse formato de post é compatível com sistemas baseados em Danbooru 1.
 * @author Caique
 */
public class Post implements Searchable {
    private Source sourceInst;
    
    protected int id;
    protected String md5;
    protected String source;
    protected String rating;
    
    @SerializedName(value = "file_url", alternate = "file-url")
    protected String fileUrl;
    
    @SerializedName(value = "image_width", alternate = {"width", "image-width"})
    protected int width;
    
    @SerializedName(value = "image_height", alternate = {"height", "image-height"})
    protected int height;
    
    @SerializedName(value = "tag_string", alternate = {"tags", "tag-string"})
    protected String tagString;
    
    @SerializedName(value = "file_size", alternate = "file-size")
    protected long fileSize;
    
    @SerializedName(value = "status")
    protected String status;

    protected Date creationDate;

    @Override
    public void setSourceInstance(Source source) {
        sourceInst = source;
    }

    @Override
    public Source getSourceInstance() {
        return sourceInst;
    }
    
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
        if (creationDate == null){
            return new Date();
        } else {
            return creationDate;
        }
    }

    public void setCreatedAt(Date createdAt) {
        this.creationDate = createdAt;
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
    
    public String[] getTagStringList(){
        if (tagString.isEmpty()){
            return new String[0];
        } else {
            return tagString.split(" ");
        }
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
    
    public boolean isActive() {
        return status.equals("active");
    }
    
    public boolean isPending() {
        return status.equals("pending");
    }
    
    public boolean isFlagged() {
        return status.equals("flagged");
    }
    
    public boolean isDeleted() {
        return status.equals("deleted");
    }
    
    public boolean isBanned() {
        return tagString.contains("banned_artist");
    }
    
    public boolean isCensored(){
        return false;
    }
    
    public String getAdiRating(){
        String r = "qac";
        TagLists tlist;
        try {
            tlist = new Configuration().getTagLists();
        } catch (IOException ex){
            System.err.println(ex.toString());
            tlist = TagLists.defaultTagLists();
        }
        int pointsQAC = 0;
        int pointsOPP = 0;
        int pointsPFNV = 0;
        for (String tag : getTagStringList()){
            if (tlist.tagsQACcontainsTag(tag)){
                pointsQAC ++;
            }
            if (tlist.tagsOPPcontainsTag(tag)){
                pointsOPP ++;
            }
            if (tlist.tagsPFNVcontainsTag(tag)){
                pointsPFNV ++;
            }
        }
        
        if (rating.equals("s")){
            if (pointsQAC < 1){
                r = "tlb";
            } else {
                r = "qac";
                if (pointsOPP > 1){
                    r = "opp";
                }
            }
        } else {
            if (rating.equals("q")){
                if (pointsPFNV < 1){
                    if (pointsOPP < 1){
                        r = "qac";
                    } else {
                        r = "opp";
                    }
                } else {
                    r = "pfnv";
                }
            } else {
                if (rating.equals("e") == true){
                    r = "pfnv";
                }
            }
        }
        
        return r;
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
    public SearchTypeInstance getSearchableType() {
        return SearchTypeInstance.POSTS;
    }

    /**
     * 
     * @return "Post" e ID, MD5, URL e tag String geral.
     */
    @Override
    public String toString(){
        return "Post " + id + " - " + md5 + "\n\t" + fileUrl + "\n\t" + tagString;
    }
}

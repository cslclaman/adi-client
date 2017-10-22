/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source.danbooru;

import com.adi.model.source.DanbooruModel;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Representa um post do Danbooru2, conforme retornado pela API.
 * <br>Inclui ID, data de criação, MD5, classif. etária, dimensões e URL da imagem, tag strings e flags, dentre outros dados.
 * @author Caique
 */
public class DanbooruPost implements DanbooruModel {
    
    private int id;
    
    @SerializedName(value = "created_at", alternate = "created-at")
    private Date createdAt;
    
    private String source;
    
    private String md5;
    
    private String rating;
    
    @SerializedName(value = "image_width", alternate = "image-width")
    private int imageWidth;
    
    @SerializedName(value = "image_height", alternate = "image-height")
    private int imageHeight;
    
    @SerializedName(value = "tag_string", alternate = "tag-string")
    private String tagString;
    
    @SerializedName(value = "file_ext", alternate = "file-ext")
    private String fileExt;
    
    @SerializedName(value = "tag_count_general", alternate = "tag-count-general")
    private int tagCountGeneral;
    
    @SerializedName(value = "tag_count_artist", alternate = "tag-count-artist")
    private int tagCountArtist;
    
    @SerializedName(value = "tag_count_character", alternate = "tag-count-character")
    private int tagCountCharacter;
    
    @SerializedName(value = "tag_count_copyright", alternate = "tag-count-copyright")
    private int tagCountCopyright;
    
    @SerializedName(value = "file_size", alternate = "file-size")
    private long fileSize;
    
    @SerializedName(value = "is_pending", alternate = "is-pending")
    private boolean pending;
    
    @SerializedName(value = "is_flagged", alternate = "is-flagged")
    private boolean flagged;
    
    @SerializedName(value = "is_deleted", alternate = "is-deleted")
    private boolean deleted;
    
    @SerializedName(value = "tag_count", alternate = "tag-count")
    private int tagCount;
    
    @SerializedName(value = "updated_at", alternate = "updated-at")
    private Date updatedAt;
    
    @SerializedName(value = "is_banned", alternate = "is-banned")
    private boolean banned;
    
    @SerializedName(value = "tag_string_artist", alternate = "tag-string-artist")
    private String tagStringArtist;
    
    @SerializedName(value = "tag_string_character", alternate = "tag-string-character")
    private String tagStringCharacter;
    
    @SerializedName(value = "tag_string_copyright", alternate = "tag-string-copyright")
    private String tagStringCopyright;
    
    @SerializedName(value = "tag_string_general", alternate = "tag-string-general")
    private String tagStringGeneral;
    
    @SerializedName(value = "file_url", alternate = "file-url")
    private String fileUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getTagString() {
        return tagString;
    }
    
    public String[] listTag_string(){
        return deserialize(tagString);
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public int getTagCountGeneral() {
        return tagCountGeneral;
    }

    public void setTagCountGeneral(int tagCountGeneral) {
        this.tagCountGeneral = tagCountGeneral;
    }

    public int getTagCountArtist() {
        return tagCountArtist;
    }

    public void setTagCountArtist(int tagCountArtist) {
        this.tagCountArtist = tagCountArtist;
    }

    public int getTagCountCharacter() {
        return tagCountCharacter;
    }

    public void setTagCountCharacter(int tagCountCharacter) {
        this.tagCountCharacter = tagCountCharacter;
    }

    public int getTagCountCopyright() {
        return tagCountCopyright;
    }

    public void setTagCountCopyright(int tagCountCopyright) {
        this.tagCountCopyright = tagCountCopyright;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getTagStringArtist() {
        return tagStringArtist;
    }

    public String[] listTag_string_artist(){
        return deserialize(tagStringArtist);
    }
    
    public void setTagStringArtist(String tagStringArtist) {
        this.tagStringArtist = tagStringArtist;
    }

    public String getTagStringCharacter() {
        return tagStringCharacter;
    }

    public String[] listTag_string_character(){
        return deserialize(tagStringCharacter);
    }
    
    public void setTagStringCharacter(String tagStringCharacter) {
        this.tagStringCharacter = tagStringCharacter;
    }

    public String getTagStringCopyright() {
        return tagStringCopyright;
    }

    public String[] listTag_string_copyright(){
        return deserialize(tagStringCopyright);
    }
    
    public void setTagStringCopyright(String tagStringCopyright) {
        this.tagStringCopyright = tagStringCopyright;
    }

    public String getTagStringGeneral() {
        return tagStringGeneral;
    }
    
    public String[] listTag_string_general(){
        return deserialize(tagStringGeneral);
    }

    public void setTagStringGeneral(String tagStringGeneral) {
        this.tagStringGeneral = tagStringGeneral;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isActive(){
        return !banned && !deleted;
    }
    
    /**
     * 
     * @return "DanbooruPost" e ID, MD5, URL e tag String geral.
     */
    @Override
    public String toString(){
        return "DanbooruPost " + id + " - " + md5 + "\n\t" + fileUrl + "\n\t" + tagString;
    }
    

    private static String[] deserialize(String tagString) {
        if (tagString.isEmpty()){
            return new String[0];
        } else {
            return tagString.split(" ");
        }
    }

    @Override
    public String getSearchableType() {
        return TYPE_POST;
    }

    
}

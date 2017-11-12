/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source.danbooru;

import com.adi.model.source.DanbooruModel;
import com.adi.model.source.Post;
import com.google.gson.annotations.SerializedName;

/**
 * Representa um post do Danbooru2, conforme retornado pela API.
 * <br>Inclui ID, data de criação, MD5, classif. etária, dimensões e URL da imagem, tag strings e flags, dentre outros dados.
 * @author Caique
 */
public class DanbooruPost extends Post implements DanbooruModel {
    
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
    
    @SerializedName(value = "is_pending", alternate = "is-pending")
    private boolean pending;
    
    @SerializedName(value = "is_flagged", alternate = "is-flagged")
    private boolean flagged;
    
    @SerializedName(value = "is_deleted", alternate = "is-deleted")
    private boolean deleted;
    
    @SerializedName(value = "tag_count", alternate = "tag-count")
    private int tagCount;
    
    @SerializedName(value = "updated_at", alternate = "updated-at")
    private String updatedAt;
    
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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

    public String[] listArtists(){
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
        
        return tagStringGeneral.split(" ");
    }

    public void setTagStringGeneral(String tagStringGeneral) {
        this.tagStringGeneral = tagStringGeneral;
    }

    public boolean isActive(){
        return !banned && !deleted;
    }
    
    public boolean isCensored(){
        return tagStringGeneral.contains("loli") || tagStringGeneral.contains("shota");
    }
    
    /**
     * 
     * @return "DanbooruPost" e ID, MD5, URL e tag String geral.
     */
    @Override
    public String toString(){
        return "DanbooruPost " + id + " - " + md5 + "\n\t" + fileUrl + "\n\t" + tagString;
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
}

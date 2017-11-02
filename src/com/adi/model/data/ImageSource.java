/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.data;

import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.model.source.danbooru.DanbooruPost;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Classe que representa a origem de uma imagem {@link Image}. Uma imagem pode ter de 0 a N origens, mas toda origem vai ter uma imagem.
 * <br>A origem guarda dados do Post (em imageboards danbooru-like) ou da origem, como ID, data de envio, tags, status (deletada, offline, ativa) e endereço.
 * @author Caique
 */
public class ImageSource {
    private Integer id;
    private String sourceName;
    private String sourceId;
    private String postUrl;
    private String fileUrl;
    private Date uploadDate;
    private String md5;
    private long fileSize;
    private String tagString;
    private boolean sourceOffline;
    private boolean imageDeleted;
    private boolean imageCensored;
    private boolean imageBanned;
    private String rating;
    private List<Tag> tagList;
    private List<Image> imageList;
    private Image image;

    /**
     * Cria uma instância com todos os dados inicializados como zero ou nulo.
     * <br>Dados que podem ser nulos são inicializados como null, outros inicializados como vazios ou zero (exceto flags, que são TRUE).
     */
    public ImageSource() {
        sourceName = "";
        sourceId = "";
        postUrl = null;
        fileUrl = null;
        uploadDate = null;
        md5 = null;
        fileSize = 0;
        tagString = null;
        sourceOffline = true;
        imageDeleted = true;
        imageCensored = true;
        imageBanned = true;
        rating = null;
    }

    /**
     * Cria uma instância a partir de determinado DanbooruPost e Origem.
     * Todos os dados são inicializados a partir desses dois.
     * @param post DanbooruPost
     * @param source Source (conforme {@link Configuration#getSources()})
     */
    public ImageSource(DanbooruPost post, Source source){
        sourceName = source.getName();
        sourceId = String.valueOf(post.getId());
        postUrl = source.getHtmlUrl() + post.getId();
        fileUrl = post.getFileUrl();
        String locale = source.getDateLocale().isEmpty() ? "en" : source.getDateLocale();
        try {
            uploadDate = new SimpleDateFormat(source.getDateFormat(), Locale.forLanguageTag(locale)).parse(post.getCreatedAt());
        } catch (ParseException ex) {
            uploadDate = null;
        }
        md5 = post.getMd5();
        fileSize = post.getFileSize();
        tagString = post.getTagString();
        sourceOffline = !source.isActive();
        imageDeleted = post.isDeleted();
        imageCensored = post.isCensored();
        imageBanned = post.isBanned();
        rating = post.getRating();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public boolean getSourceOffline() {
        return sourceOffline;
    }

    public void setSourceOffline(boolean sourceOffline) {
        this.sourceOffline = sourceOffline;
    }

    public boolean getImageDeleted() {
        return imageDeleted;
    }

    public void setImageDeleted(boolean imageDeleted) {
        this.imageDeleted = imageDeleted;
    }

    public boolean getImageCensored() {
        return imageCensored;
    }

    public void setImageCensored(boolean imageCensored) {
        this.imageCensored = imageCensored;
    }

    public boolean getImageBanned() {
        return imageBanned;
    }

    public void setImageBanned(boolean imageBanned) {
        this.imageBanned = imageBanned;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        } else {
            if (!(obj instanceof ImageSource)){
                return false;
            } else {
                ImageSource ins = (ImageSource)obj;
                return (this.sourceName.equals(ins.sourceName) && this.sourceId.equals(ins.sourceId));
            }
        }
    }

    @Override
    public String toString() {
        return sourceName + " " + sourceId + " - " + md5;
    }
}

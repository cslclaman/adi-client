/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Caique
 */
@Entity
@Table(name = "image", catalog = "adi6_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")})
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "md5")
    private String md5;
    
    @Basic(optional = false)
    @Column(name = "file_path")
    private String filePath;
    
    @Basic(optional = false)
    @Column(name = "tag_string")
    private String tagString;
    
    @Basic(optional = false)
    @Column(name = "rating")
    private String rating;
    
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    
    @Basic(optional = false)
    @Column(name = "file_size")
    private long fileSize;
    
    @Column(name = "file_source")
    private String fileSource;
    
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    
    @Column(name = "source_name")
    private String sourceName;
    
    @ManyToMany(mappedBy = "imageList")
    private List<AdiTag> adiTagList;
    
    @JoinColumn(name = "primary_source", referencedColumnName = "id")
    @ManyToOne
    private ImageSource primarySource;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image")
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

    @XmlTransient
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

    @XmlTransient
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

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.data.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Caique
 */
@Entity
@Table(name = "adi_tag", catalog = "adi6_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdiTag.findAll", query = "SELECT a FROM AdiTag a")})
public class AdiTag implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String TIPO_ARTISTA = "a";
    public static final String TIPO_PERSONAGEM = "p";
    public static final String TIPO_ITEM = "i";
    public static final String TIPO_SERIE = "c";
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    
    @Basic(optional = false)
    @Column(name = "tag")
    private String tag;
    
    @JoinTable(name = "image_tags", joinColumns = {
        @JoinColumn(name = "adi_tag", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "image", referencedColumnName = "id")})
    @ManyToMany
    private List<Image> imageList;
    
    @OneToMany(mappedBy = "adiTag")
    private List<Tag> tagList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adiTag")
    private List<Info> infoList;

    public AdiTag() {
    }

    public AdiTag(String type, String tag) {
        this.type = type;
        this.tag = tag;
    }

    public AdiTag(String adiTag) {
        int ap = adiTag.indexOf("(");
        int fp = adiTag.indexOf(")");
        
        if (ap > -1 && fp == ap + 2){
            this.type = adiTag.substring(ap + 1, fp);
            this.tag = adiTag.substring(ap + 1, fp);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @XmlTransient
    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
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

    public String getPrettyType() {
        switch (type){
            case "c":
                return "Série";
            case "p":
                return "Personagem";
            case "i":
                return "Item";
            case "a":
                return "Artista";
            default:
                return "";
        }
    }

}

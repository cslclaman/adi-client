/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance.model;

/**
 * Classe que representa um endereço de origem e sua API.
 * <br>Inclui URL, métodos suportados (pesquisa de posts, tags, etc), tipos suportados (XML/JSON) e demais endereços.
 * @author Caique
 */
public class Source {
    private String name;
    private String id;
    private String type;
    private Boolean active;
    private Boolean apiActive;
    private String htmlUrl;
    private String dataUrl;
    private String fileUrl;
    private String apiUrl;
    private String[] apiTypeSuport;
    private String[] apiQuerySuport;
    private String postsBaseJson;
    private String postsBaseXML;
    private String postsTagsQuery;
    private String postsIdQuery;
    private String postsMd5Query;
    private String dateFormat;
    private String dateLocale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getApiActive() {
        return apiActive;
    }

    public void setApiActive(Boolean apiActive) {
        this.apiActive = apiActive;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String[] getApiTypeSuport() {
        return apiTypeSuport;
    }

    public void setApiTypeSuport(String[] apiTypeSuport) {
        this.apiTypeSuport = apiTypeSuport;
    }

    public String[] getApiQuerySuport() {
        return apiQuerySuport;
    }

    public void setApiQuerySuport(String[] apiQuerySuport) {
        this.apiQuerySuport = apiQuerySuport;
    }

    public String getPostsBaseJson() {
        return postsBaseJson;
    }

    public void setPostsBaseJson(String postsBaseJson) {
        this.postsBaseJson = postsBaseJson;
    }

    public String getPostsBaseXML() {
        return postsBaseXML;
    }

    public void setPostsBaseXML(String postsBaseXML) {
        this.postsBaseXML = postsBaseXML;
    }

    public String getPostsBase(){
        if (supportJson()){
            return postsBaseJson;
        } else {
            if (supportXML()){
                return postsBaseXML;
            } else {
                return "";
            }
        }
    }
    
    public String getPostsTagsQuery() {
        return postsTagsQuery;
    }

    public void setPostsTagsQuery(String postsTagsQuery) {
        this.postsTagsQuery = postsTagsQuery;
    }

    public String getPostsIdQuery() {
        return postsIdQuery;
    }

    public void setPostsIdQuery(String postsIdQuery) {
        this.postsIdQuery = postsIdQuery;
    }

    public String getPostsMd5Query() {
        return postsMd5Query;
    }

    public void setPostsMd5Query(String postsMd5Query) {
        this.postsMd5Query = postsMd5Query;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateLocale() {
        return dateLocale;
    }

    public void setDateLocale(String dateLocale) {
        this.dateLocale = dateLocale;
    }
    
    public boolean supportJson(){
        for (String s : apiTypeSuport){
            if (s.equals("json")){
                return true;
            }
        }
        return false;
    }
    
    public boolean supportXML(){
        for (String s : apiTypeSuport){
            if (s.equals("xml")){
                return true;
            }
        }
        return false;
    }
    
    public boolean searchesPosts(){
        for (String s : apiQuerySuport){
            if (s.equals("posts")){
                return true;
            }
        }
        return false;
    }
    
    public boolean searchesTags(){
        for (String s : apiQuerySuport){
            if (s.equals("tags")){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna a source padrão (Danbooru API via http://hijiribe.donmai.us), com suporte a posts e resultados em JSON ou XML.
     * @return 
     */
    public static Source defaultSource(){
        Source s = new Source();
        
        s.name = "Danbooru";
        s.id = "d";
        s.type = "Danbooru2";
        s.active = true;
        s.apiActive = true;
        s.htmlUrl = "http://danbooru.donmai.us/posts/";
        s.dataUrl = "http://hijiribe.donmai.us";
        s.fileUrl = "http://hijiribe.donmai.us/cached/data/";
        s.apiUrl = "http://hijiribe.donmai.us/";
        s.apiTypeSuport = new String[]{"json", "xml"};
        s.apiQuerySuport = new String[]{"posts"};
        s.postsBaseJson = "posts.json?";
        s.postsBaseXML = "posts.xml?";
        s.postsTagsQuery = "tags=";
        s.postsIdQuery = "tags=id:";
        s.postsMd5Query = "tags=md5:";
        s.dateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX";
        s.dateLocale = "";
        
        return s;
    }

    /**
     * Retorna o nome da source.
     * @return Nome (ex.: Danbooru)
     */
    @Override
    public String toString() {
        return name;
    }
    
    
}

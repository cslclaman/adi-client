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

    /**
     * Nome da Source
     * @return "Danbooru"
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ID (sigla) da source, idêntica à usada na tag (s).
     * @return "d"
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Nome do tipo da source (sistema).
     * @return "Danbooru2"
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retorna se a origem está ativa (site online, liberado)
     * @return true
     */
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Retorna se o site possui API e se ela está ativa/aceitando pedidos
     * @return true
     */
    public Boolean isApiActive() {
        return apiActive;
    }

    public void setApiActive(Boolean apiActive) {
        this.apiActive = apiActive;
    }

    /**
     * URL para visualização do site/URL base
     * @return "http://danbooru.donmai.us/posts/"
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * URL base de obtenção de arquivos (ex: dataUrl + post.fileUrl)
     * @return "http://hijiribe.donmai.us"
     */
    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    /**
     * URL direta de arquivos (ex: fileUrl + archive.name)
     * @return "http://hijiribe.donmai.us/cached/data/"
     */
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * URL para requisições da API.
     * @return "http://hijiribe.donmai.us/"
     */
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Retorna os tipos de arquivo que a API retorna (XML/JSON)
     * @return Lista com tipos de arquivos aceitos
     */
    public String[] getApiTypeSuport() {
        return apiTypeSuport;
    }

    public void setApiTypeSuport(String[] apiTypeSuport) {
        this.apiTypeSuport = apiTypeSuport;
    }

    /**
     * Retorna os tipos de pesquisas que a API aceita
     * @return Lista com tipos de pesquisas possíveis
     */
    public String[] getApiQuerySuport() {
        return apiQuerySuport;
    }

    public void setApiQuerySuport(String[] apiQuerySuport) {
        this.apiQuerySuport = apiQuerySuport;
    }

    /**
     * Retorna string relativa ao formato JSON, se a API suportar.
     * @return "posts.json?"
     */
    public String getPostsBaseJson() {
        return postsBaseJson;
    }

    public void setPostsBaseJson(String postsBaseJson) {
        this.postsBaseJson = postsBaseJson;
    }

    /**
     * Retorna string relativa ao formato XML, se a API suportar.
     * @return "posts.xml?"
     */
    public String getPostsBaseXML() {
        return postsBaseXML;
    }

    public void setPostsBaseXML(String postsBaseXML) {
        this.postsBaseXML = postsBaseXML;
    }

    /**
     * Retorna String do URL básico de API, dando preferência ao JSON. 
     * @return {@link #getPostsBaseJson()}, {@link #getPostsBaseXML()} ou String vazia caso não haja suporte
     */
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
    
    /**
     * Retorna String com comando básico para pesquisar Posts por tags
     * @return "tags="
     */
    public String getPostsTagsQuery() {
        return postsTagsQuery;
    }

    public void setPostsTagsQuery(String postsTagsQuery) {
        this.postsTagsQuery = postsTagsQuery;
    }

    /**
     * Retorna String com comando básico para pesquisar Posts por ID
     * @return "tags=id:"
     */
    public String getPostsIdQuery() {
        return postsIdQuery;
    }

    public void setPostsIdQuery(String postsIdQuery) {
        this.postsIdQuery = postsIdQuery;
    }

    /**
     * Retorna String com comando básico para pesquisar Posts por MD5
     * @return "tags=md5:"
     */
    public String getPostsMd5Query() {
        return postsMd5Query;
    }

    public void setPostsMd5Query(String postsMd5Query) {
        this.postsMd5Query = postsMd5Query;
    }

    /**
     * Retorna formato da string de data usado pela API.
     * <br>Se o parser não formatar automaticamente, use essa string para formatar.
     * @return "yyyy-MM-dd'T'HH:mm:ssXXX"
     */
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Retorna local de data usado pela API, a ser usado ao formatar data.
     * <br>Padrão: EN.
     * @return "en"
     */
    public String getDateLocale() {
        return dateLocale;
    }

    public void setDateLocale(String dateLocale) {
        this.dateLocale = dateLocale;
    }
    
    /**
     * Retorna TRUE se a API recebe/envia JSON como formato de dados.
     * @return TRUE se JSON, senão FALSE
     */
    public boolean supportJson(){
        for (String s : apiTypeSuport){
            if (s.equals("json")){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna TRUE se a API recebe/envia XML como formato de dados.
     * @return TRUE se XML, senão FALSE
     */
    public boolean supportXML(){
        for (String s : apiTypeSuport){
            if (s.equals("xml")){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna TRUE se a API pesquisa posts (ou coisa parecida).
     * @return TRUE se pesquisa Posts, senão FALSE
     */
    public boolean searchesPosts(){
        for (String s : apiQuerySuport){
            if (s.equals("posts")){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna TRUE se a API pesquisa tags (ou coisa parecida).
     * @return TRUE se pesquisa Tags, senão FALSE
     */
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

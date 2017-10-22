/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.search;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.exception.UnsupportedMethodException;
import com.adi.instance.model.Source;
import com.adi.model.source.Searchable;
import com.adi.model.source.danbooru.DanbooruPost;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Caique
 */
public class Search {
    private static final int FORMAT_NULL =  0;
    public static final int FORMAT_JSON =   1;
    public static final int FORMAT_XML =    2;
    
    private String baseUrl;
    private String formatUrl;
    private String queryUrl;
    private String searchUrl;
    
    private Searchable[] results;
    private final Source source;
    private SearchTypeInstance typeInstance;
    private SearchTypeParameter typeParam;
    private int format;

    public Search(Source source) {
        this.typeInstance = SearchTypeInstance.POSTS;
        this.typeParam = SearchTypeParameter.BY_RAW;
        this.source = source;
        if (source.supportJson()) { 
            this.formatUrl = source.getPostsBaseJson();
            format = FORMAT_JSON;
        } else {
            if (source.supportXML()){
                this.formatUrl = source.getPostsBaseXML();
                format = FORMAT_XML;
            } else {
                this.formatUrl = source.getPostsBase();
                format = FORMAT_NULL;
            }
        }
        this.baseUrl = source.getApiUrl();
        this.queryUrl = "";
    }
    
    public Search() {
        this.typeInstance = SearchTypeInstance.POSTS;
        this.typeParam = SearchTypeParameter.BY_RAW;
        source = Source.defaultSource();
        if (source.supportJson()) { 
            this.formatUrl = source.getPostsBaseJson();
            format = FORMAT_JSON;
        } else {
            if (source.supportXML()){
                this.formatUrl = source.getPostsBaseXML();
                format = FORMAT_XML;
            } else {
                this.formatUrl = source.getPostsBase();
                format = FORMAT_NULL;
            }
        }
        this.baseUrl = source.getApiUrl();
        this.queryUrl = "";
    }
    
    public void setFormat(int format) {
        this.format = format;
        if (format == FORMAT_JSON){
            if (source.supportJson()) { 
                this.formatUrl = source.getPostsBaseJson();
            } else {
                throw new UnsupportedMethodException("API não possui suporte para formato JSON");
            }
        } else {
            if (format == FORMAT_XML){
                if (source.supportXML()){
                    this.formatUrl = source.getPostsBaseXML();
                } else {
                    throw new UnsupportedMethodException("API não possui suporte para formato XML");
                }
            } else {
                this.formatUrl = source.getPostsBase();
            }
        }
    }
    
    public void setTypeSearch(SearchTypeInstance typeInstance, SearchTypeParameter typeParam) {
        this.typeInstance = typeInstance;
        this.typeParam = typeParam;
        
        switch (typeInstance){
            case POSTS:
                if (!source.searchesPosts()){
                    throw new UnsupportedMethodException ("Busca por posts não suportada por esse endereço");
                } else {
                    switch (typeParam){
                        case BY_ID:
                            queryUrl = source.getPostsIdQuery();
                            break;
                        case BY_MD5:
                            queryUrl = source.getPostsMd5Query();
                            break;
                        case BY_TAGS:
                            queryUrl= source.getPostsTagsQuery();
                            break;
                        default:
                            queryUrl = "";
                            break;
                    }
                }
                break;
            default:
                queryUrl = "";
                break;
        }
    }

    public SearchTypeParameter getSearchTypeParameter(){
        return typeParam;
    }
    
    public SearchTypeInstance getSearchTypeInstance(){
        return typeInstance;
    }
    
    public void setQuery(String query) {
        searchUrl = query;
    }
    
    public boolean hasResults(){
        return results != null && results.length > 0;
    }
    
    public Searchable result(){
        return result(0);
    }
    
    public Searchable result(int index){
        if (index < 0) {
            index = 0;
        }
        if (index >= results.length){
            index = results.length - 1;
        }
        return results[index];
    }
    
    public Searchable[] resultList(){
        return results;
    }
    
    public void search() throws IOException, UnspecifiedParameterException {
        URL url = new URL(baseUrl + formatUrl + queryUrl + searchUrl);
        switch (format){
            case FORMAT_JSON:
                searchJson(url);
                break;
            case FORMAT_XML:
                searchXml(url);
                break;
            default:
                throw new UnspecifiedParameterException("Formato de arquivo da ADI não especificado");
        }
    }
    
    /**
     * Será alterado em uma atualização futura para usar método {@link Searchable#getInstance }
     * @param search
     * @throws IOException 
     */
    private void searchJson(URL search) throws IOException{
        Gson parser = new Gson();
        HttpURLConnection cn = (HttpURLConnection)search.openConnection();
        InputStreamReader r = new InputStreamReader(cn.getInputStream());
        
        //
        if (getSearchTypeInstance() == SearchTypeInstance.POSTS){
            results = parser.fromJson(r, DanbooruPost[].class);
        }
        
        r.close();
    }
    
    /**
     * Implementar usando SAX Parser e {@link Searchable#getInstance }
     * @param search
     */
    private void searchXml(URL search) {
        throw new UnsupportedOperationException("XML não implementado - URL " + search.toString());
    }
}

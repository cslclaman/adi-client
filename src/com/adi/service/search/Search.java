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
    
    public static final int SEARCH_RAW =      0;
    public static final int SEARCH_BY_ID =    1;
    public static final int SEARCH_BY_TAGS =  2;
    public static final int SEARCH_BY_MD5 =   3;
    
    public static final int SEARCH_POSTS =  10;
    public static final int SEARCH_TAGS =   20;
    
    private static final int FORMAT_NULL =  0;
    public static final int FORMAT_JSON =   1;
    public static final int FORMAT_XML =    2;
    
    private String baseUrl;
    private String formatUrl;
    private String queryUrl;
    private String searchUrl;
    
    private Searchable[] results;
    private final Source source;
    private int typeSearch;
    private int format;

    public Search(Source source) {
        this.typeSearch = SEARCH_POSTS + SEARCH_RAW;
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
        this.typeSearch = SEARCH_POSTS + SEARCH_RAW;
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
    
    public void setTypeSearch(int type) {
        this.typeSearch = type;
        
        switch ((type / 10) * 10){
            case SEARCH_POSTS:
                if (!source.searchesPosts()){
                    throw new UnsupportedMethodException ("Busca por posts não suportada por esse endereço");
                } else {
                    switch (type % 10){
                        case SEARCH_BY_ID:
                            queryUrl = source.getPostsIdQuery();
                            break;
                        case SEARCH_BY_MD5:
                            queryUrl = source.getPostsMd5Query();
                            break;
                        case SEARCH_BY_TAGS:
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

    public int getSearchTypeParameter(){
        return typeSearch % 10;
    }
    
    public int getSearchTypeInstance(){
        return typeSearch / 10 * 10;
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
                throw new UnsupportedOperationException("XML não implementado");
            default:
                throw new UnspecifiedParameterException("Formato de arquivo da ADI não especificado");
        }
    }
    
    private void searchJson(URL search) throws IOException{
        Gson parser = new Gson();
        HttpURLConnection cn = (HttpURLConnection)search.openConnection();
        InputStreamReader r = new InputStreamReader(cn.getInputStream());
        
        if (getSearchTypeInstance() == SEARCH_POSTS){
            results = parser.fromJson(r, DanbooruPost[].class);
        }
        
        r.close();
    }
}

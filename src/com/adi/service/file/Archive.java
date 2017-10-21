/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.file;

import com.adi.service.function.Hash;
import com.adi.service.tags.AdiTagsModel;
import com.adi.service.tags.AdiTagsParser;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 *
 * @author Caique
 */
public class Archive extends File {
    public static final String[] SUPPORTED_FILE_EXT = new String[]{
        "JPG", "JPEG",
        "PNG",
        "GIF",
        "SWF",
        "WEBM",
        "MP4"
    };
    
    private static final String SEARCH_TYPE_MD5 = "md5";
    private static final String SEARCH_TYPE_ID = "id";
    private static final String SEARCH_TYPE_NULL = "";
    
    private String md5 = "";
    private String extension;
    private String searchQuery;
    private String searchTypeName;
    
    public Archive(String pathname) throws IOException{
        super(pathname);
        init();
    }

    public Archive(String parent, String child) throws IOException{
        super(parent, child);
        init();
    }

    public Archive(File parent, String child) throws IOException{
        super(parent, child);
        init();
    }

    public Archive(URI uri) throws IOException {
        super(uri);
        init();
    }
    
    private void init() throws IOException {
        searchQuery = "";
        searchTypeName = SEARCH_TYPE_NULL;
        
        if (!exists()){
            throw new IOException("File doesn't exist - " + getPath());
        }
        
        if (!isDirectory()){
            if (length() <= 0){
                throw new IOException("Empty file - " + getPath());
            }

            extension = getName().substring(getName().lastIndexOf("."));

            boolean validExt = false;
            for (String ext : SUPPORTED_FILE_EXT){
                if (extension.equalsIgnoreCase(ext)){
                    validExt = true;
                    break;
                }
            }

            if (!validExt){
                throw new IOException("File has invalid extension for ADI - " + getPath());
            }
        }
    }
    
    public String getMd5(){
        if (md5.isEmpty()){
            md5 = Hash.md5(this);
        }
        return md5;
    }
    
    public String getTypeName(){
        if (searchTypeName.isEmpty()){
            makeSearch();
        }
        return searchTypeName;
    }
    
    public String getQuery(){
        if (searchQuery.isEmpty()){
            makeSearch();
        }
        return searchQuery;
    }
    
    private void makeSearch(){
        String name = getName().substring(0, getName().lastIndexOf(".")).toLowerCase();
        
        if (!name.contains(getMd5())){
            if (name.startsWith("(s") || name.startsWith("(ADI)")){
                AdiTagsModel model = AdiTagsParser.toAdiTags(name);
                searchTypeName = SEARCH_TYPE_ID;
                searchQuery = model.getSourcePost();
            } else {
                int seq = 0;
                String hash = "";

                for (char c : name.toCharArray()){
                    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')){
                        hash = hash + c;
                        seq ++;
                    } else {
                        hash = "";
                        seq = 0;
                    }
                    if (seq == 32){
                        searchTypeName = SEARCH_TYPE_MD5;
                        searchQuery = hash;
                    }
                }
            }
        } else {
            searchTypeName = SEARCH_TYPE_MD5;
            searchQuery = md5;
        }
    }
}

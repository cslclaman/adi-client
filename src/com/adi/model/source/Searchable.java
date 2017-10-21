/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source;

import com.adi.model.source.danbooru.DanbooruPost;

/**
 *
 * @author Caique
 */
public interface Searchable {
    public static final String TYPE_POST = "post";
    public static final String TYPE_TAG = "tag";
    
    public boolean isSourceSupported(String source);
    public String getSearchableType();
    
    public static Searchable getInstance (String source, String type){
        Searchable[] sl = new Searchable[]{
            new DanbooruPost()
        };
        for (Searchable s : sl){
            if (s.isSourceSupported(source) && s.getSearchableType().equalsIgnoreCase(type)){
                return s;
            }
        }
        return null;
    }
}

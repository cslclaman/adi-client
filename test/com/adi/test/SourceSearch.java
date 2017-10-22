/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.model.Source;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.search.Search;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class SourceSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Search search = new Search(Source.defaultSource());
        //search.setSearchType(Search.SEARCH_POSTS + Search.SEARCH_BY_ID);
        search.setQuery("tags=id:1500000");
        //search.setFormat(Search.FORMAT_XML);
        try {
            search.search();
        } catch (IOException | UnspecifiedParameterException ex) {
            System.err.println(ex.toString());
        }
        if (search.hasResults()){
            for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                System.out.println(p);
            }
        }
    }
    
}

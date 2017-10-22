/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.Archive;
import com.adi.service.function.Archiver;
import com.adi.service.search.Search;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class SearchArchives {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        /*archiver.removeFolders(new String[]{
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Sankaku\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Gelbooru\\",
        }, true);*/
        for (Archive a : archiver.getArchiveList()){
            Search search = new Search(conf.getDefaultSource());
            search.setTypeSearch(Search.SEARCH_POSTS + a.getQueryType());
            search.setQuery(a.getQueryText());
            try {
                search.search();
            } catch (IOException | UnspecifiedParameterException ex) {
                System.err.println(ex.toString());
            }
            System.out.println("Result(s) for file " + a.getName());
            if (search.hasResults()){
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                    System.out.println("\tFound: " + p.getId() + " - " + p.getMd5());
                }
            } else {
                System.out.println("\tNone found");
            }
        }
    }
}

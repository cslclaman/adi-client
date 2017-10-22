/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.data.entity.ImageSource;
import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.Archiver;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class ImageSourceCreate {

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        Source source = conf.getDefaultSource();
        for (Archive a : archiver.getArchiveList()){
            Search search = new Search(source);
            search.setSearchType(SearchTypeInstance.POSTS, a.getQueryTypeParameter());
            search.setQuery(a.getQueryText());
            try {
                search.search();
            } catch (IOException | UnspecifiedParameterException ex) {
                System.err.println(ex.toString());
            }
            
            if (search.hasResults()){
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                    ImageSource imageSrc = new ImageSource(p, source);
                    System.out.println("Found " + imageSrc.toString());
                }
            } else {
                System.err.println("No result for file " + a.getName());
            }
        }
    }
    
}

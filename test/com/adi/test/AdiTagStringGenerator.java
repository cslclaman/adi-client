/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.model.data.Tag;
import com.adi.model.source.Post;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.data.TagController;
import com.adi.service.file.Archiver;
import com.adi.service.file.Downloader;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import com.adi.service.tags.AdiTagsModel;
import com.adi.test.utility.ConsoleProgress;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class AdiTagStringGenerator {

    public static void main(String[] args) {
        Configuration conf = Configuration.defaults();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        Source source = conf.getDefaultSource();
        Search search = new Search(source);
        
        for (Archive a : archiver.getArchiveList()){
            
            search.setSearchType(SearchTypeInstance.POSTS, a.getQueryTypeParameter());
            search.setQuery(a.getQueryText());
            try {
                search.search();
            } catch (IOException | UnspecifiedParameterException ex) {
                System.err.println(ex.toString());
            }
            System.out.println(a.getPath());
            if (search.hasResults()){
                TagController tc = new TagController("http://localhost:5000/");
                
                for (Post p : (Post[])search.resultList()){
                    AdiTagsModel modelAdi = new AdiTagsModel();
                    modelAdi.setSource(source.getId(), p.getId());
                    modelAdi.setRating(p.getAdiRating());
                    if (!source.isActive()){
                        modelAdi.addError("io");
                    }
                    if (p.isBanned()){
                        modelAdi.addError("a");
                    }
                    if (p.isCensored()){
                        modelAdi.addError("g");
                    }
                    if (p.isDeleted()){
                        modelAdi.addError("n");
                    }
                    
                    if (Downloader.needDownload(a, p)){
                        System.out.println("GO DOWNLOAD NOW!");
                        try {
                            Archive b = Downloader.download(source.getDataUrl() + p.getFileUrl(), new ConsoleProgress());
                            System.out.println(b.getPath());
                        } catch (IOException ex){
                            System.err.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("It's fine.");
                    }
                    
                    for (String t : p.getTagStringList()){
                        Tag tag = tc.findOrCreate(t);
                        if (tag != null){
                            if (tag.getAdiTag() != null){
                                modelAdi.addAdiTag(tag.getAdiTag());
                            }
                        }
                    }
                    System.out.println(modelAdi.getTagString());
                    System.out.println(modelAdi.getFolder() + modelAdi.getTagString(150));
                    System.out.println(modelAdi.getRelativePath());
                }
            }
        }
    }

}

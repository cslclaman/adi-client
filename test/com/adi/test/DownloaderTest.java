/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.Archiver;
import com.adi.service.file.Downloader;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import com.adi.test.utility.ConsoleProgress;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class DownloaderTest {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        Source source = conf.getDefaultSource();
        Search search = new Search(source);
        archiver.removeFolders(new String[]{
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\Twitter\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Sankaku\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Gelbooru\\",
        }, true);
        archiver.removeArchives(new String[]{
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\3512efbfff477bb898d31cabfff9979a.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\eb24b503f573591af7ce99b1962796dd.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\03bb57c1f17f9f3d1e77fadaee3cf461.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\0a44644e99dcb725082c3856007dff93.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\9ad05f8770d5da08cf1ee587ce899029.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\ee7acaa6c6c75d8972b66bd689dcc5c7.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\011d7b5a1b6678c00f338f803525334d.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\a0c191fc270a8ec0ef72c540663c6472.jpg",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Danbooru\\d62b0db1eebce2f532fe830f135637ca.jpg",
        });
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
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                    if (Downloader.needDownload(a, p)){
                        System.out.println("GO DOWNLOAD NOW!");
                        Archive b = Downloader.download(source.getDataUrl() + p.getFileUrl(), new ConsoleProgress());
                        System.out.println(b.getPath());
                    } else {
                        System.out.println("It's fine.");
                    }
                }
            } 
        }
    }
    
}

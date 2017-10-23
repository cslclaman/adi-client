/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.Archiver;
import com.adi.service.file.Downloader;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class FileDownloadVerification {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        archiver.removeFolders(new String[]{
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\Twitter\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Sankaku\\",
            "C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Gelbooru\\",
        }, true);
        for (Archive a : archiver.getArchiveList(5)){
            Search search = new Search(conf.getDefaultSource());
            search.setSearchType(SearchTypeInstance.POSTS, a.getQueryTypeParameter());
            search.setQuery(a.getQueryText());
            try {
                search.search();
            } catch (IOException | UnspecifiedParameterException ex) {
                System.err.println(ex.toString());
            }
            System.out.print("Result(s) for file " + a.getName() + ": ");
            if (search.hasResults()){
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                    System.out.println("Found: " + p.getId());
                    System.out.println(Downloader.needDownload(a, p) ? 
                            "NEED DOWNLOAD!!! Post MD5 = " + (p.getMd5() == null ? "" : p.getMd5() + " - " + a.getMd5())
                            : "perfect file");
                }
            } else {
                System.out.println("None found");
            }
        }
    }
}

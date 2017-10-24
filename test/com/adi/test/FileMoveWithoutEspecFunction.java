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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;

public class FileMoveWithoutEspecFunction {

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver();
        Source source = conf.getDefaultSource();
        Search search = new Search(source);
        BufferedWriter log = new BufferedWriter(new FileWriter(new File(Configuration.LOCAL_PATH + "excluded.txt"),true));
        
        archiver.addInputList(new String[]{
            "C:\\Users\\Caique\\adi\\default_input\\",
        }, true);
        
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
                String m = "", t = "";
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
                    m = p.getId() + " - " + p.getRating() + " - " + p.getMd5();
                    t = "(sd)" + p.getId() + " (r)" + p.getRating() + "(a)" + p.getTagStringArtist() + "(c)XXXX " + "(np)" + p.getTagCountCharacter();
                    if (Downloader.needDownload(a, p)){
                        Archive b = Downloader.download(source.getDataUrl() + p.getFileUrl(), new ConsoleProgress());
                        log.write(a.getPath() + "\t" + b.getPath() + "\n");
                        log.flush();
                        Files.delete(a.toPath());
                        a = b;
                    } else {
                        System.out.println("It's fine.");
                    }
                }
                String nn = JOptionPane.showInputDialog("Novo nome do arquivo:\n" + m, t);
                File a2 = new File("C:\\Users\\Caique\\adi\\default_output", nn + "." + a.getExtension());
                Files.move(a.toPath(), a2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    
}

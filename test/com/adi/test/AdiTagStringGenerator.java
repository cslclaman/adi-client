/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.exception.UnspecifiedParameterException;
import com.adi.instance.Configuration;
import com.adi.instance.model.Source;
import com.adi.model.data.Image;
import com.adi.model.data.Tag;
import com.adi.model.source.Post;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.data.ImageController;
import com.adi.service.data.TagController;
import com.adi.service.file.Archiver;
import com.adi.service.file.Downloader;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import com.adi.service.tags.AdiTagsModel;
import com.adi.test.utility.ConsoleProgress;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Caique
 */
public class AdiTagStringGenerator {

    public static final String SERVER_URL = "http://localhost:5000/";
    
    public static void main(String[] args) {
        Configuration conf = Configuration.defaults();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        Source source = conf.getDefaultSource();
        Search search = new Search(source);
        BufferedWriter log;
        try {
            log = new BufferedWriter(new FileWriter(new File(Configuration.LOCAL_PATH + "excluded.txt"),true));
        } catch (IOException ex){
            System.err.println(ex.toString());
            log = null;
        }
        
        TagController tc = new TagController(SERVER_URL);
        ImageController ic = new ImageController(SERVER_URL);
        
        for (Archive a : archiver.getArchiveList()){
            String origPath = a.getName();
            search.setSearchType(SearchTypeInstance.POSTS, a.getQueryTypeParameter());
            search.setQuery(a.getQueryText());
            try {
                search.search();
            } catch (IOException | UnspecifiedParameterException ex) {
                System.err.println(ex.toString());
            }
            System.out.println(a.getPath());
            if (search.hasResults()){
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
                            if (log != null){
                                log.write(a.getPath() + "\t" + b.getPath() + "\n");
                                log.flush();
                            }
                            Files.delete(a.toPath());
                            a = b;
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
                    //Obs.: 220 is the medium limit for file names, but is better to change this to a constant thing.
                    String nameFolder = modelAdi.getFolder(conf.getPreferences().getOutputFolders()[0]);
                    int nameSize = 220 - nameFolder.length();
                    
                    System.out.println(modelAdi.getTagString());
                    System.out.println(modelAdi.getFolder() + modelAdi.getTagString(nameSize));
                    System.out.println(modelAdi.getRelativePath() + modelAdi.getTagString(nameSize) + "." + a.getExtension());
                    System.out.println(nameFolder);
                    
                    Image image = null;
                    
                    try {
                        File pn = new File(nameFolder);
                        if (!pn.exists()){
                            pn.mkdirs();
                        }
                        File n = new File(pn, modelAdi.getTagString(nameSize) + "." + a.getExtension());
                        Files.move(a.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Archive na = new Archive(n.getPath());
                        image = new Image(p, na, modelAdi);
                        image.setFileSource(origPath);
                    } catch (IOException ex){
                        System.err.println(ex.toString());
                    }
                    
                    if (image != null){
                        ic.create(image);
                    }
                    
                }
            }
        }
    }

}

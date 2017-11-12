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
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.Archiver;
import com.adi.service.file.Downloader;
import com.adi.service.file.file.Archive;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeInstance;
import com.adi.test.utility.ConsoleProgress;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Caique
 */
public class ApiRestTagCreator {

    /**
     * @param args the command line arguments
     */
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
                for (DanbooruPost p : (DanbooruPost[])search.resultList()){
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
                    for (String t : p.listTag_string()){
                        //localhost:5000/tag/find?name=monogatari_(series)&adi_tag=show
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create();
                        try {
                            URL url = new URL("http://localhost:5000/tag/find?adi_tag=show&name=" + t);
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            Tag tag;
                            if (con.getResponseCode() == 200){
                                tag = gson.fromJson(new InputStreamReader(con.getInputStream()), Tag.class);
                                System.out.println("Tag found: " + tag.getId() + " - " + tag.getTag());
                            } else {
                                //System.out.println("Tag not found: " + t);
                                //System.out.println(con.getResponseCode() + " - " + con.getResponseMessage());
                                tag = new Tag(t);
                                
                                //System.out.println(json);
                                url = new URL("http://localhost:5000/tag");
                                con = (HttpURLConnection)url.openConnection();
                                con.setRequestMethod("POST");
                                con.setDoOutput(true);
                                con.setRequestProperty("Content-Type", "application/json");
                                String json = gson.toJson(tag, Tag.class);
                                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                                out.write(json);
                                out.flush();
                                out.close();
                                //System.out.println(con.getResponseCode() + " - " + con.getResponseMessage());
                                if (con.getResponseCode() == 201){
                                    tag = gson.fromJson(new InputStreamReader(con.getInputStream()), Tag.class);
                                    System.out.println("Tag created: " + tag.getId() + " - " + tag.getTag());
                                } else {
                                    System.out.println(con.getResponseCode() + " - " + con.getResponseMessage());
                                    tag = null;
                                }
                            }
                            if (tag != null)
                                System.out.println(tag.getAdiTag());
                        } catch (IOException ex){
                            System.err.println(ex.getMessage());    
                        }
                    }
                }
            } 
        }
    }
    
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance;

import com.adi.instance.model.*;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caique
 */
public class Configuration {
    
    public static final String LOCAL_PATH = System.getProperty("user.home") + File.separator + "adi" + File.separator;
    public static final String CONFIG_PATH = LOCAL_PATH + "instance" + File.separator;
    
    private final static Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    
    private Preferences preferences;
    private Source[] sources;
    private TagLists tagLists;
    
    public Configuration(){
        preferences = Preferences.defaultPreferences();
        sources = new Source[]{
            Source.defaultSource()
        };
        tagLists = TagLists.defaultTagLists();
    }
    
    public Configuration(String configPath) throws IOException {
        Gson gson = new Gson();
        FileReader fr;
        
        if (configPath == null || configPath.isEmpty()){
            configPath = CONFIG_PATH;
        }
        
        fr = new FileReader(configPath + "preferences.json");
        preferences = gson.fromJson(fr, Preferences.class);
        fr.close();
        
        fr = new FileReader(configPath + "sources.json");
        sources = gson.fromJson(fr, Source[].class);
        fr.close();
        
        fr = new FileReader(configPath + "tagLists.json");
        tagLists = gson.fromJson(fr, TagLists.class);
        fr.close();
    }
    
    public void reloadPreferences(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "preferences.json");
            preferences = new Gson().fromJson(fr, Preferences.class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }
    
    public void reloadSources(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "sources.json");
            sources = new Gson().fromJson(fr, Source[].class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }
    
    public void reloadTagLists(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "tagLists.json");
            tagLists = new Gson().fromJson(fr, TagLists.class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Source[] getSources() {
        return sources;
    }

    public Source getSourceByName(String name){
        for (Source s : sources){
            if (s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }
    
    public Source getSourceById(String id){
        for (Source s : sources){
            if (s.getId().equalsIgnoreCase(id)){
                return s;
            }
        }
        return null;
    }
    
    public Source getDefaultSource(){
        return getSourceByName(preferences.getDefaultSource());
    }
    
    public TagLists getTagLists() {
        return tagLists;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    public void setTagLists(TagLists tagLists) {
        this.tagLists = tagLists;
    }
    
    public void saveConfiguration() throws IOException {
        FileWriter fw;
        Gson gson = new Gson();
        
        File f;
        f = new File(CONFIG_PATH);
        if (!f.exists()){
            f.mkdirs();
        }
        
        fw = new FileWriter(CONFIG_PATH + "preferences.json");
        gson.toJson(preferences, Preferences.class, fw);
        fw.close();

        fw = new FileWriter(CONFIG_PATH + "sources.json");
        gson.toJson(sources, Source[].class, fw);
        fw.close();

        fw = new FileWriter(CONFIG_PATH + "tagLists.json");
        gson.toJson(tagLists, TagLists.class, fw);
        fw.close();
    }
}

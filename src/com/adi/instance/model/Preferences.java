/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance.model;

import com.adi.instance.Configuration;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caique
 */
public class Preferences {
    private String ident;
    private int version;
    private String theme;
    private String defaultSource;
    private String[] databaseBackup;
    private String[] inputFolder;
    private String[] outputFolder;
    private String[] outputCopy;

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDefaultSource() {
        return defaultSource;
    }

    public void setDefaultSource(String defaultSource) {
        this.defaultSource = defaultSource;
    }

    public String[] getDatabaseBackup() {
        return databaseBackup;
    }
    
    public List<String> listDatabaseBackup(){
        return Arrays.asList(databaseBackup);
    }

    public void setDatabaseBackup(String[] databaseBackup) {
        this.databaseBackup = databaseBackup;
    }

    public String[] getInputFolder() {
        return inputFolder;
    }
    
    public List<String> listInputFolder(){
        return Arrays.asList(inputFolder);
    }

    public void setInputFolder(String[] inputFolder) {
        this.inputFolder = inputFolder;
    }

    public String[] getOutputFolder() {
        return outputFolder;
    }

    public List<String> listOutputFolder(){
        return Arrays.asList(outputFolder);
    }
    
    public void setOutputFolder(String[] outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String[] getOutputCopy() {
        return outputCopy;
    }

    public List<String> listOutputCopy(){
        return Arrays.asList(outputCopy);
    }
    
    public void setOutputCopy(String[] outputCopy) {
        this.outputCopy = outputCopy;
    }
    
    public static Preferences defaultPreferences(){
        Preferences p = new Preferences();
        
        String host = "Localhost";
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.toString());
        }
        p.ident = host + "/" + System.getProperty("user.name");
        p.version = 1;
        p.theme = "Windows";
        p.inputFolder = new String[]{
            Configuration.LOCAL_PATH + "default_input" + File.separator
        };
        p.outputFolder = new String[]{
            Configuration.LOCAL_PATH + "default_output" + File.separator
        };
        p.databaseBackup = new String[]{
            Configuration.LOCAL_PATH + "default_dbbackup" + File.separator
        };
        p.outputCopy = new String[0];
        p.defaultSource = "Danbooru";
        
        String[] folders = new String[]{
            Configuration.LOCAL_PATH + "default_input" + File.separator,
            Configuration.LOCAL_PATH + "default_output" + File.separator,
            Configuration.LOCAL_PATH + "default_dbbackup" + File.separator
        };
        for (String folder : folders){
            File f = new File(folder);
            if (!f.exists()) f.mkdirs();
        }
        
        return p;
    }

    @Override
    public String toString() {
        return "Preferences{" + "ident=" + ident + ", version=" + version + ", theme=" + theme + ", defaultSource=" + defaultSource + ", databaseBackup=" + Arrays.toString(databaseBackup) + ", inputFolder=" + Arrays.toString(inputFolder) + ", outputFolder=" + Arrays.toString(outputFolder) + ", outputCopy=" + Arrays.toString(outputCopy) + '}';
    }
    
    
}

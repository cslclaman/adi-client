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
    private String[] databaseBackups;
    private String[] inputFolders;
    private String[] outputFolders;
    private String[] outputCopies;

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

    public String[] getDatabaseBackups() {
        return databaseBackups;
    }
    
    public List<String> listDatabaseBackups(){
        return Arrays.asList(databaseBackups);
    }

    public void setDatabaseBackups(String[] databaseBackups) {
        this.databaseBackups = databaseBackups;
    }

    public String[] getInputFolders() {
        return inputFolders;
    }
    
    public List<String> listInputFolders(){
        return Arrays.asList(inputFolders);
    }

    public void setInputFolders(String[] inputFolders) {
        this.inputFolders = inputFolders;
    }

    public String[] getOutputFolders() {
        return outputFolders;
    }

    public List<String> listOutputFolders(){
        return Arrays.asList(outputFolders);
    }
    
    public void setOutputFolders(String[] outputFolders) {
        this.outputFolders = outputFolders;
    }

    public String[] getOutputCopies() {
        return outputCopies;
    }

    public List<String> listOutputCopies(){
        return Arrays.asList(outputCopies);
    }
    
    public void setOutputCopies(String[] outputCopies) {
        this.outputCopies = outputCopies;
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
        p.inputFolders = new String[]{
            Configuration.LOCAL_PATH + "default_input" + File.separator
        };
        p.outputFolders = new String[]{
            Configuration.LOCAL_PATH + "default_output" + File.separator
        };
        p.databaseBackups = new String[]{
            Configuration.LOCAL_PATH + "default_dbbackup" + File.separator
        };
        p.outputCopies = new String[0];
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
        return "Preferences{" + "ident=" + ident + ", version=" + version + ", theme=" + theme + ", defaultSource=" + defaultSource + ", databaseBackup=" + Arrays.toString(databaseBackups) + ", inputFolder=" + Arrays.toString(inputFolders) + ", outputFolder=" + Arrays.toString(outputFolders) + ", outputCopy=" + Arrays.toString(outputCopies) + '}';
    }
    
    
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.instance.Configuration;
import com.adi.instance.model.Preferences;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caique
 */
public class ConfigurationLoader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configuration cf;
        try {
             cf = new Configuration();
        } catch (IOException ex){
            System.err.println(ex.toString());
            cf = Configuration.defaults();
            try {
                cf.saveConfiguration();
            } catch (IOException ex2){
                System.err.println(ex2.toString());
            }
        }
        
        Preferences pf = cf.getPreferences();
        System.out.println(cf.getSourceByName("Sankaku complex"));
        System.out.println(cf.getSourceByName("Danbooru"));
    }
    
}

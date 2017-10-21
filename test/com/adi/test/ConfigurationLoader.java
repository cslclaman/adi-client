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
        try {
            Configuration cf = new Configuration(null);
            Preferences pf = Preferences.defaultPreferences();
            System.out.println(cf.getSourceByName("Sankaku complex"));
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
    }
    
}

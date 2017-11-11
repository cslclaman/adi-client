/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.model.data.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author Caique
 */
public class ApiRestConsumer {

    public static void main(String[] args) {
        Gson parser = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        try {
            
            URL uAdiTags = new URL("http://localhost:5000/aditag?limit=100");
            AdiTag[] lista = parser.fromJson(new InputStreamReader(uAdiTags.openStream()), AdiTag[].class);
            //for (Tag i : lista){
            for (AdiTag i : lista){
                /*System.out.printf("ID %d\tTAG %s\tURL %s\tADI TAG %s\n",
                        i.getId(),
                        i.getTag(),
                        (i.getUrl() == null ? "---" : i.getUrl()),
                        (i.getAdiTag() == null ? "---" : i.getAdiTag().toString())
                );*/
                System.out.printf("ID %d\t(%s)%s\n",
                        i.getId(),
                        i.getType(),
                        i.getTag()
                );
                URL uTags = new URL("http://localhost:5000/tag?association=" + i.toString());
                Tag[] tags = parser.fromJson(new InputStreamReader(uTags.openStream()), Tag[].class);
                for (Tag t : tags){
                    System.out.printf("\t%d\t%s%s\n",
                        t.getId(),
                        t.getTag(),
                        (t.getUrl() != null && !t.getUrl().equals(t.getTag()) ? "\t(url="+t.getUrl()+")" : "")
                );
                }
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        
    }
    
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.data;

import com.adi.model.data.Tag;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Caique
 */
public class TagController extends Controller {
    public TagController(String baseUrl) {
        super(baseUrl + "/tag");
    }
    
    public Tag find(String name){
        Tag tag = null;
        try {
            URL url = new URL(baseUrl + "/find?adi_tag=show&name=" + name);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            int response = con.getResponseCode();
            
            if (response == HttpURLConnection.HTTP_OK){
                tag = gson.fromJson(new InputStreamReader(con.getInputStream()), Tag.class);
            } else {
                System.out.println(response + " - " + con.getResponseMessage());
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        return tag;
    }
    
    public Tag create(Tag tag){
        Tag created = null;
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            
            out.write(gson.toJson(tag, Tag.class));
            out.close();
            
            int response = con.getResponseCode();
            if (response == HttpURLConnection.HTTP_CREATED){
                created = gson.fromJson(new InputStreamReader(con.getInputStream()), Tag.class);
            } else {
                System.out.println(response + " - " + con.getResponseMessage());
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        return created;
    }
    
    public Tag findOrCreate(String name){
        Tag tag = find(name);
        if (tag != null){
            return tag;
        } else {
            tag = new Tag(name);
            return create(tag);
        }
    }
    
    public Tag edit(Tag tag){
        throw new UnsupportedOperationException("Não implementado ainda");
    }
}

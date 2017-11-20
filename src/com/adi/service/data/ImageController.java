/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.data;

import com.adi.model.data.Image;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Caique
 */
public class ImageController extends Controller {

    public ImageController(String baseUrl) {
        super(baseUrl + "/image");
    }
    
    public Image find(int id){
        Image image = null;
        try {
            URL url = new URL(baseUrl + "/find?source=hide&id=" + id);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            int response = con.getResponseCode();
            
            if (response == HttpURLConnection.HTTP_OK){
                image = gson.fromJson(new InputStreamReader(con.getInputStream()), Image.class);
            } else {
                System.out.println(response + " - " + con.getResponseMessage());
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        return image;
    }
    
    public Image create(Image image){
        Image created = null;
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            
            out.write(gson.toJson(image, Image.class));
            out.close();
            
            int response = con.getResponseCode();
            if (response == HttpURLConnection.HTTP_CREATED){
                created = gson.fromJson(new InputStreamReader(con.getInputStream()), Image.class);
            } else {
                System.out.println(response + " - " + con.getResponseMessage());
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        return created;
    }
}

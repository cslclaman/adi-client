/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.model.source.danbooru.DanbooruPost;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 *
 * @author Caique
 */
public class JsonParse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        String search = "md5:6b2159744ae4008aad45ed3d1903d7dc";
        URL url = new URL("http://sonohara.donmai.us/posts.json?tags=" + search);
        HttpURLConnection cn = (HttpURLConnection)url.openConnection();
        cn.setRequestMethod("GET");
        InputStreamReader r = new InputStreamReader(cn.getInputStream());
        DanbooruPost[] dt = gson.fromJson(r, DanbooruPost[].class);
        r.close();
        
        for (DanbooruPost p : dt){
            System.out.println(p.toString());
            System.out.println(Arrays.toString(p.getTagStringList()));
            System.out.println(p.getTagCount() + " " + p.getTagStringList().length);
            System.out.println(p.getTagCountArtist()+ " " + p.getTagStringArtist());
            System.out.println(p.getTagCountCharacter()+ " " + p.getTagStringCharacter());
            System.out.println(p.getTagCountCopyright()+ " " + p.getTagStringCopyright());
            System.out.println(p.getTagCountGeneral()+ " " + p.getTagStringGeneral());
            System.out.println(p.getCreatedAt());
        }
    }
    
}

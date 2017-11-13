/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.data;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Caique
 */
public abstract class Controller {
    protected String baseUrl;
    protected Gson gson;

    public Controller(String baseUrl) {
        baseUrl = baseUrl.replace("//", "/");
        baseUrl = baseUrl.replace(":/", "://");
        this.baseUrl = baseUrl;
        System.out.println(baseUrl);
        gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }
}

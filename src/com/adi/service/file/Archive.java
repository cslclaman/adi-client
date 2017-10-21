/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.file;

import com.adi.service.function.Hash;
import java.io.File;
import java.net.URI;

/**
 *
 * @author Caique
 */
public class Archive extends File {
    private String md5 = "";
    private String extension;
    private String typeName;
    
    public Archive(String pathname) {
        super(pathname);
        init();
    }

    public Archive(String parent, String child) {
        super(parent, child);
        init();
    }

    public Archive(File parent, String child) {
        super(parent, child);
        init();
    }

    public Archive(URI uri) {
        super(uri);
        init();
    }
    
    private void init(){
        extension = getName().substring(getName().lastIndexOf("."));
        
    }
    
    public String getMd5(){
        if (md5.isEmpty()){
            md5 = Hash.md5(this);
        }
        return md5;
    }
    
    public String getQuery(){
        String name = getName().substring(0, getName().lastIndexOf(".")).toLowerCase();
        
        if (name.equals(getMd5())){
            return md5;
        } else {
            if (name.startsWith("(s") || name.startsWith("(ADI)")){
                
            } else {
                int seq = 0;
                String hash = "";

                for (char c : name.toCharArray()){
                    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')){
                        hash = hash + c;
                        seq ++;
                    } else {
                        hash = "";
                        seq = 0;
                    }
                    if (seq == 32){
                        return hash;
                    }
                }
            }
        }
        return md5;
    }
}

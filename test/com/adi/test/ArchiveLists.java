/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.test;

import com.adi.instance.Configuration;
import com.adi.service.file.file.Archive;
import com.adi.service.file.Archiver;
import java.io.IOException;

/**
 *
 * @author Caique
 */
public class ArchiveLists {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        Archiver archiver = new Archiver(conf.getPreferences().getInputFolders(), true);
        for (Archive a : archiver.getArchiveList()){
            System.out.println(a.getPath());
        }
        System.out.println(archiver.archivesCount());
        archiver.removeFolder("C:\\Users\\Caique\\Dropbox\\V_Imagens\\Twitter\\", true);
        for (Archive a : archiver.getArchiveList()){
            System.out.println(a.getPath());
        }
        System.out.println(archiver.archivesCount());
        archiver.removeArchive("C:\\Users\\Caique\\Dropbox\\V_Imagens\\D_Gelbooru\\2b2081632ad72f5b76ed1f3f63d9e1e4.png");
        for (Archive a : archiver.getArchiveList()){
            System.out.println(a.getPath());
        }
        System.out.println(archiver.archivesCount());
    }
    
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.file;

import com.adi.service.file.file.Archive;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Caique
 */
public class Archiver {
    private List<Archive> archives;
    private List<Archive> inputs;
    
    public Archiver(String[] inputList, boolean addSubfolders){
        inputs = new LinkedList<>();
        archives = new LinkedList<>();
        for (String s : inputList){
            addInput(s, addSubfolders);
        }
    }
    
    public Archiver(List<String> inputList, boolean addSubfolders){
        inputs = new LinkedList<>();
        archives = new LinkedList<>();
        for (String s : inputList){
            addInput(s, addSubfolders);
        }
    }
    
    public final int addInput(String inputPath, boolean addSubfolders){
        int count = 0;
        try {
            Archive arq = new Archive(inputPath);
            if (addSubfolders){
                searchDirectoryListRecursive(inputs, arq);
            } else {
                if (!inputs.contains(arq)){
                    inputs.add(arq);
                }
            }
        } catch (IOException ex){
            System.err.println(ex.toString());
        }
        
        //Implementação que foca a praticidade
        archives.clear();
        //Se ficar lento demais, implementar método para adicionar arquivos apenas da nova pasta adicionada, sem limpar lista.
        for (Archive input : inputs){
            for (File f : input.listFiles()){
                if (!f.isDirectory()){
                    try {
                        Archive a = new Archive(f.getPath());
                        archives.add(a);
                        count++;
                    } catch (IOException ex){
                        System.err.println(ex.toString());
                    }
                }
            }
        }
        return count;
    }
    
    private void searchDirectoryListRecursive(List<Archive> list, Archive ar){
        if (!list.contains(ar)){
            list.add(ar);
        }
        for (File f : ar.listFiles()){
            if (f.isDirectory()){
                try {
                    Archive a = new Archive(f.getPath());
                    searchDirectoryListRecursive(list, a);
                } catch (IOException ex){
                    System.err.println(ex.toString());
                } 
            }
        }
    }

    public int archivesCount(){
        return archives.size();
    }
    
    public int removeFolder(String folder, boolean removeSubfolders){
        int count = 0;
        Iterator<Archive> it = inputs.iterator();
        while (it.hasNext()){
            if (removeSubfolders){
                if (it.next().getPath().contains(folder)){
                    it.remove();
                }
            } else {
                if (it.next().getPath().equals(folder)){
                    it.remove();
                }
            }
        }
        it = archives.iterator();
        while (it.hasNext()){
            Archive a = it.next();
            if (removeSubfolders){
                if (a.getPath().replace(a.getName(), "").contains(folder)){
                    it.remove();
                    count++;
                }
            } else {
                if (a.getPath().replace(a.getName(), "").equals(folder)){
                    it.remove();
                    count++;
                }
            }
        }
        return count;
    }
    
    public int removeFolders(String[] folders, boolean removeSubfolders){
        int count = 0;
        for (String f : folders){
            count += removeFolder(f, removeSubfolders);
        }
        return count;
    }
    
    public int removeFolders(List<String> folders, boolean removeSubfolders){
        return removeFolders((String[])folders.toArray(), removeSubfolders);
    }
    
    public int removeArchive(String archive){
        Iterator<Archive> it = archives.iterator();
        while (it.hasNext()){
            if (it.next().getPath().equals(archive)){
                it.remove();
                return 1;
            }
        }
        return 0;
    }
    
    public int removeArchives(String[] archives){
        int count = 0;
        for (String a : archives){
            count += removeArchive(a);
        }
        return count;
    }
    
    public int removeArchives(List<String> archives){
        return removeArchives((String[])archives.toArray());
    }
    
    public List<Archive> getArchiveList() {
        return archives;
    }
    
    public List<Archive> getArchiveList(int limit) {
        return archives.subList(0, limit);
    }
}

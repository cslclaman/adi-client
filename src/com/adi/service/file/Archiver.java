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
 * Classe "Arquivadora" responsável por listar arquivos em pastas/subpastas
 * <br>Permite listar arquivos a partir de Strings com caminhos absolutos, remover arquivos da lista, etc.
 * @author Caique
 */
public class Archiver {
    private List<Archive> archives;
    private List<Archive> inputs;
    
    /**
     * Instancia um Archiver sem arquivos iniciais.
     */
    public Archiver(){
        inputs = new LinkedList<>();
        archives = new LinkedList<>();
    }
    
    /**
     * Instancia um archiver e adiciona arquivos a partir de um array de strings com caminhos.
     * <br>Se selecionar adicionar subpastas, para cada pasta encontrada será feita uma pesquisa em profundidade para listar outras subpastas.
     * @param inputList Array com caminhos absolutos de cada pasta raiz
     * @param addSubfolders Se TRUE, subpastas serão verificadas. Se não, apenas arquivos na raiz serão adicionados.
     */
    public Archiver(String[] inputList, boolean addSubfolders){
        inputs = new LinkedList<>();
        archives = new LinkedList<>();
        for (String s : inputList){
            addInput(s, addSubfolders);
        }
    }
    
    /**
     * Instancia um archiver e adiciona arquivos a partir de uma lista de strings com caminhos.
     * <br>Se selecionar adicionar subpastas, para cada pasta encontrada será feita uma pesquisa em profundidade para listar outras subpastas.
     * @param inputList Lista com caminhos absolutos de cada pasta raiz
     * @param addSubfolders Se TRUE, subpastas serão verificadas. Se não, apenas arquivos na raiz serão adicionados.
     */
    public Archiver(List<String> inputList, boolean addSubfolders){
        inputs = new LinkedList<>();
        archives = new LinkedList<>();
        for (String s : inputList){
            addInput(s, addSubfolders);
        }
    }
    
    /**
     * Adiciona uma pasta à lista de pastas de entrada.
     * @param inputPath Caminho absoluto da pasta
     * @param addSubfolders Se TRUE, subpastas serão verificadas. Se não, apenas arquivos na raiz serão adicionados.
     * @return Número de arquivos encontrado nas pastas.
     */        
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
        //Se ficar lento demais, implementar método para adicionar arquivos apenas da nova pasta/subpasta adicionada, sem limpar lista.
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
    
    /**
     * Adiciona várias pastas indicadas em um array à lista de pastas de entrada.
     * @param inputPaths Array com caminhos absolutos das pastas a adicionar
     * @param addSubfolders Se TRUE, subpastas serão verificadas. Se não, apenas arquivos na raiz serão adicionados.
     * @return Número de arquivos encontrado nas pastas.
     */        
    public final int addInputList(String[] inputPaths, boolean addSubfolders){
        int count = 0;
        for (String path : inputPaths){
            count += addInput(path, addSubfolders);
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

    /**
     * Retorna tamanho da lista de arquivos.
     * @return número de arquivos listado.
     */
    public int archivesCount(){
        return archives.size();
    }
    
    /**
     * Remove uma pasta e os arquivos nela contidos da lista de arquivos.
     * <br>Se removeSubfolders estiver selecionado, subpastas contidas na pasta informada (e arquivos nessas subpastas) serão removidos também.
     * Senão, apenas arquivos que estiverem nela (raiz) serão removidos e subpastas permanecem.
     * @param folder Caminho absoluto da pasta a ser removida.
     * @param removeSubfolders Se deve remover subpastas e arquivos dentro de subpastas da listagem.
     * @return Número de arquivos removidos.
     */
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
    
    /**
     * Remove pastas informadas e arquivos nelas contidos da lista de arquivos.
     * <br>Para cada caminho informado no array, executa o método {@link #removeFolder}.
     * @param folders Array de strings com caminhos absolutos das pastas a serem removidas.
     * @param removeSubfolders Se deve remover subpastas e arquivos dentro de subpastas da listagem.
     * @return Número total de arquivos removidos.
     */
    public int removeFolders(String[] folders, boolean removeSubfolders){
        int count = 0;
        for (String f : folders){
            count += removeFolder(f, removeSubfolders);
        }
        return count;
    }
    
    /**
     * Remove pastas informadas e arquivos nelas contidos da lista de arquivos.
     * <br>Para cada caminho informado no array, executa o método {@link #removeFolder}.
     * @param folders Lista de strings com caminhos absolutos das pastas a serem removidas.
     * @param removeSubfolders Se deve remover subpastas e arquivos dentro de subpastas da listagem.
     * @return Número total de arquivos removidos.
     */
    public int removeFolders(List<String> folders, boolean removeSubfolders){
        return removeFolders((String[])folders.toArray(), removeSubfolders);
    }
    
    /**
     * Remove um único arquivo da lista de arquivos.
     * @param archive Caminho absoluto do arquivo a remover
     * @return 1 se arquivo foi removido, 0 se não foi.
     */
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
    
    /**
     * Remove os arquivos informados da lista de arquivos.
     * <br>Para cada caminho informado no array, executa o método {@link #removeArchive}.
     * @param archives Array de strings com caminhos absolutos dos arquivos
     * @return Número de arquivos removidos.
     */
    public int removeArchives(String[] archives){
        int count = 0;
        for (String a : archives){
            count += removeArchive(a);
        }
        return count;
    }
    
    /**
     * Remove os arquivos informados da lista de arquivos.
     * <br>Para cada caminho informado na lista, executa o método {@link #removeArchive}.
     * @param archives Lista de strings com caminhos absolutos dos arquivos
     * @return Número de arquivos removidos.
     */
    public int removeArchives(List<String> archives){
        return removeArchives((String[])archives.toArray());
    }
    
    /**
     * Retorna a lista de arquivos.
     * @return Lista com arquivos
     */
    public List<Archive> getArchiveList() {
        return archives;
    }
    
    /**
     * Retorna a lista de arquivos limitada a uma quantidade de arquivos.
     * @param limit Limite de arquivos a retornar
     * @return Sublista com número de arquivos especificado, a contar do início da lista
     */
    public List<Archive> getArchiveList(int limit) {
        return archives.subList(0, limit);
    }
}

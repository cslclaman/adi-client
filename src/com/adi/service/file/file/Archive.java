/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.file.file;

import com.adi.service.function.Hash;
import com.adi.service.search.Search;
import com.adi.service.search.SearchTypeParameter;
import com.adi.service.tags.AdiTagsModel;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Classe que representa um arquivo de imagem ADI, sendo especialização de {@link File}.
 * <br>Possui métodos específicos para obter MD5 e decodificar nome de um arquivo.
 * @author Caique
 */
public class Archive extends File {
    
    /**
     * Lista de formatos de arquivo suportados pelo <s>Danbooru</s> ADI.
     * <br>Inclui formatos de imagem (JPG, PNG, GIF) e também vídeo (WEBM, MP4, SWF).
     */
    public static final String[] SUPPORTED_FILE_EXT = new String[]{
        "JPG", "JPEG",
        "PNG",
        "GIF",
        "SWF",
        "WEBM",
        "MP4"
    };
    
    private static final String SEARCH_TYPE_MD5 = "md5";
    private static final String SEARCH_TYPE_ID = "id";
    private static final String SEARCH_TYPE_NULL = "";
    
    private String md5 = "";
    private String extension;
    private String searchQuery;
    private String searchTypeName;
    
    /**
     * Cria uma nova instância de Archive convertendo o caminho para um path abstrato.
     * <br>Invoca o construtor da superclasse com o mesmo parâmetro e depois valida o arquivo.
     * @param pathname Nome do caminho do arquivo
     * @throws IOException Se o arquivo/pasta não existir, ou se for informado um arquivo de extensão inválida (para o ADI).
     */
    public Archive(String pathname) throws IOException{
        super(pathname);
        init();
    }

    /**
     * Cria uma nova instância de Archive a partir do caminho pai (raiz) e filho (subpasta/arquivo).
     * <br>Se o nome do caminho pai (parent) for nulo ou vazio, invoca o construtor com apenas um parâmetro.
     * Senão, trata e converte os caminhos/nomes informados ao criar. Lança NullPointerException caso o nome do caminho filho seja nulo. 
     * <br>Invoca o construtor da superclasse com os mesmos parâmetros e depois valida o arquivo.
     * @param parent Nome do caminho pai. Em geral, indica um diretório raiz.
     * @param child Nome do caminho filho. Indica um subdiretório ou arquivo dentro da pasta pai.
     * @throws IOException Se o arquivo/pasta não existir, ou se for informado um arquivo de extensão inválida (para o ADI).
     */
    public Archive(String parent, String child) throws IOException{
        super(parent, child);
        init();
    }

    /**
     * Cria uma nova instância de Archive a partir do pai (raiz) e filho (subpasta/arquivo).
     * <br>Se o File pai (parent) for nulo ou vazio, invoca o construtor com apenas um parâmetro.
     * Senão, trata e converte os caminhos/nomes informados ao criar. Lança NullPointerException caso o nome do caminho filho seja nulo. 
     * <br>Invoca o construtor da superclasse com os mesmos parâmetros e depois valida o arquivo.
     * @param parent File representando o caminho pai. Em geral, indica um diretório raiz.
     * @param child Nome do caminho filho. Indica um subdiretório ou arquivo dentro da pasta pai.
     * @throws IOException Se o arquivo/pasta não existir, ou se for informado um arquivo de extensão inválida (para o ADI).
     */
    public Archive(File parent, String child) throws IOException{
        super(parent, child);
        init();
    }
    
    /**
     * Cria uma nova instância de Archive a partir de uma referência URI (Uniform Resource Identifier).
     * <br>Converte o URI informado em um caminho de arquivo abstrato conforme o tipo de sistema.
     * <br>Este construtor invoca o construtor da superclasse File com o mesmo parâmetro e depois valida o arquivo.
     * @param uri Uri que atenda ao schema "file", não vazio
     * @throws IOException Se o arquivo/pasta não existir, ou se for informado um arquivo de extensão inválida (para o ADI).
     */
    public Archive(URI uri) throws IOException {
        super(uri);
        init();
    }
    
    private void init() throws IOException {
        searchQuery = "";
        searchTypeName = SEARCH_TYPE_NULL;
        
        if (!exists()){
            throw new IOException("File doesn't exist - " + getPath());
        }
        
        if (!isDirectory()){
            extension = getName().substring(getName().lastIndexOf(".") + 1).toLowerCase();

            boolean validExt = false;
            for (String ext : SUPPORTED_FILE_EXT){
                if (extension.equals(ext.toLowerCase())){
                    validExt = true;
                    break;
                }
            }

            if (!validExt){
                throw new IOException("File has invalid extension for ADI - " + getPath());
            }
        } else {
            extension = "";
        }
    }
    
    /**
     * Retorna a extensão (tipo) do arquivo.
     * @return extensão lower-case - ".jpg"
     */
    public String getExtension(){
        return extension;
    }
    
    /**
     * Calcula e retorna hash MD5 desse arquivo.
     * @return 
     */
    public String getMd5(){
        if (md5.isEmpty()){
            md5 = Hash.md5(this);
        }
        return md5;
    }
    
    /**
     * Verifica e retorna o tipo de pesquisa (ID/MD5/outras) a ser realizado.
     * @return Enum {@link SearchTypeParameter}
     */
    public SearchTypeParameter getQueryTypeParameter(){
        if (searchTypeName.isEmpty()){
            makeSearch();
        }
        switch (searchTypeName){
            case SEARCH_TYPE_MD5:
                return SearchTypeParameter.BY_MD5;
            case SEARCH_TYPE_ID:
                return SearchTypeParameter.BY_ID;
            default:
                return SearchTypeParameter.BY_RAW;
        }
    }
    
    /**
     * Verifica e retorna texto de pesquisa de acordo com o tipo (MD5/ID/Etc).
     * @return String com o texto (exemplo: Tipo = ID, retorna "2713801")
     */
    public String getQueryText(){
        if (searchQuery.isEmpty()){
            makeSearch();
        }
        return searchQuery;
    }
    
    private void makeSearch(){
        String name = getName().substring(0, getName().lastIndexOf(".")).toLowerCase();
        
        if (!name.contains(getMd5())){
            if (name.startsWith("(s") || name.startsWith("(ADI)")){
                AdiTagsModel model = new AdiTagsModel(name);
                searchTypeName = SEARCH_TYPE_ID;
                searchQuery = model.getSource().getTag();
            } else {
                int seq = 0;
                StringBuilder hash = new StringBuilder();

                for (char c : name.toCharArray()){
                    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')){
                        hash.append(c);
                        seq ++;
                    } else {
                        hash = new StringBuilder();
                        seq = 0;
                    }
                    if (seq == 32){
                        break;
                    }
                }
                
                searchTypeName = SEARCH_TYPE_MD5;
                if (seq == 32){
                    searchQuery = hash.toString();
                } else {
                    searchQuery = md5;
                }
            }
        } else {
            searchTypeName = SEARCH_TYPE_MD5;
            searchQuery = md5;
        }
    }
    
    public String getRelativeFilePath(){
        return getPath().replace(separator, "$");
    }
}

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance;

import com.adi.instance.model.*;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de agrupamento de configurações, preferências e variáveis do sistema (ADI).
 * <br>Classe responsável por carregar, salvar e manipular configurações diversas.
 * <br>Também carrega configurações padrão e cria pastas padrões do sistema.
 * @author Caique
 */
public class Configuration {
    /**
     * Caminho da pasta raiz das configurações e pastas padrão do sistema.
     * <br>Costuma ficar em <code>USUÁRIO/adi</code>
     */
    public static final String LOCAL_PATH = System.getProperty("user.home") + File.separator + "adi" + File.separator;
    
    /**
     * Caminho da pasta específica de preferências do usuário/computador (Instância).
     * <br>Costuma ficar em <code>USUÁRIO/adi/instance</code>
     * <br>Contém os arquivos:
     * <ul>
     * <li><code>preferences.json</code> - lista preferências alteráveis do sistema como pastas de entrada e origem padrão</li>
     * <li><code>sources.json</code> - lista origens e endereços/métodos específicos de cada API</li>
     * <li><code>tagLists.json</code> - lista tags responsáveis por classificação etária automática</li>
     * </ul>
     */
    public static final String CONFIG_PATH = LOCAL_PATH + "instance" + File.separator;
    
    /**
     * Caminho da pasta específica de arquivos temporários do sistema.
     * <br>Costuma ficar em <code>USUÁRIO/adi/temp</code>
     * <br>Registra downloads temporários e 
     */
    public static final String TEMP_PATH = LOCAL_PATH + "temp" + File.separator;
    
    private final static Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    
    private Preferences preferences;
    private Source[] sources;
    private TagLists tagLists;
    
    /**
     * Gera uma instância Configuration com configurações padrão.
     * <br>Carrega as preferências padrão do sistema, juntamente com uma única origem (Danbooru) e tagLists padrão.
     * @return Configurações padrão do sistema.
     */
    public static Configuration defaults(){
        return new Configuration(Preferences.defaultPreferences(), new Source[]{Source.defaultSource()}, TagLists.defaultTagLists());
    }

    private Configuration(Preferences preferences, Source[] sources, TagLists tagLists) {
        this.preferences = preferences;
        this.sources = sources;
        this.tagLists = tagLists;
    }
    
    /**
     * Carrega as configurações no caminho padrão (veja <code>CONFIG_PATH</code>
     * @throws IOException Em caso de erro na criação do leitor de arquivos.
     */
    public Configuration() throws IOException{
        this(CONFIG_PATH);
    }
    
    /**
     * Carrega as configurações no caminho especificado.
     * @param configPath Caminho personalizado onde estão as configurações.
     * @throws IOException Em caso de erro na criação do leitor de arquivos.
     */
    public Configuration(String configPath) throws IOException {
        Gson gson = new Gson();
        FileReader fr;
        
        fr = new FileReader(configPath + "preferences.json");
        preferences = gson.fromJson(fr, Preferences.class);
        fr.close();
        
        fr = new FileReader(configPath + "sources.json");
        sources = gson.fromJson(fr, Source[].class);
        fr.close();
        
        fr = new FileReader(configPath + "tagLists.json");
        tagLists = gson.fromJson(fr, TagLists.class);
        fr.close();
    }
    
    /**
     * Atualiza as preferências ao reler o arquivo de origem.
     */
    public void reloadPreferences(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "preferences.json");
            preferences = new Gson().fromJson(fr, Preferences.class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }
    
    /**
     * Atualiza as Sources ao reler o arquivo de origem.
     */
    public void reloadSources(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "sources.json");
            sources = new Gson().fromJson(fr, Source[].class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }
    
    /**
     * Atualiza as TagLists ao reler o arquivo de origem.
     */
    public void reloadTagLists(){
        try {
            FileReader fr = new FileReader(CONFIG_PATH + "tagLists.json");
            tagLists = new Gson().fromJson(fr, TagLists.class);
        } catch (IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Source[] getSources() {
        return sources;
    }

    /**
     * Pesquisa por nome uma source registrada nas configurações (case insensitive)
     * @param name Nome a pesquisar (ex. Danbooru, Gelbooru).
     * @return Source que tenha o nome informado ou null se não encontrar.
     */
    public Source getSourceByName(String name){
        for (Source s : sources){
            if (s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }
    
    /**
     * Pesquisa por id (sigla) uma source registrada nas configurações (case insensitive)
     * @param id id a pesquisar (ex. d, g) 
     * @return Source que tenha o ID informado ou null se não encontrar.
     */
    public Source getSourceById(String id){
        for (Source s : sources){
            if (s.getId().equalsIgnoreCase(id)){
                return s;
            }
        }
        return null;
    }
    
    /**
     * Retorna a source padrão conforme definida nas preferências
     * <br>Pesquisa source que tenha o nome igual ao definido na classe Preferences.
     * @return Source padrão
     */
    public Source getDefaultSource(){
        return getSourceByName(preferences.getDefaultSource());
    }
    
    public TagLists getTagLists() {
        return tagLists;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    public void setTagLists(TagLists tagLists) {
        this.tagLists = tagLists;
    }
    
    /**
     * Salva as configurações atuais e sobrescreve arquivos de configuração existentes.
     * <br>Se não existirem, cria as pastas e subpastas necessárias,conforme caminho padrão (vide <code>CONFIG_PATH</code>
     * @throws IOException Caso ocorra algum erro ao criar/recriar arquivos e escrever dados.
     */
    public void saveConfiguration() throws IOException {
        FileWriter fw;
        Gson gson = new Gson();
        
        File f;
        f = new File(CONFIG_PATH);
        if (!f.exists()){
            f.mkdirs();
        }
        
        fw = new FileWriter(CONFIG_PATH + "preferences.json");
        gson.toJson(preferences, Preferences.class, fw);
        fw.close();

        fw = new FileWriter(CONFIG_PATH + "sources.json");
        gson.toJson(sources, Source[].class, fw);
        fw.close();

        fw = new FileWriter(CONFIG_PATH + "tagLists.json");
        gson.toJson(tagLists, TagLists.class, fw);
        fw.close();
    }
}

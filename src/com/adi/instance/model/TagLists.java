/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.instance.model;

import java.util.Arrays;

/**
 * Representa listas de tags usadas para definições de classificação etária.
 * @author Caique
 */
public class TagLists {

    private String[] tagsQAC;
    private String[] tagsOPP;
    private String[] tagsPFNV;

    public String[] getTagsQAC() {
        return tagsQAC;
    }

    public void setTagsQAC(String[] tagsQAC) {
        this.tagsQAC = tagsQAC;
    }

    public boolean tagsQACcontainsTag(String tag){
        for (String t : tagsQAC){
            if (t.equals(tag)){
                return true;
            }
        }
        return false;
    }
    
    public String[] getTagsOPP() {
        return tagsOPP;
    }

    public void setTagsOPP(String[] tagsOPP) {
        this.tagsOPP = tagsOPP;
    }

    public boolean tagsOPPcontainsTag(String tag){
        for (String t : tagsOPP){
            if (t.equals(tag)){
                return true;
            }
        }
        return false;
    }
    
    public String[] getTagsPFNV() {
        return tagsPFNV;
    }

    public void setTagsPFNV(String[] tagsPFNV) {
        this.tagsPFNV = tagsPFNV;
    }
    
    public boolean tagsPFNVcontainsTag(String tag){
        for (String t : tagsPFNV){
            if (t.equals(tag)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Carrega listas com as tags padrão para classificação etária.
     * <br><b>TagsQAC:</b> Referentes a cenas provocativas, mas sem nudez ou coisas explícitas.
     * <br><b>TagsOPP:</b> Contém nudez e/ou atos implícitos/sugestivos.
     * <br><b>TagsPFNV:</b> Aplicam-se a imagens eróticas/pornográficas, cenas explícitas ou de conteúdo extremo em geral.
     * @return TagLists carregadas.
     */
    public static TagLists defaultTagLists() {
        TagLists t = new TagLists();
        
        t.tagsQAC = new String[]{
            "undressing",
            "topless",
            "ass",
            "bottomless",
            "cameltoe",
            "partially_visible_vulva",
            "nude",
            "vibrator",
            "covered_nipples",
            "bdsm"
        };
        t.tagsOPP = new String[]{
            "bandaids_on_nipples",
            "nipples",
            "pussy",
            "penis",
            "implied_sex",
            "object_insertion",
            "pussy_juice",
            "cum",
            "areolae"
        };
        t.tagsPFNV = new String[]{
            "sex",
            "vaginal",
            "anal",
            "ejaculation",
            "female_ejaculation",
            "guro",
            "rape"
        };
        
        return t;
    }

    /**
     * Retorna o número de tags em cada lista.
     * @return "Tag Lists: X tags QAC, Y tags OPP, Z tags PFNV"
     */
    @Override
    public String toString() {
        return "Tag Lists: " + tagsQAC.length + " tags QAC, " + tagsOPP.length + " tags OPP, " + tagsPFNV.length + " tags PFNV";
    }
    
    
    
}

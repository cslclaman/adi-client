/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.tags;

import com.adi.data.entity.AdiTag;
import com.adi.instance.model.Source;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Caique
 */
public class AdiTagsModel {
//    0º (ADI)
    private final AdiTagStruct adi;
//    1º (s#)
    private AdiTagStruct source;
//    2º (r) 
    private AdiTagStruct rating;
//    3º (a)
    private List<AdiTagStruct> artists;
//    4º (c)
    private List<AdiTagStruct> copyrights;
//    5º (np)#
    private AdiTagStruct personaCount;
//    6º (p)
    private List<AdiTagStruct> personas;
//    7º (i)
    private List<AdiTagStruct> items;
    private List<AdiTagStruct> itemsReduced;
//    8º (h#)
    //private List<AdiTag> groups;
//    9º (xg)
    private List<AdiTagStruct> errors;
    
    private List<AdiTagStruct> tagList;
    
    public AdiTagsModel(){
        tagList = new LinkedList<>();
        adi = new AdiTagStruct("ADI");
        artists = new LinkedList<>();
        copyrights = new LinkedList<>();
        personas = new LinkedList<>();
        personaCount = new AdiTagStruct("np", "0");
        items = new LinkedList<>();
        itemsReduced = new LinkedList<>();
        errors = new LinkedList<>();
    }
    
    public void setSource(String sourceId, String postId){
        source = new AdiTagStruct("s", sourceId, postId);
    }
    
    public void setSource(Source s, String postId){
        source = new AdiTagStruct("s", s.getId(), postId);
    }
    
    public void setSource(Source s, int postId){
        source = new AdiTagStruct("s", s.getId(), String.valueOf(postId));
    }
    
    public String getSourceTag(){
        return source.toString();
    }
    
    public String getSourceId(){
        return source.getParameter();
    }
    
    public String getSourcePost(){
        return source.getValue();
    }
    
    public void setRating(String adiRating){
        rating = new AdiTagStruct("r", adiRating);
    }
    
    public String getRatingTag(){
        return rating.toString();
    }
    
    public String getRating(){
        return rating.getValue();
    }
    
    private void addSomething(AdiTagStruct a, List<AdiTagStruct> l){
        if (!l.contains(a)){
            l.add(a);
            Collections.sort(l, (o1, o2) -> {
                return o1.getValue().compareTo(o2.getValue());
            });
        }
    }
    
    private String[] getSomethingTags(List<AdiTagStruct> l){
        String[] a = new String[l.size()];
        int c = 0;
        for (AdiTagStruct t : l){
            a[c++] = t.toString();
        }
        return a;
    }
    
    private String getSomethingString(List<AdiTagStruct> l){
        StringBuilder sb = new StringBuilder();
        for (AdiTagStruct t : l){
            sb.append(t.toString()).append(" ");
        }
        return sb.toString().trim();
    }
    
    public void addArtist(String tag){
        addSomething(new AdiTagStruct("a", tag), artists);
    }
    
    public String[] getArtistTags(){
        return getSomethingTags(artists);
    }
    
    public String getArtistString(){
        return getSomethingString(artists);
    }
    
    public void addCopyright(String tag){
        addSomething(new AdiTagStruct("c", tag), copyrights);
    }
    
    public String[] getCopyrightTags(){
        return getSomethingTags(copyrights);
    }
    
    public String getCopyrightString(){
        return getSomethingString(copyrights);
    }
    
    public void setPersonaCount(String count){
        personaCount = new AdiTagStruct("np", count);
    }
    
    public void setPersonaCount(int np){
        personaCount = new AdiTagStruct("np", String.valueOf(np));
    }
    
    public String getPersonaCountTag(){
        return personaCount.toString();
    }
    
    public String getPersonaCount(){
        return personaCount.getValue();
    }
    
    public void addPersona(String tag){
        addSomething(new AdiTagStruct("p", tag), personas);
        personaCount = new AdiTagStruct("np", String.valueOf(personas.size()));
    }
    
    public String[] getPersonaTags(){
        return getSomethingTags(personas);
    }
    
    public String getPersonaString(){
        return getSomethingString(personas);
    }
    
    public void addItem(String tag){
        AdiTagStruct at = new AdiTagStruct("i", tag);
        addSomething(at, items);
    }
    
    public void addItem(int cat, String tag){
        AdiTagStruct at = new AdiTagStruct("i", tag);
        addSomething(at, items);
        if (cat == 2){
            addSomething(at, itemsReduced);
        }
    }
    
    public String[] getItemTags(){
        return getSomethingTags(items);
    }
    
    public String getItemString(){
        return getSomethingString(items);
    }
                
    public void addError(String type){
        addSomething(new AdiTagStruct("x", type), errors);
    }
    
    public String[] getErrorTags(){
        return getSomethingTags(errors);
    }
    
    public String getErrorString(){
        return getSomethingString(errors);
    }
    
    public void addAdiTag(AdiTag tag){
        switch (tag.getType()){
            case AdiTag.TIPO_ARTISTA:
                addArtist(tag.getTag());
                break;
            case AdiTag.TIPO_SERIE:
                addCopyright(tag.getTag());
                break;
            case AdiTag.TIPO_PERSONAGEM:
                addPersona(tag.getTag());
                break;
            case AdiTag.TIPO_ITEM:
                addItem(tag.getTag());
                break;
            default:
                break;
        }
    }
    
    private List<AdiTagStruct> getTagList(boolean reloadTagList){
        return getTagList(reloadTagList, "anpi");
    }
    
    private List<AdiTagStruct> getTagList(boolean reloadTagList, String add){
        List<AdiTagStruct> tl = new LinkedList<>();
        add = add.toLowerCase();
        tl.add(adi);
        tl.add(source);
        tl.add(rating);
        if (add.contains("a")){
            tl.addAll(artists);
        }
        tl.addAll(copyrights);
        if (add.contains("n")){
            tl.add(personaCount);
        }
        if (add.contains("p")){
            tl.addAll(personas);
        }
        if (add.contains("i")){
            tl.addAll(items);
        } else {
            if (add.contains("r")){
                tl.addAll(itemsReduced);
            }
        }
        tl.addAll(errors);
        if (reloadTagList){
            tagList = tl;
        }
        return tl;
    }
    
    public String[] getTagArray(){
        if (tagList.isEmpty()) getTagList(true);
        return getSomethingTags(tagList);
    }
    
    public String getTagString(){
        if (tagList.isEmpty()) getTagList(true);
        return getSomethingString(tagList);
    }
    
    public String getTagString(int length){
        String ts = getTagString();
        String[] add = new String[]{"anpr","anr","an","a",""};
        int p = 0;
        while (ts.length() > length && p < add.length){
            ts = getSomethingString(getTagList(false, add[p++]));
        } 
        return ts;
    }
    
    private class AdiTagStruct{
        private final String identifier;
        private final String parameter;
        private final String value;

        /**
         * (idenfifier)
         * - ex. (ADI)
         * @param identifier
         */
        public AdiTagStruct(String identifier) {
            this.identifier = identifier;
            this.parameter = "";
            this.value = "";
        }
        
        /**
         * (identifier)value
 - ex. (r)tlb (a)asanagi (c)touhou (np)1 (p)flandre_scarlet (i)vampire (x)g
         * @param identifier
         * @param atribute 
         */
        public AdiTagStruct(String identifier, String atribute) {
            this.identifier = identifier;
            this.parameter = "";
            this.value = atribute;
        }

        /**
         * (identifier parameter)value 
 - ex. (sd)678530 (fc)x
         * @param identifier
         * @param parameter
         * @param atribute 
         */
        public AdiTagStruct(String identifier, String parameter, String atribute) {
            this.identifier = identifier;
            this.parameter = parameter;
            this.value = atribute;
        }
        
        @Override
        public String toString(){
            return ("(" + identifier + parameter + ")" + value);
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getParameter() {
            return parameter;
        }

        public String getValue() {
            return value;
        }
    }
    
    
}

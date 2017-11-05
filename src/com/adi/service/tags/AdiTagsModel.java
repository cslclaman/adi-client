/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.tags;

import com.adi.model.data.AdiTag;
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
    private final AdiTag adi;
//    1º (s#)
    private AdiTag source;
//    2º (r) 
    private AdiTag rating;
//    3º (a)
    private List<AdiTag> artists;
//    4º (c)
    private List<AdiTag> copyrights;
//    5º (np)#
    private AdiTag personaCount;
//    6º (p)
    private List<AdiTag> personas;
//    7º (i)
    private List<AdiTag> items;
    private List<AdiTag> itemsReduced;
//    8º (h#)
    //private List<AdiTag> groups;
//    9º (xg)
    private List<AdiTag> errors;
    
    private List<AdiTag> tagList;
    
    public AdiTagsModel(){
        tagList = new LinkedList<>();
        adi = new AdiTag("ADI");
        artists = new LinkedList<>();
        copyrights = new LinkedList<>();
        personas = new LinkedList<>();
        personaCount = new AdiTag("np", "0");
        items = new LinkedList<>();
        itemsReduced = new LinkedList<>();
        errors = new LinkedList<>();
    }
    
    public void setSource(AdiTag tag){
        source = tag;
    }
    
    public void setSource(String sourceId, String postId){
        source = new AdiTag("s", sourceId, postId);
    }
    
    public void setSource(Source s, String postId){
        source = new AdiTag("s", s.getId(), postId);
    }
    
    public void setSource(Source s, int postId){
        source = new AdiTag("s", s.getId(), String.valueOf(postId));
    }
    
    public String getSourceTag(){
        return source.toString();
    }
    
    public String getSourceId(){
        return source.getParameter();
    }
    
    public String getSourcePost(){
        return source.getTag();
    }
    
    public void setRating(String adiRating){
        rating = new AdiTag("r", adiRating);
    }
    
    public String getRatingTag(){
        return rating.toString();
    }
    
    public String getRating(){
        return rating.getTag();
    }
    
    private void addSomething(AdiTag a, List<AdiTag> l){
        if (!l.contains(a)){
            l.add(a);
            Collections.sort(l, (o1, o2) -> {
                return o1.getTag().compareTo(o2.getTag());
            });
        }
    }
    
    private String[] getSomethingTags(List<AdiTag> l){
        String[] a = new String[l.size()];
        int c = 0;
        for (AdiTag t : l){
            a[c++] = t.toString();
        }
        return a;
    }
    
    private String getSomethingString(List<AdiTag> l){
        StringBuilder sb = new StringBuilder();
        for (AdiTag t : l){
            sb.append(t.toString()).append(" ");
        }
        return sb.toString().trim();
    }
    
    public void addArtist(String tag){
        addSomething(new AdiTag("a", tag), artists);
    }
    
    public void addArtist(AdiTag tag){
        addSomething(tag, artists);
    }
    
    public String[] getArtistTags(){
        return getSomethingTags(artists);
    }
    
    public String getArtistString(){
        return getSomethingString(artists);
    }
    
    public void addCopyright(String tag){
        addSomething(new AdiTag("c", tag), copyrights);
    }
    
    public void addCopyright(AdiTag tag){
        addSomething(tag, copyrights);
    }
    
    public String[] getCopyrightTags(){
        return getSomethingTags(copyrights);
    }
    
    public String getCopyrightString(){
        return getSomethingString(copyrights);
    }
    
    public void setPersonaCount(String count){
        personaCount = new AdiTag("np", count);
    }
    
    public void setPersonaCount(int np){
        personaCount = new AdiTag("np", String.valueOf(np));
    }
    
    public String getPersonaCountTag(){
        return personaCount.toString();
    }
    
    public String getPersonaCount(){
        return personaCount.getTag();
    }
    
    public void addPersona(String tag){
        addSomething(new AdiTag("p", tag), personas);
        personaCount = new AdiTag("np", String.valueOf(personas.size()));
    }
    
    public void addPersona(AdiTag tag){
        addSomething(tag, personas);
        personaCount = new AdiTag("np", String.valueOf(personas.size()));
    }
    
    public String[] getPersonaTags(){
        return getSomethingTags(personas);
    }
    
    public String getPersonaString(){
        return getSomethingString(personas);
    }
    
    public void addItem(String tag){
        addSomething(new AdiTag("i", tag), items);
    }
    
    public void addItem(AdiTag tag){
        addSomething(tag, items);
    }
    
    public void addItem(int cat, String tag){
        AdiTag at = new AdiTag("i", tag);
        addSomething(at, items);
        if (cat == 2){
            addSomething(at, itemsReduced);
        }
    }
    
    public void addItem(int cat, AdiTag tag){
        addSomething(tag, items);
        if (cat == 2){
            addSomething(tag, itemsReduced);
        }
    }
    
    public String[] getItemTags(){
        return getSomethingTags(items);
    }
    
    public String getItemString(){
        return getSomethingString(items);
    }
                
    public void addError(String type){
        addSomething(new AdiTag("x", type), errors);
    }
    
    public void addError(AdiTag tag){
        addSomething(tag, errors);
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
                addArtist(tag);
                break;
            case AdiTag.TIPO_SERIE:
                addCopyright(tag);
                break;
            case AdiTag.TIPO_PERSONAGEM:
                addPersona(tag);
                break;
            case AdiTag.TIPO_ITEM:
                addItem(tag);
                break;
            default:
                break;
        }
    }
    
    private List<AdiTag> getTagList(boolean reloadTagList){
        return getTagList(reloadTagList, "anpi");
    }
    
    private List<AdiTag> getTagList(boolean reloadTagList, String add){
        List<AdiTag> tl = new LinkedList<>();
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
}

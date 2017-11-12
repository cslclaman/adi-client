/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.tags;

import com.adi.model.data.AdiTag;
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
    
    public AdiTagsModel(String tagString){
        tagList = new LinkedList<>();
        artists = new LinkedList<>();
        copyrights = new LinkedList<>();
        personas = new LinkedList<>();
        items = new LinkedList<>();
        itemsReduced = new LinkedList<>();
        errors = new LinkedList<>();
        adi = new AdiTag("ADI");
        personaCount = new AdiTag("np", "0");
        rating = new AdiTag("r", "qac");
        
        String[] tags = tagString.split(" ");
        for (String name : tags){
            name = name.trim();
            if (AdiTag.isValid(name)){
                addAdiTag(new AdiTag(name));
            }
        }
        getTagList(true);
    }
    
    public void addAdiTag(AdiTag tag){
        switch (tag.getTypeIdent()){
            case "s":
                source = tag;
                break;
            case "r":
                rating = tag;
                break;
            case "a":
                if (!artists.contains(tag)){
                    artists.add(tag);
                }
                break;
            case "c":
                if (!copyrights.contains(tag)){
                    copyrights.add(tag);
                }
                break;
            case "n":
                switch(tag.getTypeParameter()){
                    case "p":
                        personaCount = tag;
                        break;
                    default:
                        break;
                }
                break;
            case "p":
                if (!personas.contains(tag)){
                    personas.add(tag);
                    personaCount.setTag(String.valueOf(personas.size()));
                }
                break;
            case "i":
                if (!items.contains(tag)){
                    items.add(tag);
                }
                break;
            case "x":
                if (!errors.contains(tag)){
                    errors.add(tag);
                }
                break;
            default:
                break;
        }
    }
    
    public void setSource(String sourceId, String postId){
        source = new AdiTag("s", sourceId, postId);
    }
    
    public void setSource(String sourceId, int postId){
        source = new AdiTag("s", sourceId, String.valueOf(postId));
    }
    
    public AdiTag getSource(){
        return source;
    }
    
    public void setRating(String adiRating){
        rating = new AdiTag("r", adiRating);
    }
    
    public AdiTag getRating(){
        return rating;
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
    
    public String getArtistString(){
        return getSomethingString(artists);
    }
    
    public void addCopyright(String tag){
        addSomething(new AdiTag("c", tag), copyrights);
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
    
    public AdiTag getPersonaCount(){
        return personaCount;
    }
    
    public void addPersona(String tag){
        addSomething(new AdiTag("p", tag), personas);
        
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
    
    public String getItemString(){
        return getSomethingString(items);
    }
                
    public void addError(String type){
        addSomething(new AdiTag("x", type), errors);
    }
    
    public String getErrorString(){
        return getSomethingString(errors);
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

/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.function;

/**
 *
 * @author Caique
 */
public class StringTag {
    /**
     * Remove caracteres especiais de URLs e substitui pelo código HTML correspondente.
     * <ul>
     * <li>""" (aspas duplas) substituído por "%22"</li>
     * <li>"#" substituído por "%23"</li>
     * <li>"%" substituído por "%25"</li>
     * <li>"&" substituído por "%26"</li>
     * <li>"*" substituído por "%2A"</li>
     * <li>"+" substituído por "%2B"</li>
     * <li>";" substituído por "%3B"</li>
     * </ul>
     * Note que outros símbolos são aceitos pelos sistemas "Booru" e não são substituídos.
     * @param tag Tag original (ex.: "chuuta_(+14)" )
     * @return tag convertida (ex.: "chuuta_(%2B14)")
     */
    public static String urlify (String tag){
        String url = tag;
        //% => %25
        while (url.contains("%"))
            url = url.replace("%", "%25");
        //" => %22
        while (url.contains("\""))
            url = url.replace("\"", "%22");
        //# => %23
        while (url.contains("#"))
            url = url.replace("#", "%23");
        //& => %26
        while (url.contains("&"))
            url = url.replace("&", "%26");
        //* => %2A
        while (url.contains("*"))
            url = url.replace("*", "%2A");
        //+ => %2B
        while (url.contains("+"))
            url = url.replace("+", "%2B");
        //; => %3B
        while (url.contains(";"))
            url = url.replace(";", "%3B");
        
        return url;
    }
}

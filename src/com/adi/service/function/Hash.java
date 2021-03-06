/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.function;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe manipuladora de funções HASH e Checksum
 * @author Caique
 */
public abstract class Hash {
    private static MessageDigest md;
    
    /**
     * Retorna hash MD5 a partir de um arquivo.
     * @param file Arquivo de entrada
     * @return String com hash calculada ou String vazia em caso de erro.
     */
    public static String md5(File file) {
        String hash = "";
        try {
            hash = md5frombytes(Files.readAllBytes(file.toPath()));
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
        return hash;
    }
    
    /**
     * Retorna hash MD5 a partir de uma String.
     * @param string String de entrada
     * @return String com hash calculada ou String vazia em caso de erro.
     */
    public static String md5(String string) {
        return md5frombytes(string.getBytes());
    }
    
    private static String md5frombytes(byte[] in){
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.toString());
            return "";
        }
        
        BigInteger result = new BigInteger(1, md.digest(in));
 
        return String.format("%032x", result);
    }
}

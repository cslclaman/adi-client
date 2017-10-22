/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.exception;

/**
 * Exceção lançada quando um parâmetro/atributo obrigatório não foi definido corretamente. 
 * @author Caique
 */
public class UnspecifiedParameterException extends Throwable {

    public UnspecifiedParameterException() {
    }

    public UnspecifiedParameterException(String message) {
        super(message);
    }

    public UnspecifiedParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnspecifiedParameterException(Throwable cause) {
        super(cause);
    }

    public UnspecifiedParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}

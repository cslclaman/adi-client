/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.info;

import com.adi.data.entity.AdiTag;

/**
 *
 * @author Caique
 */
public class Personagem extends Info {
    private Serie serie;
    private Artista artista;

    public Personagem(AdiTag adiTag) {
        super(adiTag);
        artista = null;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}

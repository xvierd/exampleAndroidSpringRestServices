package com.ucla.models;

/**
 * Created by xavier on 04/07/15.
 */
public class ListCartelera {
    protected int imgCartelera;
    protected String tvTituloCartelera;
    protected String tvCartelera;
    protected long id;

    public ListCartelera(int imgCarteleraPrivada, String tvTituloCarteleraPrivada, String tvCarteleraPrivada, long id) {
        this.imgCartelera = imgCarteleraPrivada;
        this.tvTituloCartelera = tvTituloCarteleraPrivada;
        this.tvCartelera = tvCarteleraPrivada;
        this.id = id;
    }

    public int getImgCartelera() {
        return imgCartelera;
    }

    public void setImgCartelera(int imgCartelera) {
        this.imgCartelera = imgCartelera;
    }

    public String getTvTituloCartelera() {
        return tvTituloCartelera;
    }

    public void setTvTituloCartelera(String tvTituloCartelera) {
        this.tvTituloCartelera = tvTituloCartelera;
    }

    public String getTvCartelera() {
        return tvCartelera;
    }

    public void setTvCartelera(String tvCartelera) {
        this.tvCartelera = tvCartelera;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

package com.ucla.adapter;

import com.ucla.models.Noticia;

import java.util.ArrayList;

/**
 * Created by xaviergutierrez on 28/2/16.
 */
public class NoticiasPublicas {
    ArrayList<Noticia> noticias;

    public NoticiasPublicas() {
    }

    public NoticiasPublicas(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }

    public ArrayList<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }
}

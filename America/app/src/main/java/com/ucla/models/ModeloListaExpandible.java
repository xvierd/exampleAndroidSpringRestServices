package com.ucla.models;

import com.ucla.america.R;

/**
 * Created by dixon on 10/03/16.
 */

public class ModeloListaExpandible {
    private String titulo;
    private String subTitulo;
    private String contenido;
    private int icono;

    public ModeloListaExpandible(String titulo, String subTitulo,String contenido) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.icono = R.drawable.ic_action_event;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getSubTitulo() {
        return this.subTitulo;
    }

    public String getContenido() {
        return contenido;
    }

    public int getIcono() {
        return icono;
    }
}

package com.ucla.models;

/**
 * Created by dixon on 21/06/15.
 */
public class ModeloLista {
    private String texto;
    private int icono;

    public ModeloLista(String texto, int icono) {
        this.texto = texto;
        this.icono = icono;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

}

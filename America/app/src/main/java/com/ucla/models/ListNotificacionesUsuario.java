package com.ucla.models;

/**
 * Created by xavier on 09/06/15.
 */
public class ListNotificacionesUsuario {
    protected int imagen;
    protected String texto;
    protected long id;

    public ListNotificacionesUsuario(int imagen, String texto, long id) {
        this.imagen = imagen;
        this.texto = texto;
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

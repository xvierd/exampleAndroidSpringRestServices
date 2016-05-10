package com.ucla.models;

/**
 * Created by xavier on 09/06/15.
 */
public class ListMenu {
    protected int imagen;
    protected String texo;
    protected long id;

    public ListMenu(int imagen, String texo, long id) {
        this.imagen = imagen;
        this.texo = texo;
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTexo() {
        return texo;
    }

    public void setTexo(String texo) {
        this.texo = texo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

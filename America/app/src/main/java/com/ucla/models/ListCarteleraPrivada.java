package com.ucla.models;

/**
 * Created by xavier on 04/07/15.
 */
public class ListCarteleraPrivada extends ListCartelera {

    String tvCarteleraPrivadaPrueba;

    public ListCarteleraPrivada(int imgCarteleraPrivada, String tvTituloCarteleraPrivada, String tvCarteleraPrivada, long id, String tvCarteleraPrivadaPrueba) {
        super(imgCarteleraPrivada, tvTituloCarteleraPrivada, tvCarteleraPrivada, id);
        this.tvCarteleraPrivadaPrueba = tvCarteleraPrivadaPrueba;
    }

    public String getTvCarteleraPrivadaPrueba() {
        return tvCarteleraPrivadaPrueba;
    }

    public void setTvCarteleraPrivadaPrueba(String tvCarteleraPrivadaPrueba) {
        this.tvCarteleraPrivadaPrueba = tvCarteleraPrivadaPrueba;
    }
}

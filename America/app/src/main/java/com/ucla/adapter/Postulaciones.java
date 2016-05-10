package com.ucla.adapter;

import com.ucla.models.Postulacion;

import java.util.ArrayList;


public class Postulaciones {

    private ArrayList<Postulacion> postulaciones = new ArrayList<Postulacion>();

    public Postulaciones(ArrayList<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public ArrayList<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(ArrayList<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }
}

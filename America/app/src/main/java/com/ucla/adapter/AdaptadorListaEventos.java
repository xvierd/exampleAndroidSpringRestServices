package com.ucla.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucla.america.R;
import com.ucla.models.Evento;

import java.util.ArrayList;

/**
 * Created by dixon on 10/03/16.
 */

public class AdaptadorListaEventos extends BaseAdapter {
    private Activity context;
    private ArrayList<Evento> datos;

    public AdaptadorListaEventos(Activity context,ArrayList<Evento> datos){
        this.context = context;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int i) {
        return datos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi =convertView;
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_event,null);
        }

        Evento mDatos = datos.get(position);
        TextView titulo = (TextView)vi.findViewById(R.id.tvTituloEvento);
        TextView descripcion = (TextView)vi.findViewById(R.id.tvDescripcionEvento);
        TextView fecha = (TextView)vi.findViewById(R.id.tvFechaEvento);

        titulo.setText(mDatos.getNombre());
        descripcion.setText(mDatos.getDescripcion());
        fecha.setText(mDatos.getFechaInicio());

        return  vi;
    }
}
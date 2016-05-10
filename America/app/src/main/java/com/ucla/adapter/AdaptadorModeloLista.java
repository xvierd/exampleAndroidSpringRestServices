package com.ucla.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucla.america.R;
import com.ucla.models.ModeloLista;

import java.util.ArrayList;


/**
 * Created by dixon on 21/06/15.
 */
public class AdaptadorModeloLista extends BaseAdapter{
    private Activity context;
    private ArrayList<ModeloLista> datos;

    public AdaptadorModeloLista(Activity context, ArrayList<ModeloLista> datos) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView.
        View vi = convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_item,null);
        }

        ModeloLista mDatos = datos.get(position);
        TextView texto = (TextView)vi.findViewById(R.id.contenidoLista);
        ImageView img = (ImageView)vi.findViewById(R.id.imgLista);

        texto.setText(mDatos.getTexto());
        img.setImageResource(mDatos.getIcono());

        return vi;

    }

}

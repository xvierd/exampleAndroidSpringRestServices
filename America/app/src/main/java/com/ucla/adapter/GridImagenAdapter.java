package com.ucla.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ucla.america.R;
import com.ucla.models.Foto;

import java.util.ArrayList;

/**
 * Created by xavier on 15/06/15.
 */
public class GridImagenAdapter extends BaseAdapter {
    public Context context;
    private ArrayList<Foto> datos;



    public GridImagenAdapter(Context context,  ArrayList<Foto> datos) {
        this.context = context;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.foto_item_galeria,null);
        }

        Foto foto = datos.get(position);
        ImageView img = (ImageView)vi.findViewById(R.id.imgGaleria);

        Glide.with(context.getApplicationContext()).load(foto.getUrl()).placeholder(R.drawable.america).error(R.drawable.america).into(img);

        return vi;

    }

}

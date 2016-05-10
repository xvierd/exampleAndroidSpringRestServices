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
import com.ucla.models.ListNotificacionesUsuario;

import java.util.ArrayList;

/**
 * Created by xavier on 09/06/15.
 */
public class ListNotificacionesUsuarioAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<ListNotificacionesUsuario> items;

    public ListNotificacionesUsuarioAdapter(Activity activity, ArrayList<ListNotificacionesUsuario> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(items.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_notificaciones_usuario,null);
        }

        ListNotificacionesUsuario listNotificacionesUsuario = items.get(position);
        TextView txtNotificacionesUsuario = (TextView)vi.findViewById(R.id.txtNotificacionesUsuario);
        ImageView imgNotificacionesUsuario = (ImageView)vi.findViewById(R.id.imgNotificacionesUsuario);
        txtNotificacionesUsuario.setText(listNotificacionesUsuario.getTexto());
        imgNotificacionesUsuario.setImageResource(listNotificacionesUsuario.getImagen());


        return vi;
    }
}

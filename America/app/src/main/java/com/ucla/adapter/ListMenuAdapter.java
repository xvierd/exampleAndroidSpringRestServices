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
import com.ucla.models.ListMenu;

import java.util.ArrayList;


/**
 * Created by xavier on 09/06/15.
 */
public class ListMenuAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<ListMenu> items;

    public ListMenuAdapter(Activity activity, ArrayList<ListMenu> items) {
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
            vi = inflater.inflate(R.layout.list_menu,null);
        }
        ListMenu listMenu = items.get(position);
        TextView texNombre = (TextView)vi.findViewById(R.id.txtMenu);
        ImageView imgMenu = (ImageView)vi.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(listMenu.getImagen());
        texNombre.setText(listMenu.getTexo());
        return vi;

    }
}

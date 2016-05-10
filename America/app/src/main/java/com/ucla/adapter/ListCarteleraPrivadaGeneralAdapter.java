package com.ucla.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ucla.america.R;
import com.ucla.models.Noticia;

import java.util.ArrayList;

/**
 * Created by xavier on 04/07/15.
 */

public class ListCarteleraPrivadaGeneralAdapter extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    Noticia tempValues=null;


    /*************  CustomAdapter Constructor *****************/
    public ListCarteleraPrivadaGeneralAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView tvTituloCarteleraPrivada;
        public TextView tvCarteleraPrivada;
        public ImageView imgCarteleraPrivada;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate business_item.xmlem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_cartelera_privada, null);

            /****** View Holder Object to contain business_items_item.xml file elements ******/

            holder = new ViewHolder();
            holder.tvTituloCarteleraPrivada = (TextView) vi.findViewById(R.id.tvTituloCarteleraPrivada);
            holder.tvCarteleraPrivada = (TextView) vi.findViewById(R.id.tvCarteleraPrivada);
            holder.imgCarteleraPrivada = (ImageView)vi.findViewById(R.id.imgCarteleraPrivada);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.tvCarteleraPrivada.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (Noticia) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.tvTituloCarteleraPrivada.setText( tempValues.getTitulo() );
            holder.tvCarteleraPrivada.setText(String.valueOf(tempValues.getDescripcion()));
            Glide.with(activity.getApplicationContext()).load(tempValues.getFoto()).placeholder(R.drawable.noticia_defecto).error(R.drawable.noticia_defecto).into(holder.imgCarteleraPrivada);



        }
        return vi;
    }
}

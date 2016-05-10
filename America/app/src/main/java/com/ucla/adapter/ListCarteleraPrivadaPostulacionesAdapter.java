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
import com.ucla.models.Postulacion;

import java.util.ArrayList;

/**
 * Created by xavier on 04/07/15.
 */

public class ListCarteleraPrivadaPostulacionesAdapter extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    Postulacion tempValues=null;


    /*************  CustomAdapter Constructor *****************/
    public ListCarteleraPrivadaPostulacionesAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data==null)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
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
            tempValues = (Postulacion) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.tvTituloCarteleraPrivada.setText( tempValues.getPostulado().getPersona().getNombre()+" "+tempValues.getPostulado().getPersona().getApellido() );
            holder.tvCarteleraPrivada.setText(String.valueOf(tempValues.getMotivoPostulacion().getDescripcion()));

            Glide.with(activity.getApplicationContext()).load(tempValues.getPostulado().getPersona().getFoto()).placeholder(R.drawable.perfil_defecto).error(R.drawable.perfil_defecto).into(holder.imgCarteleraPrivada);


        }
        return vi;
    }
}

package com.ucla.models;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucla.america.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by xavier on 03/07/15.
 */
@EViewGroup(R.layout.list_cartelera_publica)
public class ListCarteleraPublicaItemView extends LinearLayout {
    @ViewById
    public ImageView imgCarteleraPublica;
    @ViewById
    TextView tvTituloCartelera;
    @ViewById
    TextView  tvCartelera;

    public ListCarteleraPublicaItemView(Context context) {
        super(context);
    }

    public void bind(ListCarteleraPublica carteleraPublica) {
        imgCarteleraPublica.setImageResource(carteleraPublica.getImgCartelera());
        tvTituloCartelera.setText(carteleraPublica.getTvTituloCartelera());
        tvCartelera.setText(carteleraPublica.getTvCartelera());
    }
}

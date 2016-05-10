package com.ucla.america;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;


public class Fragment_registrar_eventualidades extends Fragment implements OnClickListener {

    private String mParam1;
    private String mParam2;
    private Spinner spinnerInstalaciones;
    private Spinner spinnerEventos;
    private FloatingActionButton floatingActionButton;
    private EditText editextCed;
    private String cedC = "";
    private String ced = "19104634";

    private LinearLayout layout = null;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private View popupView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static Fragment_registrar_eventualidades newInstance(String param1, String param2) {
        Fragment_registrar_eventualidades fragment = new Fragment_registrar_eventualidades();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View vista = inflater.inflate(R.layout.fragment_registrar_eventualidades, container, false);


/*
        Spinner spinnerInstalaciones = (Spinner) vista.findViewById(R.id.spinnerInstalacionesEventualidades);
		ArrayAdapter<CharSequence> adapterI = ArrayAdapter.createFromResource(getActivity(),R.array.ListaInstalacionesStatic, android.R.layout.simple_spinner_item);
		adapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerInstalaciones.setAdapter(adapterI);
		//Fin spinner instalaciones
*/
        //Spinner Eventos
/*		Spinner spinnerEventos = (Spinner) vista.findViewById(R.id.spinnerEventosEventualidades);
/*		ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(getActivity(),R.array.ListaEventosEventualidades, android.R.layout.simple_spinner_item);
		adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerEventos.setAdapter(adapterE);
		//Fin spinner Eventos
*/

        //boton que llama al popup
        ImageButton btn = (ImageButton) vista.findViewById(R.id.btnBuscarSocio);
        btn.setOnClickListener(this);

        floatingActionButton = (FloatingActionButton) vista.findViewById(R.id.btnCancelarEventualidad);
        floatingActionButton.setOnClickListener(this);
        spinnerInstalaciones = (Spinner) vista.findViewById(R.id.spinnerInstalacionesEventualidades);
        spinnerEventos = (Spinner) vista.findViewById(R.id.spinnerEventosEventualidades);
        editextCed = (EditText) vista.findViewById(R.id.editTextCodigoSocioEventualidades);


        //Inicio Popup
        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup_buscar_socio_eventualidad, null);


        popupWindow = new PopupWindow(popupView, android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        Drawable d = new ColorDrawable(Color.DKGRAY);
        d.setAlpha(240);
        popupWindow.setBackgroundDrawable(d);


        //fin popu


        return vista;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarSocio:
                cedC = editextCed.getText().toString();
                if (cedC.equals(ced)) {
                    popupWindow.dismiss();
                    popupWindow.setFocusable(true);// esto es para que el popupp tenga foco y se edite
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                    ArrayAdapter<CharSequence> adapterI = ArrayAdapter.createFromResource(getActivity(), R.array.ListaInstalacionesStatic, android.R.layout.simple_spinner_item);
                    adapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerInstalaciones.setAdapter(adapterI);


                    //Spinner Eventos
                    ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(getActivity(), R.array.ListaEventosEventualidades, android.R.layout.simple_spinner_item);
                    adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEventos.setAdapter(adapterE);


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "NO REGISTRADO", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancelarEventualidad:
                Toast.makeText(getActivity().getApplicationContext(), " Enviada", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

                break;

        }
    }


}

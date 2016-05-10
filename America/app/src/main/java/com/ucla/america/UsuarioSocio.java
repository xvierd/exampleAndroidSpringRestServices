package com.ucla.america;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ucla.adapter.AdaptadorModeloLista;
import com.ucla.models.FileOpen;
import com.ucla.models.ModeloLista;
import com.ucla.models.RoundImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuarioSocio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsuarioSocio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioSocio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listNotificacionesUsuario;
    private Button btnModificarPerfilUsuario;
    //popup
    private LinearLayout layout = null;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private View popupView;

    //fin popup

    private ArrayList<ModeloLista> datos;
    private ListView lista;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ImageView imgPerfiUsuario;
    RoundImage roundedImage;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsuarioSocio.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuarioSocio newInstance(String param1, String param2) {
        UsuarioSocio fragment = new UsuarioSocio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UsuarioSocio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vistaUsuarioSocio = inflater.inflate(R.layout.fragment_usuario_socio, container, false);
        //this.listNotificacionesUsuario = (ListView)vistaUsuarioSocio.findViewById(R.id.listNotificacionesUsuario);
        btnModificarPerfilUsuario = (Button) vistaUsuarioSocio.findViewById(R.id.btnModificarPerfilUsuario);

        lista = (ListView) vistaUsuarioSocio.findViewById(R.id.listNotificacionesUsuario);
        datos = new ArrayList<ModeloLista>();
        rellenarArrayList();
        Log.i("datos", String.valueOf(datos.size()));
        lista.setAdapter(new AdaptadorModeloLista(getActivity(), datos));

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                FragmentManager fragmentManager = null;
                fragmentManager = getActivity().getSupportFragmentManager();
                if (position == 0) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, Evento.newInstance("hola", "prueba"))
                            .commit();
                } else if (position == 1) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, OpinionPostulacion.newInstance("hola", "prueba"))
                            .commit();
                }
            }

        });



        /*long id = 1;
        ArrayList<ListNotificacionesUsuario> datosLista = new ArrayList<ListNotificacionesUsuario>();
        ListNotificacionesUsuario listNotificacionesUsuario = new ListNotificacionesUsuario(R.drawable.ic_notificacion,"Evento día del padre",id);
        datosLista.add(listNotificacionesUsuario);

        listNotificacionesUsuario = new ListNotificacionesUsuario(R.drawable.ic_notificacion,"Solicitud de alquiler aprobada",id);
        datosLista.add(listNotificacionesUsuario);

        listNotificacionesUsuario = new ListNotificacionesUsuario(R.drawable.ic_notificacion,"Hay una nueva postulacion. Opina",id);
        datosLista.add(listNotificacionesUsuario);

        this.listNotificacionesUsuario.setAdapter(new ListNotificacionesUsuarioAdapter(getActivity() ,datosLista) );
         */
        /*configurando popup
        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup_editar_perfil_usuario,null);

        popupWindow = new PopupWindow(popupView,android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        Drawable d = new ColorDrawable(Color.GRAY);
        d.setAlpha(240);
        popupWindow.setBackgroundDrawable(d);*/
        //fin configuracion popup

        imgPerfiUsuario = (ImageView) vistaUsuarioSocio.findViewById(R.id.imgPerfiUsuario);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.perfil);
        roundedImage = new RoundImage(bm);
        imgPerfiUsuario.setImageDrawable(roundedImage);
        imgPerfiUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myFile = new File("PERFIL.jpg");
                try {
                    FileOpen.openFile(vistaUsuarioSocio.getContext(), myFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        btnModificarPerfilUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int1;
                int1 = new Intent(getActivity(), Modificacion_Perfil_Usuario.class);

                int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(int1);

            }
        });

        return vistaUsuarioSocio;
        //return inflater.inflate(R.layout.fragment_usuario_socio, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void rellenarArrayList() {
        datos.add(new ModeloLista("Evento dia del Padre", R.drawable.ic_notificacion));
        datos.add(new ModeloLista("Hay una nueva postulacion. Opina", R.drawable.ic_notificacion));
        datos.add(new ModeloLista("Solicitud de alquiler aprobada", R.drawable.ic_notificacion));

    }

}

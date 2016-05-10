package com.ucla.america;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ucla.adapter.AdaptadorModeloLista;
import com.ucla.models.ModeloLista;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Evento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Evento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Evento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<ModeloLista> datos;
    private ListView lista;
    private EditText titulo;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Evento.
     */
    // TODO: Rename and change types and number of parameters
    public static Evento newInstance(String param1, String param2) {
        Evento fragment = new Evento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Evento() {
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

        View vista = inflater.inflate(R.layout.fragment_evento, container, false);
        lista = (ListView) vista.findViewById(R.id.listaActividadesEvento);
        titulo = (EditText) vista.findViewById(R.id.txtNombreEvento);
        datos = new ArrayList<ModeloLista>();
        rellenarArrayList();
        lista.setAdapter(new AdaptadorModeloLista(getActivity(), datos));
        titulo.setText(mParam2.toUpperCase());
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                FragmentManager fragmentManager = null;
                fragmentManager = getActivity().getSupportFragmentManager();
                switch (position) {
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, ListaTareas.newInstance(datos.get(position).getTexto().toString(), "prueba"))
                                .commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, ListaTareas.newInstance(datos.get(position).getTexto().toString(), "prueba"))
                                .commit();
                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, ListaTareas.newInstance(datos.get(position).getTexto().toString(), "prueba"))
                                .commit();
                        break;
                    default:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, ListaTareas.newInstance(datos.get(position).getTexto().toString(), "prueba"))
                                .commit();
                        break;
                }


            }
        });
        this.lista.setScrollContainer(false);

        return vista;
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
        datos.add(new ModeloLista("Torneo Dominó", R.drawable.ic_action_event));
        datos.add(new ModeloLista("Karaoke", R.drawable.ic_action_event));
        datos.add(new ModeloLista("Concierto", R.drawable.ic_action_event));
        datos.add(new ModeloLista("Torneo de Softball", R.drawable.ic_action_event));
        datos.add(new ModeloLista("Torneo de Bolas Criollas", R.drawable.ic_action_event));
        datos.add(new ModeloLista("Stand Up", R.drawable.ic_action_event));
    }

}

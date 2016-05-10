package com.ucla.america;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ucla.adapter.AdaptadorModeloCheckLista;
import com.ucla.models.ModeloLista;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaTareas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaTareas extends Fragment {
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
    private TextView titulo;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaTareas newInstance(String param1, String param2) {
        ListaTareas fragment = new ListaTareas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListaTareas() {
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
        View vista = inflater.inflate(R.layout.fragment_lista_tareas, container, false);
        lista = (ListView) vista.findViewById(R.id.listView_Tareas);
        datos = new ArrayList<ModeloLista>();
        rellenarArrayList();
        lista.setAdapter(new AdaptadorModeloCheckLista(getActivity(), datos));
        //titulo = (TextView)vista.findViewById(R.id.listView_Tareas);
        //titulo.setText(mParam1.toUpperCase());
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
        datos.add(new ModeloLista("Mesas", R.drawable.ic_action_star));
        datos.add(new ModeloLista("Jueces", R.drawable.ic_action_star));
        datos.add(new ModeloLista("Marcadores", R.drawable.ic_action_star));
        datos.add(new ModeloLista("Pizarra", R.drawable.ic_action_star));
        datos.add(new ModeloLista("Premio", R.drawable.ic_action_star));
        datos.add(new ModeloLista("Refrigerio", R.drawable.ic_action_star));

    }

}

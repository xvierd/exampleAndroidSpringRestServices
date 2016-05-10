package com.ucla.america;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ucla.adapter.AdaptadorListaEventos;
import com.ucla.models.EstadoEvento;
import com.ucla.models.Evento;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaEventos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaEventos#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_lista_eventos)
public class ListaEventos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Evento> datos;
    @ViewById
    public ListView listView_Eventos;
    private String sIp;
    private String idUser;
    private AdaptadorListaEventos adapterEventos;


    @ViewById
    public ProgressBar progress_bar_eventos;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaEventos.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaEventos newInstance(String param1, String param2) {
        ListaEventos fragment = new ListaEventos_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListaEventos() {
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
        View vista = inflater.inflate(R.layout.fragment_lista_eventos, container, false);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");
        idUser = pref.getString("idUser", "");
        //BUSCAR SERVICIOS
        informacionCarteleraInteres();
        //rellenarArrayList();
        //lista.setAdapter(new AdaptadorListaEventos(getActivity(), datos));


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
        Evento event = new Evento(1, new EstadoEvento(), "Dia del Padre",
                "Descripcion", "FechaINicio", "fechaFin", true, true);
        datos.add(event);
        datos.add(event);
        datos.add(event);
        datos.add(event);
        datos.add(event);
        datos.add(event);
        datos.add(event);
        datos.add(event);
    }

    @UiThread
    void progressBarEventos(boolean status) {
        if (status)
            progress_bar_eventos.setVisibility(View.VISIBLE);
        else
            progress_bar_eventos.setVisibility(View.GONE);
    }

    @Background
    public void informacionCarteleraInteres() {
        progressBarEventos(true);
        String url = "http://" + sIp + ":8000/preferencia/evento/";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        map.add("user_id", idUser);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
        try {
            Evento[] eventos = restTemplate.postForObject(url, requestEntity, Evento[].class);
            this.datos = new ArrayList<Evento>(Arrays.asList(eventos));
            ;
            progressBarEventos(false);
            cargarEventos();
        } catch (Exception e) {
            progressBarEventos(false);
            Log.e("error Interes", e.getMessage());
            message("error de conexion");

        }

    }

    @UiThread
    public void cargarEventos() {
        adapterEventos = new AdaptadorListaEventos(getActivity(), datos);
        listView_Eventos.setAdapter(adapterEventos);
    }

    @UiThread
    public void message(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

}

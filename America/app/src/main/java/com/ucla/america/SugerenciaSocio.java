package com.ucla.america;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ucla.models.TipoSugerencia;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SugerenciaSocio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SugerenciaSocio#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_sugerencia_socio)
public class SugerenciaSocio extends Fragment implements OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton floatingActionButton1;
    private FloatingActionButton floatingActionButton2;
    private ArrayAdapter<CharSequence> adapterS;
    private EditText descripcion;
    private String sIp;
    private String idUser;
    public Spinner tipoSugerencia;
    @ViewById
    public EditText editTextDescripcionSugerencia;
    private ArrayList<TipoSugerencia> tipoSugerencias;
    String[] spinnerArray;
    HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();
    String valueTipoSugerencia;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SugerenciaSocio.
     */
    // TODO: Rename and change types and number of parameters
    public static SugerenciaSocio newInstance(String param1, String param2) {
        SugerenciaSocio fragment = new SugerenciaSocio_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SugerenciaSocio() {
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
        View vistaS = inflater.inflate(R.layout.fragment_sugerencia_socio, container, false);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");
        idUser = pref.getString("idUser", "");
        tipoSugerencia = (Spinner) vistaS.findViewById(R.id.tipoSugerencia);
        tipoSugerencia();
        tipoSugerencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueTipoSugerencia = (String) parent.getAdapter().getItem(position);
                Log.i("spinner+++: ", valueTipoSugerencia + " " + String.valueOf(getIdTipSugerencia()));
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        floatingActionButton1 = (FloatingActionButton) vistaS.findViewById(R.id.btnEnviarSugerencia);
        floatingActionButton1.setOnClickListener(this);
        floatingActionButton2 = (FloatingActionButton) vistaS.findViewById(R.id.btnCancelarSugerencia);
        floatingActionButton2.setOnClickListener(this);
        descripcion = (EditText) vistaS.findViewById(R.id.editTextDescripcionSugerencia);
        return vistaS;


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


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviarSugerencia:
                //sugerencia.setAdapter(adapterS);
                descripcion.setText("");
                Toast.makeText(getActivity().getApplicationContext(), "Enviada", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancelarSugerencia:
                //sugerencia.setAdapter(adapterS);
                descripcion.setText("");

                break;

        }
    }

    @UiThread
    public void cargarSugenrecias() {
        spinnerArray = new String[tipoSugerencias.size()];
        spinnerMap = new HashMap<String, Integer>();
        for (int i = 0; i < tipoSugerencias.size(); i++) {
            spinnerMap.put(tipoSugerencias.get(i).getDescripcion(), tipoSugerencias.get(i).getId());
            spinnerArray[i] = tipoSugerencias.get(i).getDescripcion();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.simple_spinner_item, spinnerArray);
        tipoSugerencia.setAdapter(adapter);
    }

    @Background
    public void tipoSugerencia() {
        String url = "http://" + sIp + ":8000/tipo/sugerencia/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(requestHeaders);
        try {
            TipoSugerencia[] tipoSugerencias = restTemplate.postForObject(url, requestEntity, TipoSugerencia[].class);
            this.tipoSugerencias = new ArrayList<TipoSugerencia>(Arrays.asList(tipoSugerencias));
            cargarSugenrecias();
        } catch (Exception e) {
            Log.e("error TipoOpinion", e.getMessage());
        }

    }

    public long getIdTipSugerencia() {
        return spinnerMap.get(valueTipoSugerencia);
    }

    @UiThread
    public void message(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @UiThread
    public void limpiar() {
        editTextDescripcionSugerencia.setText("");
    }

    @Click
    public void btnEnviarSugerencia() {
        enviarSugerencia();
    }

    @Background
    public void enviarSugerencia() {
        String url = "http://" + sIp + ":8000/enviar/sugerencia/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("user_id", idUser);
        byte[] data = new byte[0];
        try {
            data = editTextDescripcionSugerencia.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.add("descripcion", Base64.encodeToString(data, Base64.DEFAULT));
        map.add("tipoSugerencia", String.valueOf(getIdTipSugerencia()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
        try {
            MessageServicie s = restTemplate.postForObject(url, requestEntity, MessageServicie.class);
            if (s.getMessage() == 201) {
                message("su comentario ha sido enviado");
                limpiar();
            } else if (s.getMessage() == 204) {
                message("usted ya ha comentado sobre el postulado anterior mente");
                limpiar();
            } else if (s.getMessage() == 500)
                message("Disculpe el servidor presenta problemas");
        } catch (Exception e) {
            Log.e("error noticias privadas", e.getMessage());
            message("error de conexion");
        }


    }

}

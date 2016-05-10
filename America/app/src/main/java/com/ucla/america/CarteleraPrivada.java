package com.ucla.america;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ucla.adapter.ListCarteleraPrivadaGeneralAdapter;
import com.ucla.adapter.ListCarteleraPrivadaInteresAdapter;
import com.ucla.adapter.ListCarteleraPrivadaPostulacionesAdapter;
import com.ucla.adapter.NoticiasPublicas;
import com.ucla.models.ListCarteleraPublica;
import com.ucla.models.Noticia;
import com.ucla.models.Postulacion;
import com.ucla.models.TipoOpnion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarteleraPrivada#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_view_cartelera_privada)
public class CarteleraPrivada extends Fragment implements Button.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @ViewById
    public ListView listCarteleraPrivadaInteres;
    @ViewById
    public ListView listCarteleraPrivadaGeneral;
    @ViewById
    public ListView listCarteleraPrivadaPostulaciones;
    ListCarteleraPrivadaInteresAdapter adapterInteres;
    ListCarteleraPrivadaPostulacionesAdapter adapterPostulaciones;
    ListCarteleraPrivadaGeneralAdapter adapterGeneral;
    ArrayList<Noticia> noticias;
    ArrayList<Postulacion> postulaciones;
    ArrayList<Noticia> noticiasIteres;
    List<ListCarteleraPublica> datosLista = new ArrayList<ListCarteleraPublica>();
    private String sIp;
    private String idUser;
    private String fullName;

    @ViewById
    public ProgressBar progress_bar_postulaciones;
    @ViewById
    public ProgressBar progress_bar_cartelera;
    @ViewById
    public ProgressBar progress_bar_intereses;

    //popup
    private LinearLayout layout = null;
    private PopupWindow popupWindow;
    private View popupView;
    private Spinner tipoOpinion;
    private EditText descripcionOpinion;
    private RatingBar ratingBar;
    private Button btnEnviar;
    private Button btnCancelar;
    private TextView tituloPopupCarteleraPrivada;
    //fin popup

    private ArrayList<TipoOpnion> tipoOpnions;
    String[] spinnerArray;
    HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();
    String valueTipoOpinion;
    long idPostulacion;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private OnFragmentInteractionListener mListener;
    ActionBar actionBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarteleraPrivada.
     */
    // TODO: Rename and change types and number of parameters
    public static CarteleraPrivada newInstance(String param1, String param2) {
        CarteleraPrivada fragment = new CarteleraPrivada_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CarteleraPrivada() {
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
        View viewCarteleraPrivada = inflater.inflate(R.layout.fragment_view_cartelera_privada, container, false);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");
        idUser = pref.getString("idUser", "");
        System.out.print("ID USER:: " + idUser);
        fullName = pref.getString("fullName", "");
        Resources res = getResources();

        TabHost tabs = (TabHost) viewCarteleraPrivada.findViewById(android.R.id.tabhost);
        tabs.setup();


        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",
                res.getDrawable(R.drawable.postulados_24));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",
                res.getDrawable(R.drawable.notebook_24));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",
                res.getDrawable(R.drawable.like_24));
        tabs.addTab(spec);


        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
            }
        });

        //fin pesta�as
        informacionCarteleraPrivada();
        informacionCarteleraPostulaciones();
        informacionCarteleraInteres();


        //configurando popup
        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        final LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup_opinion_postulado, null);
        tipoOpinion = (Spinner) popupView.findViewById((R.id.tipoOpinion));

        tipoOpinion();
        ratingBar = (RatingBar) popupView.findViewById((R.id.ratingBar));
        tipoOpinion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueTipoOpinion = (String) parent.getAdapter().getItem(position);
                Log.i("spinner+++: ", valueTipoOpinion + " " + String.valueOf(getIdTipOpinion()));
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        descripcionOpinion = (EditText) popupView.findViewById((R.id.descripcionOpinion));
        btnEnviar = (Button) popupView.findViewById((R.id.btnEnviar));
        btnEnviar.setOnClickListener(this);
        btnCancelar = (Button) popupView.findViewById((R.id.btnCancelar));
        btnCancelar.setOnClickListener(this);
        tituloPopupCarteleraPrivada = (TextView) popupView.findViewById(R.id.tituloPopupCarteleraPrivada);
        popupWindow = new PopupWindow(popupView, android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(240);
        popupWindow.setAnimationStyle(R.style.Base_Animation_AppCompat_DropDownUp);
        popupWindow.setBackgroundDrawable(d);
        //fin configuracion popup

        return viewCarteleraPrivada;

    }

    @AfterViews
    void bindAdapter() {
        this.listCarteleraPrivadaGeneral.setAdapter(adapterGeneral);
        this.listCarteleraPrivadaInteres.setAdapter(adapterInteres);
        this.listCarteleraPrivadaPostulaciones.setAdapter(adapterPostulaciones);
        listCarteleraPrivadaPostulaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Postulacion postulacion = (Postulacion) parent.getAdapter().getItem(position);
                Log.i("postulacion", String.valueOf(postulacion.getId()));
                idPostulacion = postulacion.getId();
                spinnerArray = new String[tipoOpnions.size()];
                spinnerMap = new HashMap<String, Integer>();
                for (int i = 0; i < tipoOpnions.size(); i++) {
                    spinnerMap.put(tipoOpnions.get(i).getDescripcion(), tipoOpnions.get(i).getId());
                    spinnerArray[i] = tipoOpnions.get(i).getDescripcion();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(popupView.getContext(), R.layout.simple_spinner_item, spinnerArray);
                tipoOpinion.setAdapter(adapter);
                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        tituloPopupCarteleraPrivada.setText("Tu Opinión sobre: " + postulacion.getPostulado().getPersona().getNombre());
                        descripcionOpinion.setText("");
                        popupWindow.dismiss();
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                    }
                }, 100);


            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public long getIdTipOpinion() {
        return spinnerMap.get(valueTipoOpinion);
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

    @UiThread
    public void dimessPopup() {
        popupWindow.dismiss();
    }

    @Background
    public void enviarOpinion() {
        String url = "http://" + sIp + ":8000/enviar/opinion/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentEncoding(ContentCodingType.ALL);
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("postulacionid_postulacion", String.valueOf(idPostulacion));
        byte[] data = new byte[0];
        try {
            data = descripcionOpinion.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.add("descripcion", Base64.encodeToString(data, Base64.DEFAULT));
        map.add("usuarioid_usuario", String.valueOf(idUser));
        map.add("calificacion", String.valueOf((int)ratingBar.getRating()));
        map.add("tipo_opnionid_tipo_opninion", String.valueOf(getIdTipOpinion()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
        try {
            MessageServicie s = restTemplate.postForObject(url, requestEntity, MessageServicie.class);
            if (s.getMessage() == 201)
                message("su comentario ha sido enviado");
            else if (s.getMessage() == 204)
                message("usted ya ha comentado sobre el postulado anterior mente");
            else if (s.getMessage() == 500)
                message("Disculpe el servidor presenta problemas");
            dimessPopup();
        } catch (Exception e) {
            Log.e("error noticias privadas", e.getMessage());
            message("error de conexion");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviar:
                enviarOpinion();
                break;
            case R.id.btnCancelar:
                popupWindow.dismiss();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @UiThread
    public void cargarnoticias() {
        adapterGeneral = new ListCarteleraPrivadaGeneralAdapter(getActivity(), noticias);
        this.listCarteleraPrivadaGeneral.setAdapter(adapterGeneral);

    }

    @UiThread
    public void cargarPostulaciones() {
        adapterPostulaciones = new ListCarteleraPrivadaPostulacionesAdapter(getActivity(), postulaciones);
        this.listCarteleraPrivadaPostulaciones.setAdapter(adapterPostulaciones);

    }

    @UiThread
    public void cargarInteres() {
        adapterInteres = new ListCarteleraPrivadaInteresAdapter(getActivity(), noticiasIteres);
        this.listCarteleraPrivadaInteres.setAdapter(adapterInteres);
    }

    @UiThread
    public void message(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void progressBarCartelera(boolean status) {
        if (status)
            progress_bar_cartelera.setVisibility(View.VISIBLE);
        else
            progress_bar_cartelera.setVisibility(View.GONE);
    }

    @UiThread
    void progressBarPostulaciones(boolean status) {
        if (status)
            progress_bar_postulaciones.setVisibility(View.VISIBLE);
        else
            progress_bar_postulaciones.setVisibility(View.GONE);
    }

    @UiThread
    void progressBarIntereses(boolean status) {
        if (status)
            progress_bar_intereses.setVisibility(View.VISIBLE);
        else
            progress_bar_intereses.setVisibility(View.GONE);
    }


    @Background
    public void informacionCarteleraPrivada() {
        progressBarCartelera(true);
        String url = "http://" + sIp + ":8000/cartelera/privada/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(requestHeaders);
        try {
            Noticia[] noticias = restTemplate.postForObject(url, requestEntity, Noticia[].class);
            this.noticias = new ArrayList<Noticia>(Arrays.asList(noticias));
            progressBarCartelera(false);
            cargarnoticias();
        } catch (Exception e) {
            progressBarCartelera(false);
            Log.e("error noticias privadas", e.getMessage());
            message("error de conexion");
        }
    }

    @Background
    public void informacionCarteleraPostulaciones() {
        progressBarPostulaciones(true);
        NoticiasPublicas objNoticias = new NoticiasPublicas();
        Noticia noticia = new Noticia();
        String url = "http://" + sIp + ":8000/postulaciones/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(requestHeaders);
        try {
            Postulacion[] postulaciones = restTemplate.postForObject(url, requestEntity, Postulacion[].class);
            this.postulaciones = new ArrayList<Postulacion>(Arrays.asList(postulaciones));
            progressBarPostulaciones(false);
            cargarPostulaciones();
        } catch (Exception e) {
            progressBarPostulaciones(false);
            Log.e("error postulaciones", e.getMessage());
            message("error de conexion");

        }

    }

    @Background
    public void informacionCarteleraInteres() {
        progressBarIntereses(true);
        NoticiasPublicas objNoticias = new NoticiasPublicas();
        Noticia noticia = new Noticia();
        String url = "http://" + sIp + ":8000/preferencia/noticia/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("user_id", idUser);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
        try {
            Noticia[] noticias = restTemplate.postForObject(url, requestEntity, Noticia[].class);
            this.noticiasIteres = new ArrayList<Noticia>(Arrays.asList(noticias));
            ;
            progressBarIntereses(false);
            cargarInteres();
        } catch (Exception e) {
            progressBarIntereses(false);
            Log.e("error Interes", e.getMessage());
            message("error de conexion");

        }

    }

    @Background
    public void tipoOpinion() {
        String url = "http://" + sIp + ":8000/tipo/opinion/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(requestHeaders);
        try {
            TipoOpnion[] tOpnions = restTemplate.postForObject(url, requestEntity, TipoOpnion[].class);
            this.tipoOpnions = new ArrayList<TipoOpnion>(Arrays.asList(tOpnions));
            progressBarIntereses(false);
            cargarInteres();
        } catch (Exception e) {
            progressBarIntereses(false);
            Log.e("error TipoOpinion", e.getMessage());
            message("error de conexion");

        }

    }
}
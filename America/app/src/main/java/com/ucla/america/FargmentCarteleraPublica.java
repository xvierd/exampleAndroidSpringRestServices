package com.ucla.america;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.ucla.adapter.ListCarteleraPublicaAdapter;
import com.ucla.models.Noticia;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FargmentCarteleraPublica.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FargmentCarteleraPublica#newInstance} factory method to
 * create an instance of this fragment.
 */

@EFragment(R.layout.fragment_view_cartelera_publica)
public class FargmentCarteleraPublica extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @ViewById
    public ListView listCarteleraPublica;
    @ViewById
    public FloatingActionsMenu btnMenuCartelera;
    @ViewById
    public SwipyRefreshLayout swipyrefreshlayout;
    //popup
    private LinearLayout layout = null;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private View popupView;
    private Button okbutton;
    private TextView ip;
    private String sIp;

    //fin popup

    ListCarteleraPublicaAdapter adapter;
    ArrayList<Noticia> noticias;
    @ViewById
    ProgressBar progress_bar;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static FargmentCarteleraPublica newInstance(String param1, String param2) {
        FargmentCarteleraPublica fragment = new FargmentCarteleraPublica_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FargmentCarteleraPublica() {
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

        View viewCarteleraPublica = inflater.inflate(R.layout.fragment_view_cartelera_publica, container, false);
        listCarteleraPublica = (ListView) viewCarteleraPublica.findViewById(R.id.listCarteleraPublica);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");

        //configurando popup
        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup_sugerencia_cartelera, null);
        informacionPublica();
        okbutton = (Button) popupView.findViewById((R.id.okbutton));
        okbutton.setOnClickListener(this);
        ip = (TextView) popupView.findViewById((R.id.ip));
        popupWindow = new PopupWindow(popupView, android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        Drawable d = new ColorDrawable(Color.WHITE);
        d.setAlpha(240);
        popupWindow.setAnimationStyle(R.style.Base_Animation_AppCompat_DropDownUp);
        popupWindow.setBackgroundDrawable(d);

        agregarIP();
        //fin configuracion popup
        return viewCarteleraPublica;
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

    @Click
    public void btnSugerenciaPublica01Clicked() {

        popupWindow.dismiss();
        popupWindow.setFocusable(true);// esto es para que el popupp tenga foco y se edite
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        btnMenuCartelera.collapse();

    }
    /*Lista
    @AfterViews
    void bindAdapter() {
        this.listCarteleraPublica.setAdapter(adapter);
        //swipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.TOP);
        swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                swipyrefreshlayout.setRefreshing(false);

            }
        });

    }*/

    @Click
    void swipyrefreshlayout() {
        swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
            }
        });
    }

    @Override
    public void onClick(View v) {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ip", ip.getText().toString());
        editor.apply();
        sIp = ip.getText().toString();
        popupWindow.dismiss();
    }

    public void agregarIP() {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");
        ip.setText(sIp);
        popupWindow.dismiss();
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @UiThread
    public void cargarnoticias() {
        adapter = new ListCarteleraPublicaAdapter(getActivity(), noticias);
        this.listCarteleraPublica.setAdapter(adapter);
    }

    @UiThread
    public void message(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void progressBar(boolean status) {
        if (status)
            progress_bar.setVisibility(View.VISIBLE);
        else
            progress_bar.setVisibility(View.GONE);
    }

    @Background
    public void informacionPublica() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar(true);
        String url = "http://" + sIp + ":8000/cartelera/publica/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestHeaders);
        try {
            Noticia[] noticias = restTemplate.postForObject(url, requestEntity, Noticia[].class);
            this.noticias = new ArrayList<>(Arrays.asList(noticias));
            progressBar(false);
            cargarnoticias();
        } catch (Exception e) {
            progressBar(false);
            Log.e("noticias publicas:", e.getMessage());
            message("error de conexion");
        }


    }

}

package com.ucla.america;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ucla.models.Usuario;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_home_login)
public class HomeLogin extends Fragment implements Button.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnLogin;
    private OnFragmentInteractionListener mListener;
    private UserLoginTask authTask = null;
    private EditText etPswd;
    private EditText etUsuario;
    private View mProgressView;
    private String sIp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeLogin newInstance(String param1, String param2) {
        HomeLogin fragment = new HomeLogin_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeLogin() {
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
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        sIp = pref.getString("ip", "");

        View view = null;
        view = inflater.inflate(R.layout.fragment_home_login, container, false);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        btnLogin.setBackgroundColor(Color.parseColor("#5fcab0"));
        etUsuario = (EditText) view.findViewById(R.id.etUsuario);
        etPswd = (EditText) view.findViewById(R.id.etPswd);

        mProgressView = (ProgressBar) view.findViewById(R.id.login_progress);


        //return inflater.inflate(R.layout.fragment_home_login, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if (a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                attemptLogin();

                break;
        }
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


    // validamos los datos antes de contactar el servicio para loguear
    public void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        etUsuario.setError(null);
        etPswd.setError(null);
        btnLogin.setError(null);
        // Store values at the time of the login attempt.
        String usuario = etUsuario.getText().toString();
        String password = etPswd.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPswd.setError(getString(R.string.error_invalid_password));
            focusView = etPswd;
            cancel = true;
        }

        // Check for a valido nombre de usuario.
        if (TextUtils.isEmpty(usuario)) {
            etUsuario.setError(getString(R.string.error_field_required));
            focusView = etUsuario;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgressView.showContextMenu();
            mProgressView.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            authTask = new UserLoginTask(usuario, password);


            authTask.execute((getActivity().getApplicationContext()), getActivity());
        }
    }

    private boolean isPasswordValid(String password) {

        if (password.length() > 3)
            return true;

        return false;
    }


    @UiThread
    public void progressDialogStop() {
        btnLogin.setEnabled(true);
        mProgressView.setVisibility(View.GONE);

    }


    /* proceso en background que se encarga de loguear en segundo plano lo que evita que la app se cuelgue
    mientras se conecta al servicio*/
    public class UserLoginTask extends AsyncTask<Context, Void, Boolean> {

        private Context context = null;
        private HomeActivity login = null;
        private boolean conexion = true;
        private final String usuario;
        private final String password;


        UserLoginTask(String usuario, String password) {
            this.usuario = usuario.trim();
            this.password = password.trim();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Context... params) {
            // TODO: attempt authentication against a network service.
            context = params[0];

            login = (HomeActivity) params[1];
            String url = "http://" + sIp + ":8000/login/";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("usuario", usuario);
            map.add("password", password);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            try {
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
                Usuario[] objUsuario = restTemplate.postForObject(url, requestEntity, Usuario[].class);
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("idUser", String.valueOf(objUsuario[0].getId()));
                editor.putString("fullName", objUsuario[0].getPersona().getNombre() + " " + objUsuario[0].getPersona().getApellido());
                try {
                    editor.putString("foto", objUsuario[0].getPersona().getFoto());
                    editor.apply();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                return (objUsuario[0].getUsername().equals(usuario));
            } catch (Exception e) {
                progressDialogStop();
                Log.e("error user", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            //showProgress(false);
            progressDialogStop();
            if (success) {

                Intent intent = new Intent(login, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                login.finish();
            } else {
                if (conexion) {
                    etPswd.setError(getString(R.string.error_invalid_password));
                    etPswd.requestFocus();
                } else {
                    Toast t = Toast.makeText(login, "Error de Conexion",
                            Toast.LENGTH_LONG);
                    t.show();
                }

            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;

        }

        // metodo que se encarga de detectar si hay conexion WIFI
        protected Boolean conectadoWifi() {
            ConnectivityManager connectivity = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }

        // metodo que se encarga de detectar si hay conexion Móvil
        protected Boolean conectadoRedMovil() {
            ConnectivityManager connectivity = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected Boolean estaConectado() {
            if (conectadoWifi() || conectadoRedMovil()) {

                return true;
            } else {
                return false;
            }
        }


    }

    /**
     *  @Override
     *  con header param jetty jersey
    protected Boolean doInBackground(Context... params) {
    // TODO: attempt authentication against a network service.

    context = params[0];
    login = (HomeActivity) params[1];
    //String url = "http://"+sIp+":8000/login/";
    String url = "http://"+sIp+":8000/login/";
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
    map.add("usuario", usuario);
    map.add("password", password);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    HttpHeaders requestHeaders = new HttpHeaders();

    // Sending multipart/form-data
    requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, requestHeaders);
    //HttpEntity<?> requestEntity = new HttpEntity<Object>(objUsuario , map);
    Usuario[] objUsuario = restTemplate.postForObject(url,requestEntity, Usuario[].class);
    return (objUsuario != null);
    }
     */

}

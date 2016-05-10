package com.ucla.america;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.ucla.adapter.ImageAdapter;
import com.ucla.models.Club;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
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

import java.util.Timer;

import page.indicator.CirclePageIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisionVision.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisionVision#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_mision_vision)
public class MisionVision extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @ViewById
    public ViewPager viewPager;
    @ViewById
    public CirclePageIndicator mIndicator;
    @ViewById
    public FloatingActionButton fab;
    @ViewById
    public ObservableScrollView scrollView;
    @ViewById
    public TextView tvVision;
    @ViewById
    public TextView tvMision;
    @Bean
    public ImageAdapter adapter;
    private OnFragmentInteractionListener mListener;
    //private CirclePageIndicator mIndicator;
    private Timer swipeTimer;
    private Club club;
    private String sIp;
    @ViewById
    public ProgressBar progress_bar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisionVision.
     */
    // TODO: Rename and change types and number of parameters
    public static MisionVision newInstance(String param1, String param2) {
        MisionVision fragment = new MisionVision_();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MisionVision() {
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
        view = inflater.inflate(R.layout.fragment_mision_vision, container, false);
        //ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        //adapter = new ImageAdapter(getActivity());
        //bindAdapter();
        //mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);

        cargarMisionVision();
        return view;
    }

    @AfterViews
    void bindAdapter() {
        viewPager.setAdapter(adapter);
        mIndicator.setViewPager(viewPager);
        fab.attachToScrollView(scrollView);

    }

    @Click
    void fab() {
        Intent int1;
        int1 = new Intent(getActivity(), FotoGaleria_.class);

        int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(int1);
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

    @UiThread
    public void showMisionVision() {
        tvMision.setText(club.getMision());
        tvVision.setText(club.getVision());
    }

    @UiThread
    public void message(String s) {
        Toast.makeText(getActivity().getApplicationContext(), "Error de conexion", Toast.LENGTH_LONG).show();
    }

    @UiThread
    void progressBarMision(boolean status) {
        if (status)
            progress_bar.setVisibility(View.VISIBLE);
        else
            progress_bar.setVisibility(View.GONE);
    }

    @Background
    public void cargarMisionVision() {
        progressBarMision(true);
        String url = "http://" + sIp + ":8000/club/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(requestHeaders);
        try {
            Club[] club = restTemplate.postForObject(url, requestEntity, Club[].class);
            this.club = club[0];
            progressBarMision(false);
            showMisionVision();
        } catch (Exception e) {
            progressBarMision(false);
            message("Error de conexion");
        }
    }

}

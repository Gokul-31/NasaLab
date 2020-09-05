package com.example.nasalab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apod extends Fragment {

    public apod() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static apod newInstance(String param1, String param2) {
        apod fragment = new apod();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apod, container, false);
    }

    DatePicker dp;
    ImageView imageView;
    WebView ytp;
    Calendar now;
    Pojo p;

    Handler h;

    static final String API_KEY = "AIzaSyCNXEVGZecTst0GH8Oo-CgafQuQBGTmCLc";
    static final String VIDEO_ID = "https://www.youtube.com/watch?v=fg8Cpl5PIRE";

    DatePicker.OnDateChangedListener dateListener;

    int year;
    int month;
    int day;
    String date;

    Retrofit retrofit;
    JsonHolder jApi;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Global.setFrag(0);

        h = new Handler(getContext().getMainLooper());

        dp = getView().findViewById(R.id.datePicker);
        imageView = getView().findViewById(R.id.img);
        ytp = getView().findViewById(R.id.vid);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);

        ytp.getSettings().setJavaScriptEnabled(true);
        ytp.setWebChromeClient(new WebChromeClient());


        dateListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                imageView.setImageDrawable(null);
                ytp.loadUrl("about:blank");

                year = i;
                month = i1 + 1;
                day = i2;
                loadMedia();
            }
        };

        dp.init(year, month, day, dateListener);
        jApi = retrofit.create(JsonHolder.class);

        loadMedia();
    }

    void loadMedia() {
        date = "" + year + "-" + month + "-" + day;
        Call<Pojo> call = jApi.getData1(date);

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", "onResponse: Code :" + response.code());
                    return;
                }
                p = response.body();
                Log.i("Test", "onResponse: url is " + p.getUrl());

                if (p.getMedia_type().equals("image")) {
                    Log.i("Main", "onResponse: This is a image");

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setVisibility(View.VISIBLE);
                            ytp.loadUrl("about:blank");

                        }
                    });

                    Picasso.with(getContext()).
                            load(p.getUrl())
                            .into(imageView);

                } else if (p.getMedia_type().equals("video")) {
                    Log.i("Main", "onResponse: This is a video");

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(null);
                            ytp.setVisibility(View.VISIBLE);
                            ytp.loadUrl(p.getUrl());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
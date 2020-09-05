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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class detail extends Fragment {


    private POJO_.C1_Collection.C2_Item preData;

    public detail() {
    }

    public static detail newInstance(String param1, String param2) {
        detail fragment = new detail();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            preData = (POJO_.C1_Collection.C2_Item) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    Retrofit retrofit;
    JsonHolder jsonHolder;

    ImageView imageView;
    WebView webView;


    Handler h;

    String s;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Global.setFrag(1);

        h= new Handler(getContext().getMainLooper());

        retrofit= new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonHolder=retrofit.create(JsonHolder.class);

        imageView=getView().findViewById(R.id.d_image);
        webView=getView().findViewById(R.id.d_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        Call<POJO_D> call1= jsonHolder.getDetail(preData.data.get(0).getNasa_id());

        call1.enqueue(new Callback<POJO_D>() {
            @Override
            public void onResponse(Call<POJO_D> call, Response<POJO_D> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", "onResponse: Code :" + response.code());
                    return;
                }
                final POJO_D.CD1_Collection.CD2_Item item=response.body().getCollection().getItems().get(0);
                //images
                if(preData.getData().get(0).getMedia_type().equals("image")){
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.setVisibility(View.GONE);
                            webView.loadUrl("about:blank");
                            imageView.setVisibility(View.VISIBLE);

                            s=item.getHref();
                            s=s.substring(0,4)+"s"+s.substring(4);

                            Picasso.with(getContext())
                                    .load(s)
                                    .into(imageView);
                        }
                    });
                }
                //video
                else{
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                            imageView.setImageDrawable(null);
                            s = item.getHref();
                            String[] divide= s.split("~");
                            s=divide[0]+"~mobile.mp4";
                            s=s.substring(0,4)+"s"+s.substring(4);
                            webView.loadUrl(s);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<POJO_D> call, Throwable t) {
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
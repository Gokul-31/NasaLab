package com.example.nasalab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class lib extends Fragment {

    public lib() {
        // Required empty public constructor
    }

    public static lib newInstance(String param1, String param2) {
        lib fragment = new lib();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RAdapter adapter;

    ArrayList<POJO_.C1_Collection.C2_Item> itemsList;

    ImageButton searchBt;
    EditText searchBar;
    String search;

    Retrofit retrofit;
    JsonHolder jsonHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lib, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Global.setFrag(0);

        searchBar=getView().findViewById(R.id.search_bar);
        searchBt=getView().findViewById(R.id.searchBt);

        recyclerView=getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        itemsList= new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonHolder = retrofit.create(JsonHolder.class);

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search=searchBar.getText().toString();
                Log.i(TAG, "search string : "+search);
                load(search);
            }
        });

    }

    private void load(String s) {
        Call<POJO_> call = jsonHolder.getData(s);

        call.enqueue(new Callback<POJO_>() {
            @Override
            public void onResponse(Call<POJO_> call, Response<POJO_> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", "onResponse: Code :" + response.code());
                    return;
                }
                POJO_ p =response.body();
                for(POJO_.C1_Collection.C2_Item item: p.getCollection().getItems()){
                    if(!(item.getLinks()==null)&&!(item.getData().get(0).media_type=="audio")){
                        itemsList.add(item);
                    }
                }

                adapter=new RAdapter(getContext(),itemsList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new RAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        POJO_.C1_Collection.C2_Item i=itemsList.get(position);
                        Bundle bundle= new Bundle();
                        bundle.putSerializable("data",i);
                        Fragment fragment= new detail();
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).commit();
                    }
                });

            }

            @Override
            public void onFailure(Call<POJO_> call, Throwable t) {
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
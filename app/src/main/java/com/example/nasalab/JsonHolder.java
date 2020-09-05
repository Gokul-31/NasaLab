package com.example.nasalab;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonHolder {
    @GET("search")
    Call<POJO_> getData(@Query("q") String s);

    @GET("asset/{nasaid}")
    Call<POJO_D> getDetail(@Path("nasaid") String nasaid);

    @GET("apod?api_key=42fcmcXcskJYhMJq4GgA1tJz5anVSW5mKQUVq4S0")
    Call<Pojo> getData1(@Query("date") String date);
}

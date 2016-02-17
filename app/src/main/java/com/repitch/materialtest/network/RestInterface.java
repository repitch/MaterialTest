package com.repitch.materialtest.network;

import com.repitch.materialtest.network.pojo.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ilyapyavkin on 17.02.16.
 */
public interface RestInterface {

    @GET("weather")
    Call<WeatherModel> getWeather(
            @Query("appid") String appid,
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("lang") String lang,
            @Query("units") String units);
}

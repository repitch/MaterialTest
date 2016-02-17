package com.repitch.materialtest.view.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.repitch.materialtest.BaseActivity;
import com.repitch.materialtest.Constants;
import com.repitch.materialtest.R;
import com.repitch.materialtest.network.RestInterface;
import com.repitch.materialtest.network.pojo.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ilyapyavkin on 17.02.16.
 */
public class RetrofitActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initViews();

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        loadWeather();
    }
    private TextView mTxtCity, mTxtStatus, mTxtHumidity, mTxtPressure, mTxtTemp;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private void initViews() {
        mTxtCity = (TextView) findViewById(R.id.txt_city);
        mTxtStatus = (TextView) findViewById(R.id.txt_status);
        mTxtTemp = (TextView) findViewById(R.id.txt_temp);
        mTxtHumidity = (TextView) findViewById(R.id.txt_humidity);
        mTxtPressure = (TextView) findViewById(R.id.txt_press);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadWeather();
            }
        });
    }

    private void loadWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface restInterface = retrofit.create(RestInterface.class);
        Call<WeatherModel> call = restInterface.getWeather(
                Constants.WEATHER_APP_KEY,
                Double.toString(Constants.CITY_ZEL_LAT),
                Double.toString(Constants.CITY_ZEL_LON),
                Constants.LANG_RU,
                Constants.UNITS_METRIC);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Log.e("RETRO", "onResponse: " + response.toString());
                WeatherModel weatherModel = response.body();
                mTxtCity.setText(weatherModel.getName());
                mTxtStatus.setText(weatherModel.getWeather().get(0).getDescription());
                mTxtTemp.setText(weatherModel.getMain().getTemp()+"");
                mTxtHumidity.setText(weatherModel.getMain().getHumidity()+"");
                mTxtPressure.setText(weatherModel.getMain().getPressure()+"");

                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mTxtCity, "Погода успешно обновлена", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.e("RETRO", "onFailure: "+t.toString());
            }
        });
    }

}

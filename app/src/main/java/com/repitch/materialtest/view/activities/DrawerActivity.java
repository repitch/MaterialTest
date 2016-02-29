package com.repitch.materialtest.view.activities;

import android.content.Intent;

import com.repitch.materialtest.BaseActivity;

/**
 * Created by ilyapyavkin on 29.02.16.
 */
public class DrawerActivity extends BaseActivity implements DrawerCallbacks {
    @Override
    public void openWeather() {
        startActivity(new Intent(this, RetrofitActivity.class));
    }

    @Override
    public void openGallery() {
        startActivity(new Intent(this, GalleryActivity.class));
    }

    @Override
    public void openAudiosVK() {
        startActivity(new Intent(this, VKAudiosActivity.class));
    }
}

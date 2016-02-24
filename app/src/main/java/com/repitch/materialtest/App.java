package com.repitch.materialtest;

import android.app.Application;

import com.vk.sdk.VKSdk;


public class App extends Application {

    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        // Инициализация VK
        VKSdk.initialize(this);

    }

    public static App getInstance() {
        return application;
    }

}

package com.repitch.materialtest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.repitch.materialtest.BaseActivity;
import com.repitch.materialtest.R;
import com.repitch.materialtest.view.views.ImageSnackbar;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by ilyapyavkin on 17.02.16.
 */
public class VKAuthActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int RESULT_AUTH_SUCCESS = 0;
    public static final int RESULT_AUTH_ERROR = 1;
    public static final int REQUEST_AUTH = 10;

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_auth);
        initViews();

    }
    private Button mBtnVkAuth;

    private void initViews() {
        mBtnVkAuth = (Button) findViewById(R.id.btn_vk_auth);
        mBtnVkAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(VKAuthActivity.this, VKScope.AUDIO, VKScope.FRIENDS);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Log.e(VKAuthActivity.class.getSimpleName(), "VKSdk onResult");
                ImageSnackbar.make(mBtnVkAuth, ImageSnackbar.TYPE_SUCCESS, "Авторизация прошла успешно", Snackbar.LENGTH_SHORT).show();
                setResult(RESULT_AUTH_SUCCESS);
                finish();
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Log.e(VKAuthActivity.class.getSimpleName(), "VKSdk onError");
                ImageSnackbar.make(mBtnVkAuth, ImageSnackbar.TYPE_ERROR, "Ошибка при авторизации: " + error, Snackbar.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

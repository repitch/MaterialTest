package com.repitch.materialtest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import com.repitch.materialtest.BaseActivity;
import com.repitch.materialtest.R;
import com.repitch.materialtest.view.views.ImageSnackbar;
import com.vk.sdk.VKSdk;

/**
 * Created by ilyapyavkin on 29.02.16.
 */
public class VKAudiosActivity extends BaseActivity {

    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_audios);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        if (!VKSdk.isLoggedIn()) {
            startActivityForResult(new Intent(this, VKAuthActivity.class), VKAuthActivity.REQUEST_AUTH);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VKAuthActivity.REQUEST_AUTH) {
            switch (resultCode) {
                case VKAuthActivity.RESULT_AUTH_SUCCESS:
                    ImageSnackbar.make(mCoordinatorLayout, ImageSnackbar.TYPE_SUCCESS, "Авторизация прошла успешно", Snackbar.LENGTH_SHORT).show();
                    break;
                case VKAuthActivity.RESULT_AUTH_ERROR:
                    ImageSnackbar.make(mCoordinatorLayout, ImageSnackbar.TYPE_ERROR, "Ошибка при авторизации", Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

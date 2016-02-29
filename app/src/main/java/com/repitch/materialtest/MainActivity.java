package com.repitch.materialtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;
import com.repitch.materialtest.view.activities.BaseSpiceActivity;
import com.repitch.materialtest.view.activities.GalleryActivity;
import com.repitch.materialtest.view.activities.RetrofitActivity;
import com.repitch.materialtest.view.activities.VKAudiosActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.io.ByteArrayOutputStream;

public class MainActivity extends BaseSpiceActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    Button btnTestSnackbar, btnTestBottomSheet, btnTestRobospice;
    ProgressBar pbTestRobospice;
    TextView txtRobospiceResult;
    ImageView imgSmall;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    BottomSheet.Builder testBottomSheet;
    /*View mBottomSheet;
    BottomSheetBehavior mBottomSheetBehavior;*/

    SimpleTextRequest textRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initViewsClicks();

        // robospice
        textRequest = new SimpleTextRequest("http://androiddocs.ru/api/friends.json");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
            // Пользователь успешно авторизовался
                Log.e(MainActivity.class.getSimpleName(), "VKSdk onResult");
            }

            @Override
            public void onError(VKError error) {
            // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Log.e(MainActivity.class.getSimpleName(), "VKSdk onError");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViewsClicks() {
        btnTestRobospice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                txtRobospiceResult.setVisibility(View.GONE);
                pbTestRobospice.setVisibility(View.VISIBLE);
                getSpiceManager().execute(textRequest, "txt", DurationInMillis.ONE_MINUTE, new RequestListener<String>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        Snackbar.make(v, "RequestFailure:(", Snackbar.LENGTH_SHORT).show();
                        txtRobospiceResult.setVisibility(View.VISIBLE);
                        pbTestRobospice.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRequestSuccess(String s) {
                        txtRobospiceResult.setText(s);
                        Snackbar.make(v, "Request Success! :)", Snackbar.LENGTH_SHORT).show();
                        txtRobospiceResult.setVisibility(View.VISIBLE);
                        pbTestRobospice.setVisibility(View.GONE);
                    }
                });
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cake!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnTestSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Test snackbar, damn!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        btnTestBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBottomSheet.show();
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        imgSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                // pass img between activities
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.palm);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                args.putByteArray(DetailImageFragment.ARG_PICTURE, b);
                launchActivityWithSharedElement(DetailImageActivity.class, imgSmall, args);
            }
        });
    }

    private void initUI() {
        btnTestRobospice = (Button) findViewById(R.id.btnTestRobospice);
        pbTestRobospice = (ProgressBar) findViewById(R.id.pbTestRobospice);
        txtRobospiceResult = (TextView) findViewById(R.id.txtRobospiceResult);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btnTestSnackbar = (Button) findViewById(R.id.btnTestSnackbar);
        btnTestBottomSheet = (Button) findViewById(R.id.btnTestBottomSheet);
        imgSmall = (ImageView) findViewById(R.id.imgSmall);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        /*mBottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);*/
        testBottomSheet = new BottomSheet.Builder(this).title("Это тестовый bottomsheet").sheet(R.menu.bottom_sheet).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.help:
//                        q.toast("Help me!");
                        break;
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.nav_gallery:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.nav_vk_audios:
                startActivity(new Intent(this, VKAudiosActivity.class));
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

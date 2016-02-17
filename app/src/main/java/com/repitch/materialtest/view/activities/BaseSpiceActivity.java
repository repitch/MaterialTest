package com.repitch.materialtest.view.activities;

import com.octo.android.robospice.SpiceManager;
import com.repitch.materialtest.BaseActivity;
import com.repitch.materialtest.services.SampleSpiceService;

/**
 * Created by ilyapyavkin on 18.11.15.
 */
public abstract class BaseSpiceActivity extends BaseActivity {
    private SpiceManager spiceManager = new SpiceManager(SampleSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
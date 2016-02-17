package com.repitch.materialtest.services;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

/**
 * Created by ilyapyavkin on 18.11.15.
 */
public class SampleSpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        InFileStringObjectPersister inFileStringObjectPersister =
                new InFileStringObjectPersister(application);

        cacheManager.addPersister(inFileStringObjectPersister);

        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return 3;
    }
}
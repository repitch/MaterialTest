package com.repitch.materialtest;

/**
 * Created by ilyapyavkin on 29.02.16.
 */
public class DataManager {
//    public Person mPerson;

    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }
}

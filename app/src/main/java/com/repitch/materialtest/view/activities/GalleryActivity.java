package com.repitch.materialtest.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.GridView;

import com.repitch.materialtest.R;
import com.repitch.materialtest.adapters.GalleryGridAdapter;
import com.repitch.materialtest.adapters.ImageItem;

import java.util.ArrayList;

/**
 * Created by ilyapyavkin on 12.02.16.
 */
public class GalleryActivity extends BaseSpiceActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView mGridGallery;
    GalleryGridAdapter mGridGalleryAdapter;
    private ArrayList<ImageItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mGridGallery = (GridView) findViewById(R.id.grid_gallery);
        mGridGalleryAdapter = new GalleryGridAdapter(this, R.layout.grid_gallery_item, getData());
        mGridGallery.setAdapter(mGridGalleryAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    public ArrayList<ImageItem> getData() {
        ArrayList<ImageItem> images = new ArrayList<>();
        images.add(new ImageItem(Uri.parse("https://homepages.cae.wisc.edu/~ece533/images/airplane.png"),
                "Plane"));
        images.add(new ImageItem(Uri.parse("https://homepages.cae.wisc.edu/~ece533/images/baboon.png"),
                "Baboon"));
        images.add(new ImageItem(Uri.parse("https://homepages.cae.wisc.edu/~ece533/images/peppers.png"),
                "Peppers"));
        images.add(new ImageItem(Uri.parse("http://www.kayavolunteer.com/images/categories/volunteer/big_wildlife_and_animal_volunteer.jpg"),
                "Lion"));
        images.add(new ImageItem(Uri.parse("http://cdn.earthporm.com/wp-content/uploads/2014/10/animal-family-portraits-2__880.jpg"),
                "Lion pride"));
        images.add(new ImageItem(Uri.parse("http://designyoutrust.com/wp-content/uploads/2012/02/baby-milk-tea1.jpg"),
                "Rabbit"));
        // по второму кругу
        int len = images.size();
        for (int i = 0; i < len; i++) {
            images.add(images.get(i));
        }
        // по третьему
        for (int i = 0; i < len; i++) {
            images.add(images.get(i));
        }
        return images;
    }
}

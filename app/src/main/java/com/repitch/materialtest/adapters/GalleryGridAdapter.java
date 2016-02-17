package com.repitch.materialtest.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.repitch.materialtest.BaseActivity;
import com.repitch.materialtest.DetailImageActivity;
import com.repitch.materialtest.DetailImageFragment;
import com.repitch.materialtest.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ilyapyavkin on 12.02.16.
 */
public class GalleryGridAdapter extends ArrayAdapter {
    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList mData = new ArrayList();

    public GalleryGridAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        mLayoutResourceId = layoutResourceId;
        mContext = context;
        mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final ImageItem item = (ImageItem) mData.get(position);
        holder.imageTitle.setText(item.getTitle());
        Picasso.with(mContext)
                .load(item.getUri())
                .placeholder(R.drawable.gallery_placeholder)
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                // pass img between activities
//                args.putString(DetailImageFragment.ARG_IMAGE_URI, item.getUri().toString());
                args.putParcelable(DetailImageFragment.ARG_IMAGE_ITEM, item);
//                args.putSerializable(DetailImageFragment.ARG_IMAGE_ITEM, item);
                ((BaseActivity)mContext).launchActivityWithSharedElement(DetailImageActivity.class, (ImageView) v, args);
            }
        });
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}

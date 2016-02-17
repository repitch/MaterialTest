package com.repitch.materialtest;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.repitch.materialtest.adapters.ImageItem;
import com.squareup.picasso.Picasso;

/**
 * Created by ilyapyavkin on 18.11.15.
 */
public class DetailImageFragment extends BaseFragment {
    public static final String ARG_PICTURE = "ARG_PICTURE";
    public static final String ARG_IMAGE_URI = "ARG_IMAGE_URI";
    public static final String ARG_IMAGE_ITEM = "ARG_IMAGE_ITEM";

    ImageView mImage;
    TextView mTxtImageTitle;
    ImageItem mImageItem;

    public static DetailImageFragment newInstance() {
        return new DetailImageFragment();
    }

    public static DetailImageFragment newInstance(Bundle args) {
        DetailImageFragment fragment = new DetailImageFragment();
        if (args!=null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_image, container, false);
        mImage = (ImageView) rootView.findViewById(R.id.image);
        mTxtImageTitle = (TextView) rootView.findViewById(R.id.txt_image_title);
        if (getArguments()!=null) {
           /* byte[] b = getArguments().getByteArray(ARG_PICTURE);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            mImage.setImageBitmap(bmp);*/
            if (getArguments().containsKey(ARG_IMAGE_URI)) {
                Uri uri = Uri.parse(getArguments().getString(ARG_IMAGE_URI));
//                mImageItem = (ImageItem) getArguments().getSerializable(ARG_IMAGE_ITEM);
                Picasso.with(getActivity())
                        .load(uri)
                        .into(mImage);
            }
            if (getArguments().containsKey(ARG_IMAGE_ITEM)) {
                mImageItem = getArguments().getParcelable(ARG_IMAGE_ITEM);
                Picasso.with(getActivity())
                        .load(mImageItem.getUri())
                        .into(mImage);
                mTxtImageTitle.setText(mImageItem.getTitle());
            }
        }
        return rootView;
    }
}

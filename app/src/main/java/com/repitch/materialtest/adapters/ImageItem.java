package com.repitch.materialtest.adapters;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ilyapyavkin on 12.02.16.
 */
public class ImageItem implements Parcelable {
    private Uri mUri;
    private String title;

    public ImageItem(Uri uri, String title) {
        super();
        mUri = uri;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    protected ImageItem(Parcel in) {
        mUri = in.readParcelable(Uri.class.getClassLoader());
        title = in.readString();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mUri, flags);
        dest.writeString(title);
    }
}

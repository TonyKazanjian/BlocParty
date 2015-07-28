package io.bloc.android.blocparty.ui.activity.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import api.model.ImagePost;

/**
 * Created by tonyk_000 on 7/23/2015.
 */
public class Post_Fragment extends Fragment implements ImageLoadingListener {

    ImagePost fbPost;
    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}

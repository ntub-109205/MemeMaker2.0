package com.deep.photoeditor.model;

import android.graphics.BitmapFactory;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.deep.photoeditor.R;
import com.deep.photoeditor.utils.GenerateModel;
import com.deep.photoeditor.utils.GeneratePictureManager;


/**
 * Created by HomgWu on 2017/11/29.
 */

public class SharePicModel extends GenerateModel {
    private ImageView mTitleAvatarIv;
    private View mSharePicView;
    private int mAvatarResId;

    public SharePicModel(ViewGroup rootView) {
        super(rootView);
    }

//    @Override
//    protected void startPrepare(GeneratePictureManager.OnGenerateListener listener) throws Exception {
//
//    }

    @Override
    protected void startPrepare(GeneratePictureManager.OnGenerateListener listener) throws Exception {
        mSharePicView = LayoutInflater.from(mContext).inflate(R.layout.layout_share_video_gif_model, mRootView, false);
        mTitleAvatarIv = mSharePicView.findViewById(R.id.invitation_share_link_pic_avatar_iv);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), mAvatarResId));
//        circularBitmapDrawable.setCircular(true);
        mTitleAvatarIv.setImageDrawable(circularBitmapDrawable);
        prepared(listener);
    }

    @Override
    public View getView() {
        return mSharePicView;
    }

    public void setAvatarResId(int avatarResId) {
        mAvatarResId = avatarResId;
    }
}

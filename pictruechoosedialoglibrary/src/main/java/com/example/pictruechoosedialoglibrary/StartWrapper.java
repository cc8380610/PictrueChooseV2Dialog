package com.example.pictruechoosedialoglibrary;

import android.content.Context;
import android.content.Intent;

import com.example.pictruechoosedialoglibrary.activity.AlbumActivity;
import com.example.pictruechoosedialoglibrary.activity.CameraActivity;


public class StartWrapper extends BasicWrapper<StartWrapper>{

    public StartWrapper(Context context) {
        super(context);
    }

    public void start() {
        CameraActivity.callBack = chooseCallBack;
        Intent intent = new Intent(mContext, CameraActivity.class);
        mContext.startActivity(intent);
    }

    public void startAlbum() {
        AlbumActivity.chooseCallBack = chooseCallBack;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        mContext.startActivity(intent);
    }
}

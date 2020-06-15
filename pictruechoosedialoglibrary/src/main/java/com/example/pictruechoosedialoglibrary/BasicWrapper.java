package com.example.pictruechoosedialoglibrary;

import android.content.Context;

import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;

public abstract class BasicWrapper<Returner extends BasicWrapper> {

    public OnPictureChooseCallBack chooseCallBack;
    public Context mContext;

    public BasicWrapper(Context context) {
        this.mContext = context;
    }

    public final Returner onResult(OnPictureChooseCallBack chooseCallBack) {
        this.chooseCallBack = chooseCallBack;
        return (Returner) this;
    }


    /**
     * Start up.
     */
    public abstract void start();
}

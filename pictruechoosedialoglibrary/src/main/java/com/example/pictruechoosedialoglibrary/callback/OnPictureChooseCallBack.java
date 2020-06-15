package com.example.pictruechoosedialoglibrary.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public interface OnPictureChooseCallBack {
    void result(Bitmap result);
    void resultDrawable(Drawable drawable);
    void dismiss();
    void errorMsg(String msg);
    void exception(Exception e);


}


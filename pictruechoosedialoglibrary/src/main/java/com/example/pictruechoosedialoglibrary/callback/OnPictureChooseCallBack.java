package com.example.pictruechoosedialoglibrary.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

public interface OnPictureChooseCallBack extends Serializable {
    void result(Bitmap result);
    void resultDrawable(Drawable drawable);
    void dismiss();
    void exception(Exception e);
}

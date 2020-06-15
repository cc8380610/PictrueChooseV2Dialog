package com.example.pictruechoosedialoglibrary;

import android.content.Context;

import com.example.pictruechoosedialoglibrary.callback.CameraInit;

public class Camera {

    public static StartWrapper init(Context content){
        return new StartWrapper(content);
    }
}

package com.example.pictruechoosedialoglibrary;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * 計算手機螢幕高度
 */
public class ScreenSizeUtils {

    private static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeight;

    public static ScreenSizeUtils getInstance(Context mContext) {
        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {
                instance = new ScreenSizeUtils(mContext);
            }
        }
        return instance;
    }

    private ScreenSizeUtils(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeight = dm.heightPixels;// 获取屏幕分辨率高度
    }

    //获取屏幕宽度
    public int getScreenWidth() {
        return screenWidth;
    }

    //获取屏幕高度
    public int getScreenHeight() {
        return screenHeight;
    }

    public static float convertPixelToDp(float px, Context context) {
        float dp = px / getDensity(context);
        return dp;
    }
    public static float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }
}

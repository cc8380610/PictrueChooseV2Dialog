package com.example.pictruechoosedialoglibrary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.pictruechoosedialoglibrary.activity.CameraActivity;
import com.example.pictruechoosedialoglibrary.callback.CustomerClickListener;
import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import java.io.IOException;
import java.util.ArrayList;

public class PictureChooseDialog {

    private static final String TAG = "PictureChooseDialog";
    private Activity activity;
    private Dialog dialog;
    private Uri uri;
    private OnPictureChooseCallBack callBack;

    public PictureChooseDialog(final Activity activity, final OnPictureChooseCallBack callBack){
        this.activity = activity;
        this.callBack = callBack;


        if(dialog == null) {

            Album.initialize(AlbumConfig.newBuilder(activity)
                    .setAlbumLoader(new MediaLoader())
                    .build());
            dialog = new Dialog(activity, R.style.NormalDialogStyle);
            View view = View.inflate(activity, R.layout.dialog_picture_choose, null);
            view.setElevation(15);
            TextView tvCamera = view.findViewById(R.id.tvCamera);
            TextView tvAlbum = view.findViewById(R.id.tvAlbum);
            TextView tvCancel = view.findViewById(R.id.tvCancel);

            CustomerRippleDrawable rippleDrawableNormal = new CustomerRippleDrawable(Color.parseColor("#ffffff"), 0);
            tvCamera.setBackground(rippleDrawableNormal.rippleDrawable());
            tvAlbum.setBackground(rippleDrawableNormal.rippleDrawable());
            tvCancel.setBackground(rippleDrawableNormal.rippleDrawable());

            tvAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Album.image(activity)//打開相冊
                            .singleChoice()
                            .widget(getWidget())
                            .camera(false)
                            .columnCount(3)
                            .onResult(new Action<ArrayList<AlbumFile>>() {
                                @Override
                                public void onAction(@NonNull ArrayList<AlbumFile> result) {

                                    uri = Uri.parse("file://" + result.get(0).getPath());
                                    try {
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                                        Drawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
                                        if(bitmap == null){
                                            return;
                                        }
                                        callBack.result(bitmap);
                                        callBack.resultDrawable(drawable);
                                    } catch (IOException e) {
                                        Log.d(TAG, "IOException" + e.getMessage());
                                        callBack.exception(e);
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .onCancel(new Action<String>() {
                                @Override
                                public void onAction(@NonNull String result) {
                                    callBack.errorMsg(result);
                                    dialog.dismiss();
                                }
                            })
                            .start();
                }
            });

            tvCamera.setOnClickListener(new CustomerClickListener() {
                @Override
                public void onOneClick(View v) {
                    Camera.init(activity)
                            .onResult(new OnPictureChooseCallBack() {
                                @Override
                                public void result(Bitmap result) {

                                }

                                @Override
                                public void resultDrawable(Drawable drawable) {
                                    callBack.resultDrawable(drawable);
                                    dialog.dismiss();
                                }

                                @Override
                                public void dismiss() {

                                }

                                @Override
                                public void errorMsg(String msg) {

                                }

                                @Override
                                public void exception(Exception e) {

                                }
                            }).start();
                }
            });



            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    callBack.dismiss();
                }
            });

            dialog.setContentView(view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            Window window = dialog.getWindow();
            if(window == null){
                return;
            }
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);

        }

    }

    private Widget getWidget(){
        return Widget.newLightBuilder(activity)
                .title("請選擇")
                .statusBarColor(Color.parseColor("#ffffff"))
                .toolBarColor(Color.parseColor("#ffffff")).build();
    }

    public void show() {

        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            int INTENT_CAMERA_ID_FRONT_REQUEST_CODE = 666;
            ActivityCompat.requestPermissions(activity, PERMISSION_REQUEST, INTENT_CAMERA_ID_FRONT_REQUEST_CODE);
            return;
        }

        if (!activity.isFinishing() && !isShowing()){
            dialog.show();
        }
    }

    /**
     * 相機權限請求
     **/
    private static String[] PERMISSION_REQUEST = {
            Manifest.permission.CAMERA
    };

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void dismiss(){
        if(!isShowing()){
            return;
        }
        dialog.dismiss();
    }
}

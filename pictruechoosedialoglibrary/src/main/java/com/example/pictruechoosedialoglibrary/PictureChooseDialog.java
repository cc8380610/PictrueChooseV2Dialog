package com.example.pictruechoosedialoglibrary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.pictruechoosedialoglibrary.callback.CustomerClickListener;
import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;

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
            dialog = new Dialog(activity, R.style.NormalDialogStyle);
        }
    }

    private void showAlbum(){
        Album.init(activity)
                .onResult(new OnPictureChooseCallBack() {
                    @Override
                    public void result(Bitmap result) {
                        callBack.result(result);
                        dialog.dismiss();
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
                        dialog.dismiss();
                    }

                    @Override
                    public void exception(Exception e) {
                        dialog.dismiss();
                    }
                }).startAlbum();
    }

    private void previewImage(final Drawable drawable,final Bitmap bitmap) {
        dialog.dismiss();
        View view = View.inflate(activity,R.layout.activity_picture_preview_check, null);
        view.findViewById(R.id.image).setBackground(drawable);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        TextView tvRestart = view.findViewById(R.id.tvRestart);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.result(bitmap);
                callBack.resultDrawable(drawable);
                dialog.dismiss();
            }
        });

        tvRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlbum();
            }
        });

        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        dialog.show();


    }


    public void show() {

        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            int INTENT_CAMERA_ID_FRONT_REQUEST_CODE = 666;
            ActivityCompat.requestPermissions(activity, PERMISSION_REQUEST, INTENT_CAMERA_ID_FRONT_REQUEST_CODE);
            return;
        }

        if (!activity.isFinishing() && !isShowing()){
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
                    showAlbum();
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

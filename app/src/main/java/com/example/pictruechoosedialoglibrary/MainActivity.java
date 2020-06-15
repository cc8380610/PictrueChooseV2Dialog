package com.example.pictruechoosedialoglibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;

public class MainActivity extends AppCompatActivity {

    private PictureChooseDialog pictureChooseDialog;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv);
        pictureChooseDialog = new PictureChooseDialog(this, new OnPictureChooseCallBack() {
            @Override
            public void result(Bitmap result) {

            }

            @Override
            public void resultDrawable(Drawable drawable) {
                imageView.setBackground(drawable);
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
        });
        findViewById(R.id.btn).setOnClickListener(view -> {
            pictureChooseDialog.show();
        });

    }

}

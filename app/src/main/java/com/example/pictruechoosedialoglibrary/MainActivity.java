package com.example.pictruechoosedialoglibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PictureChooseDialog pictureChooseDialog = new PictureChooseDialog(this, new PictureChooseDialog.OnPictureChooseCallBack() {
            @Override
            public void result(Bitmap result) {

            }

            @Override
            public void resultDrawable(Drawable drawable) {

            }

            @Override
            public void dismiss() {

            }

            @Override
            public void exception(Exception e) {

            }
        });
    }
}

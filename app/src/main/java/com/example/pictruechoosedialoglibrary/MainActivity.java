package com.example.pictruechoosedialoglibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PictureChooseDialog pictureChooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            pictureChooseDialog.show();
        });

        pictureChooseDialog = new PictureChooseDialog(this, new PictureChooseDialog.OnPictureChooseCallBack() {
            @Override
            public void result(Bitmap result) {

            }

            @Override
            public void resultDrawable(Drawable drawable) {
                findViewById(R.id.iv).setBackground(drawable);
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

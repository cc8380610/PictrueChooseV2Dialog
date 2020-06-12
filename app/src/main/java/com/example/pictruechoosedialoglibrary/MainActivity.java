package com.example.pictruechoosedialoglibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;

public class MainActivity extends AppCompatActivity implements OnPictureChooseCallBack {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PictureChooseDialog  pictureChooseDialog = new PictureChooseDialog(this,this);
        findViewById(R.id.btn).setOnClickListener(view -> {
            pictureChooseDialog.show();
        });
    }

    @Override
    public void result(Bitmap result) {
        Log.d("TTTTTTT","result: "+result);
    }

    @Override
    public void resultDrawable(Drawable drawable) {
        findViewById(R.id.iv).setBackground(drawable);
    }


    @Override
    public void dismiss() {

    }

    @Override
    public void errorMsg(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exception(Exception e) {
    }
}

package com.example.pictruechoosedialoglibrary.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pictruechoosedialoglibrary.R;
import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;
import com.yanzhenjie.album.Album;

import java.io.IOException;

public class AlbumActivity extends AppCompatActivity {

    private static final String TAG = "AlbumActivity";
    public static OnPictureChooseCallBack chooseCallBack;
    private TextView tvConfirm;
    private TextView tvRestart;
    private ImageView imageView;
    private int callBackCode = 66;
    private Activity activity;
    private Drawable resultDrawable;
    private Bitmap resultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        activity = this;

        openAlbum();



        tvConfirm = findViewById(R.id.tvConfirm);
        tvRestart = findViewById(R.id.tvRestart);
        imageView = findViewById(R.id.image);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultDrawable == null){
                    return;
                }
                chooseCallBack.resultDrawable(resultDrawable);
                chooseCallBack.result(resultBitmap);
                finish();
            }
        });

        tvRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
            }
        });
    }

    private void openAlbum(){
        Album.album(activity)
                .requestCode(callBackCode)//打開相冊
                .toolBarColor(ContextCompat.getColor(this, R.color.black333333))
                .statusBarColor(ContextCompat.getColor(this, R.color.black333333))
                .selectCount(1)//開啟的相冊最多選擇1張圖片
                .columnCount(3)//開啟的相冊一列放置3張圖片
                .camera(false)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != 1 && resultCode != RESULT_OK ) {
            return;
        }
        if(data == null){
            return;
        }
        for (int i = Album.parseResult(data).size() - 1; i >= 0; i--) {
            Uri uri = Uri.parse("file://" + Album.parseResult(data).get(i));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                Drawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
                if(bitmap == null){
                    return;
                }
                imageView.setBackground(drawable);
                resultDrawable = drawable;
                resultBitmap = bitmap;
            } catch (IOException e) {
                Log.d(TAG, "IOException" + e.getMessage());
            }

        }
    }
}

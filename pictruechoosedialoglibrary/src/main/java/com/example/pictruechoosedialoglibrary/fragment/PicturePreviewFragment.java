package com.example.pictruechoosedialoglibrary.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pictruechoosedialoglibrary.R;
import com.example.pictruechoosedialoglibrary.activity.CameraActivity;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.PictureResult;

import java.io.ByteArrayOutputStream;

public class PicturePreviewFragment extends Fragment {

    private static PictureResult picture;
    private CameraActivity cameraActivity;

    public static void setPictureResult(@Nullable PictureResult pictureResult) {
        picture = pictureResult;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_picture_preview, container, false);
        final PictureResult result = picture;
        final ImageView imageView = view.findViewById(R.id.image);
        if (result == null) {
            imageView.setImageDrawable(new ColorDrawable(Color.BLACK));
            Toast.makeText(getActivity(), "Can't preview this format: ",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                result.toBitmap(1000, 1000, new BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        cameraActivity.returnPictureBitmapResult(bitmap);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            } catch (UnsupportedOperationException e) {
                imageView.setImageDrawable(new ColorDrawable(Color.BLACK));
                Toast.makeText(getActivity(), "Can't preview this format: " + picture.getFormat(),
                        Toast.LENGTH_LONG).show();
            }

            if (result.isSnapshot()) {
                // Log the real size for debugging reason.
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(result.getData(), 0, result.getData().length, options);
                if (result.getRotation() % 180 != 0) {
                    Log.e("PicturePreview", "The picture full size is " + result.getSize().getHeight() + "x" + result.getSize().getWidth());
                } else {
                    Log.e("PicturePreview", "The picture full size is " + result.getSize().getWidth() + "x" + result.getSize().getHeight());
                }
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cameraActivity = (CameraActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setPictureResult(null);
    }
}
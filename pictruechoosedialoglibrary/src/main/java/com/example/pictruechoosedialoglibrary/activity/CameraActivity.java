package com.example.pictruechoosedialoglibrary.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.pictruechoosedialoglibrary.R;
import com.example.pictruechoosedialoglibrary.callback.CustomerClickListener;
import com.example.pictruechoosedialoglibrary.callback.OnPictureChooseCallBack;
import com.example.pictruechoosedialoglibrary.fragment.PicturePreviewFragment;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Preview;

/**
 * 相機
 */
public class CameraActivity extends AppCompatActivity {

    private CameraView camera;
    private static final CameraLogger LOG = CameraLogger.create("DemoApp");
    private long mCaptureTime;
    private ImageView btn;
    private TextView tvReTake, tvUpload, tv11, tv43, tvfull, tvCameraViewSize;
    private View view;
    private boolean isScanOrder;
    private PicturePreviewFragment fragment;
    public static OnPictureChooseCallBack callBack;
    private ConstraintSet constraintSet;
    private ConstraintLayout main;
    private boolean isOpen = false;
    private Bitmap bitmapResult;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        activity = this;
        constraintSet = new ConstraintSet();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;
        initView();
        initListener(width);
        cameraListener();
    }

    private void initView() {
        camera = findViewById(R.id.camera1);
        tvReTake = findViewById(R.id.tvReTake);
        tvUpload = findViewById(R.id.tvUpload);
        btn = findViewById(R.id.btn);
        main = findViewById(R.id.main);
        tv11 = findViewById(R.id.tv11);
        tv43 = findViewById(R.id.tv43);
        tvfull = findViewById(R.id.tvfull);
        view = findViewById(R.id.view);
        tvCameraViewSize = findViewById(R.id.tvCameraViewSize);
    }


    private void cameraListener() {
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(@NonNull CameraOptions options) {
                super.onCameraOpened(options);
                Log.d("MainActivity", "onCameraOpened");
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
                Log.d("MainActivity", "onCameraClosed");
            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);
                Log.d("MainActivity", "onPictureTaken");
                if (camera.isTakingVideo()) {
                    message("Captured while taking video. Size=" + result.getSize(), false);
                    return;
                }
                // This can happen if picture was taken with a gesture.
                long callbackTime = System.currentTimeMillis();
                if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
                LOG.w("onPictureTaken called! Launching activity. Delay:", callbackTime - mCaptureTime);
                fragment = new PicturePreviewFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.body, fragment)
                        .commit();
                isScanOrder = true;
                btn.setVisibility(View.GONE);
                tvCameraViewSize.setVisibility(View.GONE);
                tvReTake.setVisibility(View.VISIBLE);
                tvUpload.setVisibility(View.VISIBLE);

                PicturePreviewFragment.setPictureResult(result);
                mCaptureTime = 0;
                LOG.w("onPictureTaken called! Launched activity.");
            }

            @Override
            public void onZoomChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
                super.onZoomChanged(newValue, bounds, fingers);
                Log.d("MainActivity", "onZoomChanged");
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
                if (exception.getReason() == CameraException.REASON_UNKNOWN) {
                    Toast.makeText(CameraActivity.this, "不明原因錯誤,請重新嘗試", Toast.LENGTH_SHORT).show();
                } else if (exception.getReason() == CameraException.REASON_FAILED_TO_CONNECT) {
                    Toast.makeText(CameraActivity.this, "無法連接到相機服務, 請檢查權限或重新進入", Toast.LENGTH_SHORT).show();
                } else if (exception.getReason() == CameraException.REASON_DISCONNECTED) {
                    Toast.makeText(CameraActivity.this, "相機服務斷線請重新進入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initListener(final int width) {

        btn.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                capturePictureSnapshot();
            }
        });

        tvCameraViewSize.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                if (isOpen) {
                    closeAnimation();
                } else {
                    openAnimation();
                }
            }
        });

        tv11.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                tvCameraViewSize.setText(tv11.getText());
                closeAnimation();
                //1:1
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, width);
                camera.setLayoutParams(params);
                constraintSet.clone(main);
                constraintSet.connect(camera.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(camera.getId(), ConstraintSet.BOTTOM, R.id.view, ConstraintSet.TOP);
                constraintSet.applyTo(main);
            }
        });

        tv43.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                tvCameraViewSize.setText(tv43.getText());
                closeAnimation();
                int height = width / 4 * 3;
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                camera.setLayoutParams(params);
                constraintSet.clone(main);
                constraintSet.connect(camera.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(camera.getId(), ConstraintSet.BOTTOM, R.id.view, ConstraintSet.TOP);
                constraintSet.applyTo(main);
            }
        });

        tvfull.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                tvCameraViewSize.setText(tvfull.getText());
                closeAnimation();
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                camera.setLayoutParams(params);
                constraintSet.clone(main);
                constraintSet.connect(camera.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(camera.getId(), ConstraintSet.BOTTOM, R.id.view, ConstraintSet.TOP);
                constraintSet.applyTo(main);
            }
        });

        tvReTake.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                removePictureFg();
            }
        });

        tvUpload.setOnClickListener(new CustomerClickListener() {
            @Override
            public void onOneClick(View v) {
                Drawable drawable = new BitmapDrawable(activity.getResources(), bitmapResult);
                callBack.result(bitmapResult);
                callBack.resultDrawable(drawable);
                finish();
            }
        });

    }

    private void openAnimation() {
        isOpen = true;
        tv11.setVisibility(View.VISIBLE);
        tv43.setVisibility(View.VISIBLE);
        tvfull.setVisibility(View.VISIBLE);
        int bottomViewHeight = view.getMeasuredHeight();
        ObjectAnimator openAnimation = ObjectAnimator.ofFloat(tvfull, "translationY", -bottomViewHeight);
        openAnimation.setDuration(200);
        ObjectAnimator openAnimation2 = ObjectAnimator.ofFloat(tv43, "translationY", -(bottomViewHeight * 2));
        openAnimation2.setDuration(200);
        ObjectAnimator openAnimation3 = ObjectAnimator.ofFloat(tv11, "translationY", -(bottomViewHeight * 3));
        openAnimation3.setDuration(200);
        openAnimation.start();
        openAnimation2.start();
        openAnimation3.start();
    }

    private void closeAnimation() {
        isOpen = false;
        int bottomViewHeight = view.getMeasuredHeight();
        ObjectAnimator closeAnimation = ObjectAnimator.ofFloat(tvfull, "translationY", bottomViewHeight);
        closeAnimation.setDuration(200);
        ObjectAnimator closeAnimation2 = ObjectAnimator.ofFloat(tv43, "translationY", (bottomViewHeight * 2));
        closeAnimation2.setDuration(200);
        ObjectAnimator closeAnimation3 = ObjectAnimator.ofFloat(tv11, "translationY", (bottomViewHeight * 3));
        closeAnimation3.setDuration(200);
        closeAnimation.start();
        closeAnimation2.start();
        closeAnimation3.start();

        tv11.setVisibility(View.GONE);
        tv43.setVisibility(View.GONE);
        tvfull.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

    private void capturePictureSnapshot() {
        if (camera.isTakingPicture()) return;
        if (camera.getPreview() != Preview.GL_SURFACE) {
            message("Picture snapshots are only allowed with the GL_SURFACE preview.", true);
            return;
        }
        mCaptureTime = System.currentTimeMillis();
        message("Capturing picture snapshot...", false);
        camera.takePictureSnapshot();
    }

    private void message(@NonNull String content, boolean important) {
        if (important) {
            LOG.w(content);
        } else {
            LOG.i(content);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean valid = true;
        for (int grantResult : grantResults) {
            valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (valid && !camera.isOpened()) {
            camera.open();
        }
    }

    @Override
    public void onBackPressed() {

        if (isScanOrder) {
            removePictureFg();
        } else {
            super.onBackPressed();
        }

    }

    private void removePictureFg() {
        btn.setVisibility(View.VISIBLE);
        tvCameraViewSize.setVisibility(View.VISIBLE);
        tvReTake.setVisibility(View.GONE);
        tvUpload.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        isScanOrder = false;
    }

    public void returnPictureBitmapResult(Bitmap bitmap) {
        bitmapResult = bitmap;
    }
}
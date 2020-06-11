package com.example.pictruechoosedialoglibrary.callback;

import android.view.View;

public abstract class CustomerClickListener implements View.OnClickListener {
    private long lastClickTime = 0L;
    private int FAST_CLICK_DELAY_TIME = 1000;

    public CustomerClickListener() {
    }

    public CustomerClickListener(int fastClickDelayTime) {
        this.FAST_CLICK_DELAY_TIME = fastClickDelayTime;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClickTime > FAST_CLICK_DELAY_TIME) {
            onOneClick(v);
        }
        lastClickTime = System.currentTimeMillis();
    }
    public abstract void onOneClick(View v);


}

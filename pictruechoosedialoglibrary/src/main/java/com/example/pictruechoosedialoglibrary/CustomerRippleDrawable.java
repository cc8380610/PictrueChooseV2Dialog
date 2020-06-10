package com.example.pictruechoosedialoglibrary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;

public class CustomerRippleDrawable {
    private int mSelectedColor = Color.parseColor("#C4C4C4");
    private int mBackgroundColor;
    private Drawable mDrawable = null;
    private int mStrokeColor;
    private int mStrokeWidth;
    private int mRadius;

    /***
     * selectedColor  點選時漣漪的顏色( 預設設碼為 "#C4C4C4" 的灰色)
     * backgroundColor 背景顏色
     * strokeColor 線框顏色
     * strokeWidth 線框寬度
     * radius 圓角
     * drawable 背景圖片
     */
    public CustomerRippleDrawable() {
        mBackgroundColor = Color.parseColor("#ffffff");
        mRadius = 0;
        mStrokeColor = -1;
        mBackgroundColor = -1;
    }

    public CustomerRippleDrawable(int backgroundColor, int radius) {
        mBackgroundColor = backgroundColor;
        mRadius = radius;
        mStrokeColor = -1;
        mStrokeWidth = -1;
    }

    public CustomerRippleDrawable(int backgroundColor, int strokeColor, int strokeWidth, int radius) {
        mBackgroundColor = backgroundColor;
        mStrokeColor = strokeColor;
        mStrokeWidth = strokeWidth;
        mRadius = radius;
    }

    public CustomerRippleDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

    public RippleDrawable rippleDrawable() {
        int[][] stateList = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        };

        int[] stateColorList = new int[]{
                mSelectedColor,
                mSelectedColor,
                mSelectedColor,
                mSelectedColor
        };
        ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

        if (mDrawable == null) {
            GradientDrawable contentDrawable = new GradientDrawable();
            if (mStrokeColor != -1 && mStrokeWidth != -1) {//有邊框時
                contentDrawable.setColor(mBackgroundColor);
                contentDrawable.setCornerRadius(mRadius);
                contentDrawable.setStroke(mStrokeWidth, mStrokeColor);
                return new RippleDrawable(colorStateList, contentDrawable, contentDrawable);
            } else {//純背景色和圓角
                contentDrawable.setColor(mBackgroundColor);
                contentDrawable.setCornerRadius(mRadius);
                return new RippleDrawable(colorStateList, contentDrawable, contentDrawable);
            }
        } else {//底圖為圖片
            return new RippleDrawable(colorStateList, mDrawable, mDrawable);
        }
    }


    public void setSelectedColor(int selectedColor){
        mSelectedColor = selectedColor;
    }

    public void setmSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
    }

    public void setmBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public void setmStrokeColor(int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
    }

    public void setmStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }
}

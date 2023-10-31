package com.androidstepper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

/* *************************** */
//    code by:  Behzad Sadeghi
//    date : 2023-10-30
/* *************************** */

public class StepperView extends LinearLayoutCompat {

    private OnStepClick onStepClick;
    private final LinearLayoutCompat.LayoutParams linearLayoutParams =
            new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final LinearLayoutCompat.LayoutParams textviewLayoutParams = new LinearLayoutCompat.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private final LinearLayoutCompat.LayoutParams spacerLayoutParams = new LinearLayoutCompat.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private int currentActiveStep;
    private final List<TextView> stepLists = new ArrayList<TextView>();
    private int stepCount;
    private int activeStep;
    private final int fontFamily;
    private int activeStepBackgroundColor;
    private int activeStepTextColor;
    private int spacerBackgroundColor;
    private int activeStepFontSize;
    private int activeStepPaddingLeft;
    private int activeStepPaddingTop;
    private int activeStepPaddingRight;
    private int activeStepPaddingBottom;
    private int stepPaddingLeft;
    private int stepPaddingTop;
    private int stepPaddingRight;
    private int stepPaddingBottom;
    private int stepBackgroundColor;
    private int spacerHeight;

    public StepperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.HamoonBreadCrumb, 0, 0);
        try {
            stepCount = a.getInt(R.styleable.HamoonBreadCrumb_stepCount, 1);
            activeStep = a.getInt(R.styleable.HamoonBreadCrumb_activeStep, 1);
            fontFamily = a.getResourceId(R.styleable.HamoonBreadCrumb_android_fontFamily, 0);
            activeStepBackgroundColor = a.getResourceId(R.styleable.HamoonBreadCrumb_activeStepBackgroundColor, R.color.stepper_primary_dark);
            activeStepTextColor = a.getResourceId(R.styleable.HamoonBreadCrumb_activeStepTextColor, R.color.stepper_white);
            spacerBackgroundColor = a.getResourceId(R.styleable.HamoonBreadCrumb_spacerBackgroundColor, R.color.stepper_primary);
            activeStepFontSize = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_activeStepFontSize, 14);
            spacerHeight = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_spacerHeight, 8);
            activeStepPaddingLeft = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_activeStepPaddingLeft, 28);
            activeStepPaddingTop = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_activeStepPaddingTop, 16);
            activeStepPaddingRight = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_activeStepPaddingRight, 28);
            activeStepPaddingBottom = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_activeStepPaddingBottom, 16);
            stepPaddingLeft = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_stepPaddingLeft, 20);
            stepPaddingTop = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_stepPaddingTop, 0);
            stepPaddingRight = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_stepPaddingRight, 20);
            stepPaddingBottom = a.getDimensionPixelSize(R.styleable.HamoonBreadCrumb_stepPaddingBottom, 0);
            stepBackgroundColor = a.getResourceId(R.styleable.HamoonBreadCrumb_stepBackgroundColor, R.color.stepper_primary);
            currentActiveStep = activeStep;
        } finally {
            a.recycle();
        }

        setViewConstants();
        setupView();

    }

    public void setOnStepClickListener(OnStepClick onStepClick) {
        this.onStepClick = onStepClick;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
        if (stepCount == 1) {
            this.activeStep = 1;
        }
        resetView();
    }

    public void setActiveStep(int i) {
        TextView textView;
        if (i > 0 && i <= stepLists.size()) {
            currentActiveStep = i - 1;
            clearCurrentActiveSteps();
            textView = stepLists.get(currentActiveStep);
            textView.setText(String.valueOf(i));
        } else {
            currentActiveStep = stepLists.size() - 1;
            clearCurrentActiveSteps();
            textView = stepLists.get(currentActiveStep);
            textView.setText(String.valueOf(stepLists.size()));
        }
        textView.setBackground(setStepBackgroundColor(activeStepBackgroundColor));
        textView.setTextColor(ContextCompat.getColor(getContext(), activeStepTextColor));
        textView.setPadding(activeStepPaddingLeft, activeStepPaddingTop, activeStepPaddingRight, activeStepPaddingBottom);
    }

    public void setActiveStepPadding(int padding) {
        activeStepPaddingLeft = intToPx(padding);
        activeStepPaddingTop = intToPx(padding);
        activeStepPaddingRight = intToPx(padding);
        activeStepPaddingBottom = intToPx(padding);
        resetView();
    }

    public void setActiveStepPaddingLeft(int padding) {
        activeStepPaddingLeft = intToPx(padding);
        resetView();
    }

    public void setActiveStepPaddingTop(int padding) {
        activeStepPaddingTop = intToPx(padding);
        resetView();
    }

    public void setActiveStepPaddingRight(int padding) {
        activeStepPaddingRight = intToPx(padding);
        resetView();
    }

    public void setActiveStepPaddingBottom(int padding) {
        activeStepPaddingBottom = intToPx(padding);
        resetView();
    }

    public void setStepPadding(int padding) {
        stepPaddingLeft = intToPx(padding);
        stepPaddingTop = intToPx(padding);
        stepPaddingRight = intToPx(padding);
        stepPaddingBottom = intToPx(padding);
        resetView();
    }

    public void setStepPaddingLeft(int padding) {
        stepPaddingLeft = intToPx(padding);
        resetView();
    }

    public void setStepPaddingTop(int padding) {
        stepPaddingTop = intToPx(padding);
        resetView();
    }

    public void setStepPaddingRight(int padding) {
        stepPaddingRight = intToPx(padding);
        resetView();
    }

    public void setStepPaddingBottom(int padding) {
        stepPaddingBottom = intToPx(padding);
        resetView();
    }

    public void setSpacerHeight(int padding) {
        spacerHeight = intToPx(padding);
        spacerLayoutParams.height = spacerHeight;
        resetView();
    }

    public void clearCurrentActiveSteps() {
        if (stepLists.size() > 0) {
            TextView textView = stepLists.get(currentActiveStep);
            textView.setText(" ");
            textView.setBackground(setStepBackgroundColor(stepBackgroundColor));
            textView.setPadding(stepPaddingLeft, stepPaddingTop, stepPaddingRight, stepPaddingBottom);
        }
    }

    public void clearCurrentActiveStepByPosition(int position) {
        if (position >= 0 && position <= stepLists.size()) {
            TextView textView = stepLists.get(currentActiveStep);
            textView.setText(" ");
            textView.setBackground(setStepBackgroundColor(stepBackgroundColor));
            textView.setPadding(stepPaddingLeft, stepPaddingTop, stepPaddingRight, stepPaddingBottom);
        }
    }

    private void setupView() {
        for (int i = 0; i < stepCount; i++) {
            addTextView();
            if (i < stepCount - 1) {
                addSpacerView();
            }
        }
        setActiveStep(activeStep);
    }

    private void setViewConstants() {
        setLayoutParams(linearLayoutParams);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        float paddingDp = 10f;
        int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingDp, getResources().getDisplayMetrics());
        setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        linearLayoutParams.width = 0;
        spacerLayoutParams.height = spacerHeight;
        spacerLayoutParams.weight = 1;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addTextView() {
        final TextView textView = new TextView(getContext());
        textView.setText(" ");
        textView.setBackground(setStepBackgroundColor(stepBackgroundColor));
        if (fontFamily > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamily));
        textView.setId(View.generateViewId());
        textView.setPadding(stepPaddingLeft, stepPaddingTop, stepPaddingRight, stepPaddingBottom);
        textView.setLayoutParams(textviewLayoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, activeStepFontSize);
        stepLists.add(textView);
        textView.setOnClickListener(v -> {
            if (onStepClick != null) {
                onStepClick.onClick(textView, stepLists.indexOf(textView) + 1);
                onStepClick.onClick(textView, stepLists.indexOf(textView) + 1, currentActiveStep + 1);
            }
        });
        addView(textView);
    }

    private void addSpacerView() {
        View spacer = new View(getContext());
        spacer.setLayoutParams(spacerLayoutParams);
        spacer.setBackgroundColor(ContextCompat.getColor(getContext(), spacerBackgroundColor));
        addView(spacer);
    }

    private Drawable setStepBackgroundColor(int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.breadcrumb_step_back);
        if (color > 0 && drawable != null) {
            int iColor = ContextCompat.getColor(getContext(), color);

            int red   = (iColor & 0xFF0000) / 0xFFFF;
            int green = (iColor & 0xFF00) / 0xFF;
            int blue  = iColor & 0xFF;

            float[] matrix = { 0, 0, 0, 0, red,
                    0, 0, 0, 0, green,
                    0, 0, 0, 0, blue,
                    0, 0, 0, 1, 0 };

            ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
            drawable.setColorFilter(colorFilter);
        }
        return drawable;
    }

    private void resetView() {
        removeAllViews();
        stepLists.clear();
        setupView();
    }

    private int intToPx(int value) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value ,r.getDisplayMetrics()));
    }

    public interface OnStepClick{
        default void onClick(TextView textView, int currentPosition){}
        default void onClick(TextView textView, int currentPosition, int lastPosition){}
    }
}

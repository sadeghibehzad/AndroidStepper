package ir.isc.behzad.hamoonstepper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

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
    private final int stepCount;
    private final int activeStep;
    private final int fontFamily;
    private final int activeStepBackgroundColor;
    private final int activeStepTextColor;
    private final int spacerBackgroundColor;
    private final int activeStepFontSize;
    private final int activeStepPaddingLeft;
    private final int activeStepPaddingTop;
    private final int activeStepPaddingRight;
    private final int activeStepPaddingBottom;
    private final int stepPaddingLeft;
    private final int stepPaddingTop;
    private final int stepPaddingRight;
    private final int stepPaddingBottom;
    private final int stepBackgroundColor;
    private final int spacerHeight;

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
        setupView();
    }

    public void setOnStepClickListener(OnStepClick onStepClick) {
        this.onStepClick = onStepClick;
    }

    public void setActiveStep(int i) {
        if (i > 0 && i <= stepLists.size()) {
            this.currentActiveStep = i - 1;
            TextView textView = stepLists.get(this.currentActiveStep);
            textView.setText(String.valueOf(i));
            textView.setBackground(setStepBackgroundColor(activeStepBackgroundColor));
            textView.setTextColor(ContextCompat.getColor(getContext(), activeStepTextColor));
            textView.setPadding(activeStepPaddingLeft, activeStepPaddingTop, activeStepPaddingRight, activeStepPaddingBottom);
        } else {
            clearCurrentActiveSteps();
        }
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
        setLayoutParams(linearLayoutParams);
        setLayoutDirection(LAYOUT_DIRECTION_RTL);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        float paddingDp = 10f;
        int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingDp, getResources().getDisplayMetrics());
        setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        linearLayoutParams.width = 0;
        spacerLayoutParams.height = spacerHeight;
        spacerLayoutParams.weight = 1;

        for (int i = 0; i < stepCount; i++) {
            addTextView();
            if (i < stepCount - 1) {
                addSpacerView();
            }
        }
        setActiveStep(activeStep);
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
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStepClick != null) {
                    onStepClick.onClick(textView, stepLists.indexOf(textView) + 1);
                    onStepClick.onClick(textView, stepLists.indexOf(textView) + 1, currentActiveStep);
                }
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

    public interface OnStepClick{
        void onClick(TextView textView, int currentPosition);
        void onClick(TextView textView, int currentPosition, int lastPosition);
    }
}

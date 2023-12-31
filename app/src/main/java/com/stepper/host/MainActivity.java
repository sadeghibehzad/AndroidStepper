package com.stepper.host;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidstepper.StepperView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepperView view = findViewById(R.id.breadcrumb);

        view.setOnStepClickListener(new StepperView.OnStepClick() {
            @Override
            public void onClick(TextView textView, int currentPosition) {

            }

            @Override
            public void onClick(TextView textView, int currentPosition, int lastPosition) {

            }
        });
    }
}
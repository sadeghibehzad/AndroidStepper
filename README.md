# 🚀️ AndroidStepper

Android Library for showing stages of progress and creating a wizard-like step-through user interface that uses navigation components.

For any suggestion please be in touch with me: 😄

> [Email: sadeghibehzad2008@gmail.com](mailto:sadeghibehzad2008@gmail.com)
> [LinkedIn](https://www.linkedin.com/in/behzad-sadeghi-20a8b668/)

---

## [Jump to section](https://github.com/sadeghibehzad/AndroidStepper#jump_to_section)

* [Stepper Types](https://github.com/sadeghibehzad/AndroidStepper#stepper_types)
* [Features](https://github.com/sadeghibehzad/AndroidStepper#features)
* [Installation](https://github.com/sadeghibehzad/AndroidStepper#installation)
* [Usage](https://github.com/sadeghibehzad/AndroidStepper#usage)
* [Attributes](https://github.com/sadeghibehzad/AndroidStepper#attributes)
* [Step Click Action](https://github.com/sadeghibehzad/AndroidStepper#attributes)
* [TODO](https://github.com/sadeghibehzad/AndroidStepper#step_click_action#todo)

## [Stepper Types](https://github.com/sadeghibehzad/AndroidStepper#stepper_types)

* Bullet with numbers
* *Tick(planning)*
* *Custom Icon (planning)*

## [Features](https://github.com/sadeghibehzad/AndroidStepper#features)

* Custom Active Step Text Font
* Custom Active Step Text and Background Color
* Custom Spacer Background Color
* OnClick Event For Steps
* Support RTL and LTR Layouts

## [Installation](https://github.com/sadeghibehzad/AndroidStepper#installation)

Just add `AndroidStepper` Folder to yout project as a module.

## [Usage](https://github.com/sadeghibehzad/AndroidStepper#usage)

In your xml layout add below view:

```
      <com.androidstepper.StepperView
        android:id="@+id/stepper"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:layout_marginTop="16dp"
        app:stepCount="5"
        app:activeStep="2"
        app:activeStepBackgroundColor="@color/stepper_primary_dark"
        app:spacerBackgroundColor="@color/stepper_primary"
        app:activeStepTextColor="@color/stepper_white"
        app:activeStepFontSize="6sp"
        app:activeStepPaddingRight="14dp"
        app:activeStepPaddingLeft="14dp"
        app:activeStepPaddingTop="8dp"
        app:activeStepPaddingBottom="8dp"
        app:stepPaddingRight="8dp"
        app:stepPaddingLeft="8dp"
        app:stepPaddingTop="0dp"
        app:stepPaddingBottom="0dp"
        app:spacerHeight="4dp"
        android:fontFamily="@font/vazir_farsi" />
```

As you can see all configurable ttributeshas been shown and each one of them are described in below section.

## [Attributes](https://github.com/sadeghibehzad/AndroidStepper#attributes)


| **Name**                      | **Type**   | **Value** | **Description**                                                                   |
|-------------------------------|------------|-----------|-----------------------------------------------------------------------------------|
| app:stepCount                 | Integer    | 1-2147483647 | This value is for step count showing on screen                                    |
| app:activeStep                | Integer    | 1-2147483647 | This Value is for showing current active step                                     |
| app:activeStepBackgroundColor | Color(Int) | @Color    | This value is for specifying background color of active step                      |
| app:spacerBackgroundColor     | Color(Int) | @Color    | This value is for specifying background color spacer between each step            |
| app:activeStepTextColor       | Color(Int) | @Color    | This value is for specifying text color of active step                            |
| app:activeStepFontSize        | SP         | ex:6sp    | For active step font size you can set this value                                  |
| app:activeStepPaddingRight    | DP         | ex: 16dp  | For giving desired shape to active steps and padding content form right side      |
| app:activeStepPaddingLeft     | DP         | ex: 16dp  | For giving desired shape to active steps and padding content form left side       |
| app:activeStepPaddingTop      | DP         | ex: 16dp  | For giving desired shape to active steps and padding content form top side        |
| app:activeStepPaddingBottom   | DP         | ex: 16dp  | For giving desired shape to active steps and padding content form bottom side     |
| app:stepPaddingRight          | DP         | ex: 16dp  | For giving desired shape to NOT active steps and padding content form right side  |
| app:stepPaddingLeft           | DP         | ex: 16dp  | For giving desired shape to NOT active steps and padding content form left side   |
| app:stepPaddingTop            | DP         | ex: 16dp  | For giving desired shape to NOT active steps and padding content form top side    |
| app:stepPaddingBottom         | DP         | ex: 16dp  | For giving desired shape to NOT active steps and padding content form bottom side |
| app:spacerHeight              | DP         | ex: 16dp  | This value defines spacer line height between steps                               |
| android:fontFamily            | Font       | @font     | This value can be set for font type of active step text                           |

## [Step Click Action](https://github.com/sadeghibehzad/AndroidStepper#step_click_action)

User can have an action for on each step click which can be useful in some cases:

```
       StepperView view = findViewById(R.id.breadcrumb);

        view.setOnStepClickListener(new StepperView.OnStepClick() {
            @Override
            public void onClick(TextView textView, int currentPosition) {

            }

            @Override
            public void onClick(TextView textView, int currentPosition, int lastPosition) {

            }
        });
```

First implementation of above click listener gives you current clicked `TextView` and `currentPosition` of step.
Second implementation will gives you current clicked `TextView` and both `currentPosition` and `lastPosition`.

## [TODO](https://github.com/sadeghibehzad/AndroidStepper#todo)

* Adding Icons for steps
* Adding Animations

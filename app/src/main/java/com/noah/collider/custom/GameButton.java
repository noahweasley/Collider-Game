package com.noah.collider.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class GameButton extends AppCompatButton {

    public GameButton(@NonNull Context context) {
        super(context);
    }

    public GameButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchAction = event.getAction();
        int animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        if (touchAction == MotionEvent.ACTION_DOWN) {
            animate().scaleX(0.8f).scaleY(0.8f).setDuration(animDuration);
        } else if (touchAction == MotionEvent.ACTION_UP) {
            animate().scaleX(1.0f).scaleY(1.0f).setDuration(animDuration);
            performClick();
        } else if (touchAction == MotionEvent.ACTION_CANCEL) {
            animate().scaleX(1.0f).scaleY(1.0f).setDuration(animDuration);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}

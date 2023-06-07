package com.noah.collider.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class BlinkingTextView extends AppCompatTextView {
    private ObjectAnimator animator = null;

    public BlinkingTextView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public BlinkingTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BlinkingTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressWarnings("unused")
    private void init(Context context, AttributeSet attributeSet) {
        animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0.8f, 0.0f);
        animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(Animation.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            animator.start();
        } else {
            animator.cancel();
        }
    }
}

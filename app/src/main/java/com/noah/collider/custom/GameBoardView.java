package com.noah.collider.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.noah.collider.R;

import java.util.List;

@SuppressWarnings("unused")
public class GameBoardView extends View {
    private Paint ballPaint, fingerPaint;
    private int ballCount;
    private int ballRadius;
    private List<Point> coordinates;
    private float touchX = -1f;
    private float touchY = -1f;

    public GameBoardView(Context context) {
        super(context);
        init();
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ballPaint = new Paint();
        ballPaint.setStyle(Paint.Style.FILL);
        ballPaint.setColor(ContextCompat.getColor(getContext(), R.color.royal_purple));

        fingerPaint = new Paint();
        fingerPaint.setStyle(Paint.Style.FILL);
        fingerPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // paint board
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.black));

        if (touchX != -1f && touchY != -1f)
            canvas.drawCircle(touchX, touchY, 15, ballPaint);

        // draw initial circles
        for (int i = 0; i < ballCount; i++) {
            canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, ballRadius, ballPaint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchAction = event.getAction();
        if (touchAction == MotionEvent.ACTION_DOWN || touchAction == MotionEvent.ACTION_MOVE) {
            this.touchX = event.getX();
            this.touchY = event.getY();
        } else if (touchAction == MotionEvent.ACTION_UP) {
            this.touchY = this.touchX = -1f;
        }
        invalidate();

        return true;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    public int getBallCount() {
        return ballCount;
    }

    public int getBallRadius() {
        return ballRadius;
    }
}

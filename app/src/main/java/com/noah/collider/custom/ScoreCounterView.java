package com.noah.collider.custom;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * A normal TextView sometimes :) but was used as a Score Counter
 */
@SuppressWarnings("unused")
public class ScoreCounterView extends AppCompatTextView implements Runnable {
    private long counter = 0L;
    private long initialCount = 0L;
    private volatile boolean wantToStopOp;
    private boolean isTimerRunning;
    private Thread timerThread;
    private OnScoreUpdateListener listener;
    private int scoreIncrementInterval = 500;

    public ScoreCounterView(Context context) {
        super(context);
        init(context, null);
    }

    public ScoreCounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScoreCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    /**
     * @return the status of the countdown timer
     */
    public boolean isUserScoreIncrements() {
        return isTimerRunning;
    }

    /**
     * Stops the timer and resets it
     */
    public void stopUserScoreCounter() {
        pauseScoreCounter();
        pause();
        timerThread = null;
        isTimerRunning = false;
        setCounter(0L);
    }

    private void pause() {
        wantToStopOp = true;
        isTimerRunning = false;
    }

    /**
     * Pause the timer but timer is still active. All callbacks would not be invoked.
     */
    public void pauseScoreCounter() {
        isTimerRunning = false;
    }

    /**
     * Resumes execution of timer. All callbacks would be invoked now.
     */
    public void resumeScoreCounter() {
        isTimerRunning = true;
    }

    /**
     * Sets the count-down timer update listener
     *
     * @param listener the listener in which it's callback functions would to be invoked
     */
    public void setOnScoreUpdateListener(OnScoreUpdateListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the counter start decrement value
     *
     * @param millis the start decrement in milli-seconds
     */
    public void setCounter(long millis) {
        this.counter = millis;
        this.initialCount = millis;
    }

    /**
     * If the count-down timer was stopped, this would restart it, and the timer starts counting from
     * value set at {@link this#setCounter(long)}
     */
    public void restart() {
        if (!isTimerRunning) {
            this.counter = this.initialCount;
            if (timerThread == null) {
                timerThread = new Thread(this);
                timerThread.start();
            }
            resumeScoreCounter();
        } else {
            throw new IllegalStateException("timer is already running");
        }
    }

    public void setScoreIncrementInterval(int scoreIncrementInterval) {
        if (scoreIncrementInterval < 1) throw new IllegalStateException(scoreIncrementInterval + " is very small");
        this.scoreIncrementInterval = scoreIncrementInterval;
    }

    public int getScoreIncrementInterval() {
        return scoreIncrementInterval;
    }

    /**
     * Call this to start running the timer. Count-down timer never runs, until this is called.
     */
    public void start() {
        if (!isTimerRunning) {
            if (timerThread == null) {
                timerThread = new Thread(this);
                timerThread.start();
                isTimerRunning = true;
            }
        } else {
            throw new IllegalStateException("timer is already running");
        }
    }

    @Override
    public void run() {
        do {
            if (isTimerRunning) {
                SystemClock.sleep(scoreIncrementInterval);

                if (getHandler() != null)
                    getHandler().post(() -> {
                        if (listener != null) {
                            listener.onScoreIncrement(counter);
                        }
                        setText(String.valueOf(counter++));
                    });
            }
        } while (!wantToStopOp);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        listener = null;
    }

    /**
     * Callbacks that would be invoked when user score increments
     */
    public interface OnScoreUpdateListener {
        void onScoreIncrement(long userScore);
    }
}

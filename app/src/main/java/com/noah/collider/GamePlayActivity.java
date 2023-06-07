package com.noah.collider;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.noah.collider.custom.GameBoardView;
import com.noah.collider.custom.ScoreCounterView;

@SuppressWarnings("unused")
public class GamePlayActivity extends AppCompatActivity {
    private boolean isGameActive;
    private long userScore;
    private ScoreCounterView userScoreCounter;
    private TextView tv_game_activity_status;
    private GameBoardView gameBoard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        gameBoard = findViewById(R.id.game_board);
        tv_game_activity_status = findViewById(R.id.game_activity_status);
        userScoreCounter = findViewById(R.id.user_score);
        userScoreCounter.setOnScoreUpdateListener(userScore -> this.userScore = userScore);

        gameBoard.setBallCount(2);
        gameBoard.setBallRadius(25);

        pauseGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            pauseGame();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startGameplay();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        userScoreCounter.pauseScoreCounter();
        isGameActive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGameActive = true;
        userScoreCounter.resumeScoreCounter();
    }

    @Override
    protected void onDestroy() {
        userScoreCounter.stopUserScoreCounter();
        super.onDestroy();
    }

    private void pauseGame() {
        isGameActive = false;
        tv_game_activity_status.setVisibility(View.VISIBLE);
        userScoreCounter.pauseScoreCounter();
    }

    private void startGameplay() {
        isGameActive = true;
        tv_game_activity_status.setVisibility(View.GONE);

        if (!userScoreCounter.isUserScoreIncrements()) {
            userScoreCounter.resumeScoreCounter();
        } else {
            userScoreCounter.start();
        }

    }
}

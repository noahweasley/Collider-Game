package com.noah.collider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Collider);
        setContentView(R.layout.activity_main_menu);

        Button btn_startGame = findViewById(R.id.button);
        btn_startGame.setOnClickListener(v -> startActivity(new Intent(this, GamePlayActivity.class)));
    }

}

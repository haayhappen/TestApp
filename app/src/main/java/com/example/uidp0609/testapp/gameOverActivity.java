package com.example.uidp0609.testapp;

import android.app.Activity;
import android.os.Bundle;

public class gameOverActivity extends Activity {

    private int finalscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle b = getIntent().getExtras();
        finalscore = b.getInt("gamescore");
    }


}

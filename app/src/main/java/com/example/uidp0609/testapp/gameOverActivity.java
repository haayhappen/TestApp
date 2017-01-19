package com.example.uidp0609.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class gameOverActivity extends Activity {

    public int score;
    private TextView finalScoreTextView;
    private Button playagainbutton;
    private Button backToMenuButton;

    private static final String TAG = "Gameoveractivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle b = getIntent().getExtras();
        score = b.getInt("gamescore");

        Handler scoreHandler = new Handler();
        scoreHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                growAnimScore();
            }
        }, 500);

        Handler buttonHandler = new Handler();
        buttonHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                growAnimButtons();
            }
        }, 1000);
        Handler button1Handler = new Handler();
        buttonHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                growAnimButtons1();
            }
        }, 1500);

    }

    private void growAnimScore(){
            Animation a = AnimationUtils.loadAnimation(this, R.anim.grow);
            a.reset();
        finalScoreTextView = (TextView) findViewById(R.id.finalscore);
        finalScoreTextView.setVisibility(View.VISIBLE);
        finalScoreTextView.clearAnimation();
        finalScoreTextView.startAnimation(a);

        try{
           // finalScoreTextView = (TextView) findViewById(R.id.finalscore);
            finalScoreTextView.setText(score);
        }catch (Exception ex) {
            Log.v(TAG,ex.getMessage());
        }
    }
    private void growAnimButtons(){
            Animation a = AnimationUtils.loadAnimation(this, R.anim.grow);
            a.reset();
        playagainbutton = (Button) findViewById(R.id.playagainbutton);
        playagainbutton.setVisibility(View.VISIBLE);
        playagainbutton.clearAnimation();
        playagainbutton.startAnimation(a);
    }
    private void growAnimButtons1(){
        Animation a = AnimationUtils.loadAnimation(this, R.anim.grow);
        a.reset();
        backToMenuButton = (Button) findViewById(R.id.backtomenubutton);
        backToMenuButton.setVisibility(View.VISIBLE);
        backToMenuButton.clearAnimation();
        backToMenuButton.startAnimation(a);

    }
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    public void onPlayAgainPressed(View view) {
        Intent intent = new Intent(this, gameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }
    public void onBackToMenuPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }

    @Override
    public void onBackPressed() {
    }
}

package com.example.uidp0609.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class gameOverActivity extends Activity {

    private int finalScore = 0;
    private Handler handler = new Handler();
    private TextView finalScoreTextView;
    private Button playagainbutton;
    private Button backToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Handler scoreHandler = new Handler();
        scoreHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                growAnim();
                // Actions to do after 1 second
                //finalScoreTextView = (TextView) findViewById(R.id.finalscore);
                //finalScoreTextView.setText("Score");
                //finalScoreTextView.setVisibility(View.VISIBLE);
            }
        }, 1000);

        Handler buttonHandler = new Handler();
        buttonHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                // Actions to do after 1 second
                playagainbutton = (Button) findViewById(R.id.playagainbutton);
                playagainbutton.setVisibility(View.VISIBLE);
                backToMenuButton = (Button) findViewById(R.id.backtomenubutton);
                backToMenuButton.setVisibility(View.VISIBLE);
            }
        }, 2000);

        Bundle b = getIntent().getExtras();
        finalScore = b.getInt("gamescore");

        //finalScoreTextView = (TextView) findViewById(R.id.finalscore);
        //finalScoreTextView.setText("TEST");
    }

    private void growAnim(){
            Animation a = AnimationUtils.loadAnimation(this, R.anim.grow);
            a.reset();
        finalScoreTextView = (TextView) findViewById(R.id.finalscore);
        finalScoreTextView.setVisibility(View.VISIBLE);
        finalScoreTextView.clearAnimation();
        finalScoreTextView.startAnimation(a);
    }
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}

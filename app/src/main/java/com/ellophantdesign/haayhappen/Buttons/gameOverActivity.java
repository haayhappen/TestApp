package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;


public class gameOverActivity extends Activity {

    private static final String TAG = "Gameoveractivity";
    public int score;
    private TextView finalScoreTextView;
    private Button playagainbutton;
    private Button backToMenuButton;
    int lastscore=0;
    boolean doneloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //get ads
        NativeExpressAdView adView =(NativeExpressAdView)findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());

        Bundle b = getIntent().getExtras();
        score = b.getInt("gamescore");
try{
    //get last score:
    SharedPreferences sharedPref = getSharedPreferences("scores", Context.MODE_PRIVATE);
    lastscore = sharedPref.getInt("score",0);

}catch (Exception e){
    lastscore = 0;
}
        //check if score ist higher than lastscore
        if (score > lastscore ){
            //if so:
            //setting preferences
            SharedPreferences prefs = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("score", score);
            editor.commit();
        }

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
            finalScoreTextView.setText(score+"");
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

        TextView tvads =(TextView) findViewById(R.id.catchemall);
        tvads.setVisibility(View.VISIBLE);
        tvads.clearAnimation();
        tvads.startAnimation(a);

    }
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    public void onPlayAgainPressed(View view) {
        Intent intent = new Intent(this, gamemodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }
    public void onBackToMenuPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // Do something in response to button
    }

    @Override
    public void onBackPressed() {
    }
}

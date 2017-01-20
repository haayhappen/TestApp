package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class scoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        TextView scoretextview = (TextView) findViewById(R.id.scoretextview);
        SharedPreferences sharedPref = getSharedPreferences("scores", Context.MODE_PRIVATE);
        int score = sharedPref.getInt("score",0);
        scoretextview.setText(score+"");
    }

    public void switchtohome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // Do something in response to button
    }

    public void deletehighscore(View view) {
        SharedPreferences prefs = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", 0);
        editor.commit();
        TextView scoretextview = (TextView) findViewById(R.id.scoretextview);
        scoretextview.setText(0+"");
        // Do something in response to button
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
    }
}

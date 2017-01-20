package com.example.uidp0609.testapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class gameActivity extends Activity {

    TextView timerTextView;
    int lastrandomused = 5;
    int random;
    int lifes = 3;
    CountDownTimer timer;
    //counter definitions
    private int gamescore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.haayhappen.uidp0609.testapp.R.layout.activity_game);

        timerTextView = (TextView) findViewById(com.haayhappen.uidp0609.testapp.R.id.timerTextView);

        final Button startbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_start_game);

        startbutton.setText("Start Game!");
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeScreenColor();
                TextView textView_instructions = (TextView) findViewById(com.haayhappen.uidp0609.testapp.R.id.textview_instructions);
                textView_instructions.setVisibility(View.GONE);
                //set what happens after start
                View startbuttonview = findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

               timer = new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timerTextView.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        switchToGameOverScreen();
                        finish();
                    }

                }.start();

            }
        });


        Button redbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_red);
        redbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                //textView_instructions.setText("Red pressed");

                if (checkRedColor()) {
                    changeScreenColor();
                    gamescore += 100;
                    updateGameScore();
                    //add score etc.
                } else {
                    lifes -= 1;
                    checkLifes();

                }
            }
        });

        Button beigebutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_beige);
        beigebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
               // textView_instructions.setText("Beige pressed");

                if (checkBeigeColor()) {
                    changeScreenColor();
                    //add score etc.
                    gamescore += 100;
                    updateGameScore();
                } else {
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

        Button yellowbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_yellow);
        yellowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
               // textView_instructions.setText("yellow pressed");

                if (checkYellowColor()) {
                    changeScreenColor();
                    gamescore += 100;
                    //add score etc.
                    updateGameScore();
                } else {
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

        Button greenbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_green);
        greenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                //textView_instructions.setText("green pressed");

                if (checkGreenColor()) {
                    changeScreenColor();
                    gamescore += 100;
                    //add score etc.
                    updateGameScore();
                } else {
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

    }

    private void switchToGameOverScreen() {
        Intent intent = new Intent(this, gameOverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle b = new Bundle();
        b.putInt("gamescore", gamescore); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        timer.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public void changeScreenColor() {

        Button screencolor = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.screencolor);
        int redColor = ContextCompat.getColor(getApplicationContext(), com.haayhappen.uidp0609.testapp.R.color.colorRed);
        int beigeColor = ContextCompat.getColor(getApplicationContext(), com.haayhappen.uidp0609.testapp.R.color.colorBeige);
        int yellowColor = ContextCompat.getColor(getApplicationContext(), com.haayhappen.uidp0609.testapp.R.color.colorYellow);
        int greenColor = ContextCompat.getColor(getApplicationContext(), com.haayhappen.uidp0609.testapp.R.color.colorGreen);

        random = getRandom();

        while (random == lastrandomused) {
            random = getRandom();
        }

        switch (random) {
            case 0:
                screencolor.setBackgroundColor(redColor);
                break;
            case 1:
                screencolor.setBackgroundColor(beigeColor);
                break;
            case 2:
                screencolor.setBackgroundColor(yellowColor);
                break;
            case 3:
                screencolor.setBackgroundColor(greenColor);
                break;
        }
        lastrandomused = random;
    }

    public boolean checkRedColor() {
        Button screencolor = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.screencolor);
        Button redbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_red);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) redbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkBeigeColor() {
        Button screencolor = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.screencolor);
        Button beigebutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_beige);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) beigebutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkYellowColor() {

        Button yellowbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_yellow);
        Button screencolor = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.screencolor);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) yellowbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkGreenColor() {
        Button greenbutton = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.btn_green);
        Button screencolor = (Button) findViewById(com.haayhappen.uidp0609.testapp.R.id.screencolor);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) greenbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    private void updateGameScore() {
        TextView gamescoretextview = (TextView) findViewById(com.haayhappen.uidp0609.testapp.R.id.gamescore);
        try {
            gamescoretextview.setText(this.gamescore);
        } catch (Exception ex) {
            //Hack users facebook, whatsapp and gmail account because it shouldfucking work
        }
    }

    public void checkLifes() {
        TextView lifestextview = (TextView) findViewById(com.haayhappen.uidp0609.testapp.R.id.lifestextview);
        if (lifes == 0) {
            switchToGameOverScreen();
            finish();
        }else {
            lifestextview.setText(lifes+"");
        }
    }

    private int getRandom() {
        Random random = new Random();
        return random.nextInt(4);
    }
}

























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
import java.util.Timer;

public class gameActivity extends Activity {

    //view definitions
    // private ImageView imgviewscreencolor = (ImageView) findViewById(R.id.screencolor); ;

    TextView timerTextView;
    int lastrandomused = 5;
    int random;
    int lifes = 3;
    //Button definitions
    private Button redbutton;
    private Button beigebutton;
    private Button yellowbutton;
    private Button greenbutton;
    //counter definitions
    private int gamescore = 0;
    private Timer timecounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        final Button startbutton = (Button) findViewById(R.id.btn_start_game);

        startbutton.setText("Start Game!");
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeScreenColor();
                //set what happens after start
                View startbuttonview = findViewById(R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timerTextView.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        Intent intent = new Intent(getApplicationContext(), gameOverActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle b = new Bundle();
                        b.putInt("gamescore", gamescore); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                        //gaem over intent
                        //timerTextView.setText("done!");
                        //save score
                        //show game over screen with score
                    }

                }.start();

            }
        });


        Button redbutton = (Button) findViewById(R.id.btn_red);
        redbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setText("Red pressed");

                if (checkRedColor()) {
                    changeScreenColor();
                    //add score etc.
                } else {
                    //TODO life - 1
                    checkLifes();

                }
            }
        });

        Button beigebutton = (Button) findViewById(R.id.btn_beige);
        beigebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setText("Red pressed");

                if (checkBeigeColor()) {
                    changeScreenColor();
                    //add score etc.
                } else {
                    //TODO life - 1
                    checkLifes();
                }
            }
        });

        Button yellowbutton = (Button) findViewById(R.id.btn_yellow);
        yellowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setText("yellow pressed");

                if (checkYellowColor()) {
                    changeScreenColor();
                    //add score etc.
                } else {
                    //TODO life - 1
                    checkLifes();
                }
            }
        });

        Button greenbutton = (Button) findViewById(R.id.btn_green);
        greenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setText("green pressed");

                if (checkGreenColor()) {
                    changeScreenColor();
                    //add score etc.
                } else {
                    //TODO life - 1
                    checkLifes();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void changeScreenColor() {

        Button screencolor = (Button) findViewById(R.id.screencolor);

        int redColor = ContextCompat.getColor(getApplicationContext(), R.color.colorRed);
        int beigeColor = ContextCompat.getColor(getApplicationContext(), R.color.colorBeige);
        int yellowColor = ContextCompat.getColor(getApplicationContext(), R.color.colorYellow);
        int greenColor = ContextCompat.getColor(getApplicationContext(), R.color.colorGreen);
/*
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            imgviewscreencolor.setBackgroundDrawable(redColor);
        } else {
            setBackground();
        }

        */

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
        Button screencolor = (Button) findViewById(R.id.screencolor);
        Button redbutton = (Button) findViewById(R.id.btn_red);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) redbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkBeigeColor() {
        Button screencolor = (Button) findViewById(R.id.screencolor);
        Button beigebutton = (Button) findViewById(R.id.btn_beige);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) beigebutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkYellowColor() {

        Button yellowbutton = (Button) findViewById(R.id.btn_yellow);
        Button screencolor = (Button) findViewById(R.id.screencolor);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) yellowbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    public boolean checkGreenColor() {
        Button greenbutton = (Button) findViewById(R.id.btn_green);
        Button screencolor = (Button) findViewById(R.id.screencolor);

        ColorDrawable screendrawable = (ColorDrawable) screencolor.getBackground();
        ColorDrawable buttondrawable = (ColorDrawable) greenbutton.getBackground();

        if (screendrawable.getColor() == buttondrawable.getColor()) {
            return true;
        }
        return false;
    }

    private void updateGameScore() {
        TextView gamescoretextview = (TextView) findViewById(R.id.gamescore);
        try {
            gamescoretextview.setText(this.gamescore);
        } catch (Exception ex) {
            //Hack users facebook, whatsapp and gmail account because it shouldfucking work
        }

    }

    public void checkLifes() {
        if (lifes == 0) {
            //EXIT GAME

            //TODO link to gameover activity when lifes equals 0
            //Intent intent = new Intent(this, gameOverActivity.class);
            //startActivity(intent);
            finish();
        }

    }

    private int getRandom() {

        Random random = new Random();
        return random.nextInt(4);
    }


}

























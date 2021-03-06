package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class gameActivity extends Activity {

    private FirebaseAnalytics mFirebaseAnalytics;

    TextView timerTextView;
    int lastrandomused = 5;
    int random;
    int lifes = 3;
    CountDownTimer timer;
    //counter definitions
    int gamescore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button redbutton = (Button) findViewById(R.id.btn_red);
        Button beigebutton = (Button) findViewById(R.id.btn_beige);
        Button yellowbutton = (Button) findViewById(R.id.btn_yellow);
        Button greenbutton = (Button) findViewById(R.id.btn_green);

        redbutton.setEnabled(false);
        beigebutton.setEnabled(false);
        yellowbutton.setEnabled(false);
        greenbutton.setEnabled(false);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        final Button startbutton = (Button) findViewById(R.id.btn_start_game);

        startbutton.setText("Start Game!");
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button redbutton = (Button) findViewById(R.id.btn_red);
                Button beigebutton = (Button) findViewById(R.id.btn_beige);
                Button yellowbutton = (Button) findViewById(R.id.btn_yellow);
                Button greenbutton = (Button) findViewById(R.id.btn_green);
                redbutton.setEnabled(true);
                beigebutton.setEnabled(true);
                yellowbutton.setEnabled(true);
                greenbutton.setEnabled(true);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, startbutton.toString());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Game start");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                changeScreenColor();
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setVisibility(View.GONE);
                //set what happens after start
                View startbuttonview = findViewById(R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

               timer = new CountDownTimer(30000, 1000) {

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
                    vibrate();
                    lifes -= 1;
                    checkLifes();

                }
            }
        });

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
                    vibrate();
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

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
                    vibrate();
                    lifes -= 1;
                    checkLifes();
                }
            }
        });


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
                    vibrate();
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
        b.putInt("gamescore", gamescore);
        intent.putExtras(b);
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

        Button screencolor = (Button) findViewById(R.id.screencolor);
        int redColor = ContextCompat.getColor(getApplicationContext(), R.color.colorRed);
        int beigeColor = ContextCompat.getColor(getApplicationContext(), R.color.colorBeige);
        int yellowColor = ContextCompat.getColor(getApplicationContext(), R.color.colorYellow);
        int greenColor = ContextCompat.getColor(getApplicationContext(), R.color.colorGreen);

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
            gamescoretextview.setText(this.gamescore+"");
        } catch (Exception ex) {
            //Hack users facebook, whatsapp and gmail account because it shouldfucking work
        }
    }

    public void checkLifes() {
        TextView lifestextview = (TextView) findViewById(R.id.lifestextview);
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

    public void vibrate(){

        if (MainActivity.vibration){
            Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }
        TextView tv = (TextView) findViewById(R.id.textview_instructions);
        tv.setVisibility(View.VISIBLE);
        tv.setText("NOPE!");
        tv.setTextSize(30);
        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TextView tv = (TextView) findViewById(R.id.textview_instructions);
                        tv.setVisibility(View.GONE);
                    }
                });
            }
        }, 500);
    }

}

























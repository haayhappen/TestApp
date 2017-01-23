package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class icongameActivity extends Activity {

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
        setContentView(R.layout.activity_icongame);

        Button burgerButton = (Button) findViewById(R.id.burgerButton);
        Button lettuceButton = (Button) findViewById(R.id.lettuceButton);
        Button eggButton = (Button) findViewById(R.id.eggButton);
        Button cupcakeButton = (Button) findViewById(R.id.cupcakeButton);

        burgerButton.setEnabled(false);
        lettuceButton.setEnabled(false);
        eggButton.setEnabled(false);
        cupcakeButton.setEnabled(false);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        final Button startbutton = (Button) findViewById(R.id.btn_start_game);

        startbutton.setText("Start Game!");
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //set what happens after start

                //Enable IconButtons to play
                Button burgerButton = (Button) findViewById(R.id.burgerButton);
                Button lettuceButton = (Button) findViewById(R.id.lettuceButton);
                Button eggButton = (Button) findViewById(R.id.eggButton);
                Button cupcakeButton = (Button) findViewById(R.id.cupcakeButton);

                burgerButton.setEnabled(true);
                lettuceButton.setEnabled(true);
                eggButton.setEnabled(true);
                cupcakeButton.setEnabled(true);

                //Hide Instructions and start button
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setVisibility(View.GONE);
                View startbuttonview = findViewById(R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

                //change screen icon for the first time
                changeScreenIcon();
                //setup timer
                timer = new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timerTextView.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        //when timer is finished switches to gameover activity
                        switchToGameOverScreen();
                        finish();
                    }

                }.start();

            }
        });

        burgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if the burgerbutton was actually pressed
                //yes-->
                if (checkBurgerIcon()) {
                    //change screen icon
                    changeScreenIcon();
                    //add score
                    gamescore += 100;
                    //update score
                    updateGameScore();
                    //no-->
                } else {
                    //vibrate
                    vibrate();
                    //subtract a life
                    lifes -= 1;
                    //check if player is dead
                    checkLifes();
                }
            }
        });

        lettuceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLettuceIcon()) {
                    changeScreenIcon();
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

        eggButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEggIcon()) {
                    changeScreenIcon();
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

        cupcakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCupcakeIcon()) {
                    changeScreenIcon();
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
        //Intent to game over activity
        Intent intent = new Intent(this, gameOverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //pass score to gameover activity
        Bundle b = new Bundle();
        b.putInt("gamescore", gamescore);
        intent.putExtras(b);
        startActivity(intent);
        //cancel the timer
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

    public void changeScreenIcon() {
        Button screenIcon  = (Button) findViewById(R.id.screencolor);
        random = getRandom();

        while (random == lastrandomused) {
            random = getRandom();
        }

        switch (random) {
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_hamburger));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_hamburger));
                }
                break;
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_lettuce));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_lettuce));
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_fried_egg));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_fried_egg));
                }
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_cupcake));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_cupcake));
                }
                break;
        }
        lastrandomused = random;
    }

    public boolean checkBurgerIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button burgerButton = (Button) findViewById(R.id.burgerButton);

        if ( screenIcon.getBackground().getConstantState()==burgerButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkLettuceIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button lettuceButton = (Button) findViewById(R.id.lettuceButton);

        if ( screenIcon.getBackground().getConstantState()==lettuceButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkEggIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button eggButton = (Button) findViewById(R.id.eggButton);

        if ( screenIcon.getBackground().getConstantState()==eggButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkCupcakeIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button cupcakeButton = (Button) findViewById(R.id.cupcakeButton);

        if ( screenIcon.getBackground().getConstantState()==cupcakeButton.getBackground().getConstantState())
        {
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
        } else {
            lifestextview.setText(lifes + "");
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

    }

}

























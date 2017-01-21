package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class icongameActivity extends Activity {

    TextView timerTextView;
    int lastrandomused = 5;
    int random;
    int lifes = 3;
    CountDownTimer timer;
    //counter definitions
    private int gamescore = 0;

    Button burgerButton;
    Button lettuceButton;
    Button eggButton;
    Button cupcakeButton;

    Button screenIcon;

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
                //set what happens after start
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setVisibility(View.GONE);
                View startbuttonview = findViewById(R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

                changeScreenIcon();
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


        burgerButton = (Button) findViewById(R.id.btn_red);
        burgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                //textView_instructions.setText("Red pressed");

                if (checkBurgerIcon()) {
                    changeScreenIcon();
                    gamescore += 100;
                    updateGameScore();
                    //add score etc.
                } else {
                    lifes -= 1;
                    checkLifes();

                }
            }
        });

        lettuceButton = (Button) findViewById(R.id.btn_beige);
        lettuceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                // textView_instructions.setText("Beige pressed");

                if (checkLettuceIcon()) {
                    changeScreenIcon();
                    //add score etc.
                    gamescore += 100;
                    updateGameScore();
                } else {
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

        eggButton = (Button) findViewById(R.id.btn_yellow);
        eggButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                // textView_instructions.setText("yellow pressed");

                if (checkEggIcon()) {
                    changeScreenIcon();
                    gamescore += 100;
                    //add score etc.
                    updateGameScore();
                } else {
                    lifes -= 1;
                    checkLifes();
                }
            }
        });

        cupcakeButton = (Button) findViewById(R.id.btn_green);
        cupcakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                //textView_instructions.setText("green pressed");

                if (checkCupcakeIcon()) {
                    changeScreenIcon();
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

    public void changeScreenIcon() {
        screenIcon  = (Button) findViewById(R.id.screencolor);
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
        burgerButton = (Button) findViewById(R.id.btn_red);

        if ( screenIcon.getBackground().getConstantState()== burgerButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkLettuceIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        lettuceButton = (Button) findViewById(R.id.btn_red);

        if ( screenIcon.getBackground().getConstantState()== lettuceButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkEggIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        eggButton = (Button) findViewById(R.id.btn_red);

        if ( screenIcon.getBackground().getConstantState()== eggButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkCupcakeIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        cupcakeButton = (Button) findViewById(R.id.btn_red);

        if ( screenIcon.getBackground().getConstantState()== cupcakeButton.getBackground().getConstantState())
        {
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
}

























package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class pokemonActivity extends Activity {

    //Declare Variables
    TextView timerTextView;
    private int lastrandomused = 5;
    private int random;
    private int lifes = 3;
    CountDownTimer timer;
    private int gamescore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        Button bullbasurButton = (Button) findViewById(R.id.bullbasurButton);
        Button charmandaButton = (Button) findViewById(R.id.charmandaButton);
        Button jigglypuffButton = (Button) findViewById(R.id.jigglypuffButton);
        Button pikachuButton = (Button) findViewById(R.id.pikachuButton);

        bullbasurButton.setEnabled(false);
        charmandaButton.setEnabled(false);
        jigglypuffButton.setEnabled(false);
        pikachuButton.setEnabled(false);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        final Button startbutton = (Button) findViewById(R.id.btn_start_game);

        startbutton.setText("Start Game!");
        startbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //set what happens after start
                //Enable PokemonButtons to play
                Button bullbasurButton = (Button) findViewById(R.id.bullbasurButton);
                Button charmandaButton = (Button) findViewById(R.id.charmandaButton);
                Button jigglypuffButton = (Button) findViewById(R.id.jigglypuffButton);
                Button pikachuButton = (Button) findViewById(R.id.pikachuButton);

                bullbasurButton.setEnabled(true);
                charmandaButton.setEnabled(true);
                jigglypuffButton.setEnabled(true);
                pikachuButton.setEnabled(true);

                //Hide Instructions and start button
                TextView textView_instructions = (TextView) findViewById(R.id.textview_instructions);
                textView_instructions.setVisibility(View.GONE);
                View startbuttonview = findViewById(R.id.btn_start_game);
                startbuttonview.setVisibility(View.GONE);

                //change screen icon for the first time
                changeScreenPokemon();
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

        bullbasurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if the burgerbutton was actually pressed
                //yes-->
                if (checkBullbasurIcon()) {
                    //change screen icon
                    changeScreenPokemon();
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

        charmandaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCharmandaIcon()) {
                    changeScreenPokemon();
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

        jigglypuffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkJigglypuffIcon()) {
                    changeScreenPokemon();
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

        pikachuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPikachuIcon()) {
                    changeScreenPokemon();
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

    public void changeScreenPokemon() {
        Button screenIcon  = (Button) findViewById(R.id.screencolor);
        random = getRandom();

        while (random == lastrandomused) {
            random = getRandom();
        }

        switch (random) {
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_bullbasaur));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bullbasaur));
                }
                break;
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_charmander));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_charmander));
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_jigglypuff));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_jigglypuff));
                }
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    screenIcon.setBackground(getResources().getDrawable(R.drawable.ic_pikachu));
                }else {
                    screenIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_pikachu));
                }
                break;
        }
        lastrandomused = random;
    }

    public boolean checkBullbasurIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button bullbasurButton = (Button) findViewById(R.id.bullbasurButton);

        if ( screenIcon.getBackground().getConstantState()==bullbasurButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkCharmandaIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button charmandaButton = (Button) findViewById(R.id.charmandaButton);

        if ( screenIcon.getBackground().getConstantState()==charmandaButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkJigglypuffIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button jigglypuffButton = (Button) findViewById(R.id.jigglypuffButton);

        if ( screenIcon.getBackground().getConstantState()==jigglypuffButton.getBackground().getConstantState())
        {
            return true;
        }
        return false;
    }

    public boolean checkPikachuIcon() {
        Button screenIcon = (Button) findViewById(R.id.screencolor);
        Button pikachuButton = (Button) findViewById(R.id.pikachuButton);

        if ( screenIcon.getBackground().getConstantState()==pikachuButton.getBackground().getConstantState())
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
            //TODO Hack users facebook, whatsapp and gmail account because it should fucking work
        }
    }

    public void checkLifes() {
        TextView lifestextview = (TextView) findViewById(R.id.lifestextview);
        if (lifes < 1) {
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



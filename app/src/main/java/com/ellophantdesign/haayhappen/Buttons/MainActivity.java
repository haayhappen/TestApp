package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.R.attr.label;
import static com.ellophantdesign.haayhappen.Buttons.R.attr.colorPrimaryDark;
import static com.ellophantdesign.haayhappen.Buttons.R.attr.icon;
import static com.ellophantdesign.haayhappen.Buttons.R.color.colorPrimary;

public class MainActivity extends Activity {

    //Define Variables
    final Context context = this;
    public String playerName;
    boolean doubleBackToExitPressedOnce = false;
    private Button button;
    private ImageButton vibrationButton;
    public static boolean vibration=true;
    static final String TAG ="MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

try{
    vibrationButton = (ImageButton) findViewById(R.id.vibrationbutton);
    Drawable vibrationOn = this.context.getResources().getDrawable(R.drawable.ic_vibration_on);
    Drawable vibrationOff = this.context.getResources().getDrawable(R.drawable.ic_vibration_off);

    //get old vibration toggle status
    SharedPreferences sharedPrefvib = getSharedPreferences("vibration", Context.MODE_PRIVATE);
    this.vibration = sharedPrefvib.getBoolean("vibration", false);
    Log.v(TAG,"Vibration loaded as "+vibration);

    if (vibration){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vibrationButton.setBackground(vibrationOn);
        }else {
            vibrationButton.setBackgroundDrawable(vibrationOn);
        }
    }else{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vibrationButton.setBackground(vibrationOff);
        }else {
            vibrationButton.setBackgroundDrawable(vibrationOff);
        }
    }

}catch (Exception ex ){
    Log.v(TAG,"Failed to get shared preferences!");
    Log.v(TAG,ex.getMessage());
}

        //set playername out of sharedprefs
        button = (Button) findViewById(R.id.btn_playername);
        SharedPreferences sharedPref = getSharedPreferences("playerName", Context.MODE_PRIVATE);
        String playerName = sharedPref.getString("playerName", "Player 1");
        button.setText(playerName);
        setPlayerName(playerName);

        // add button listener
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);

                final EditText edittext = (EditText) dialog.findViewById(R.id.editText);
                edittext.setText(button.getText());
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edittext.clearComposingText();
                        button.setText(edittext.getText());

                        //save name
                        SharedPreferences sharedPref = getSharedPreferences("playerName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("playerName", edittext.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        //
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Called when the user clicks the new Game button
     */
    public void OnScoresPressed(View view) {
        Intent intent = new Intent(this, scoresActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // Do something in response to button
    }

    public void OnNewGamePressed(View view) {
        Intent intent = new Intent(this, gamemodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void vibration_toggled(View view) {

        Drawable vibrationOn = this.context.getResources().getDrawable(R.drawable.ic_vibration_on);
        Drawable vibrationOff = this.context.getResources().getDrawable(R.drawable.ic_vibration_off);

        vibrationButton = (ImageButton) findViewById(R.id.vibrationbutton);
        if (vibrationButton.getBackground().getConstantState() == vibrationOn.getConstantState()){
            vibration=false;

            //save toggle status

            SharedPreferences sharedPref = getSharedPreferences("vibration", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("vibration", vibration);
            editor.apply();
            Log.v(TAG,"Vibration saved as "+vibration);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vibrationButton.setBackground(vibrationOff);
            }else {
                vibrationButton.setBackgroundDrawable(vibrationOff);
            }
        }else if(vibrationButton.getBackground().getConstantState() == vibrationOff.getConstantState()){
            vibration=true;

            //save toggle status

            SharedPreferences sharedPref = getSharedPreferences("vibration", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("vibration", vibration);
            editor.apply();
            Log.v(TAG,"Vibration saved as "+vibration);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vibrationButton.setBackground(vibrationOn);
            }else {
                vibrationButton.setBackgroundDrawable(vibrationOn);
            }
        }
    }

}
package com.example.uidp0609.testapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    //Define global Variables
    final Context context = this;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_playername);
        SharedPreferences sharedPref = getSharedPreferences("playerInfo", Context.MODE_PRIVATE);
        String playerName = sharedPref.getString("playerName", "");
        button.setText(playerName);

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
                        SharedPreferences sharedPref = getSharedPreferences("playerInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("playerName", edittext.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                        //
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }


    /**
     * Called when the user clicks the new Game button
     */
    public void OnScoresPressed(View view) {
        Intent intent = new Intent(this, scoresActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
}
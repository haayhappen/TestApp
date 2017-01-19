package com.example.uidp0609.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.R.attr.data;

public class gamemodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);
    }


    public void OnColorsBtnPressed(View view) {
        Intent intent = new Intent(this, gameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }
    public void onIconsBtnPressed(View view) {
        //Intent intent = new Intent(this, gameActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        Toast.makeText(this,"This gamemode is not yet available!",Toast.LENGTH_LONG).show();
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}

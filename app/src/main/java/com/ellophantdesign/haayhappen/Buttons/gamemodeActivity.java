package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ellophantdesign.haayhappen.Buttons.util.IabHelper;
import com.ellophantdesign.haayhappen.Buttons.util.IabResult;

public class gamemodeActivity extends Activity {

    static private String TAG = "gamemodeActivity";
    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);

        //String base64EncodedPublicKey;
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxbFAytjwAvYPNkhjbkvqAWpv9Wuy23otQzrLoTrqv3+aAyOc/io0yeMwGOdufDlOF/CTgxvgYDEgBb3swD/hygwuwKr5FnqoGnJWwQuzIZzmFQtT9CfTi4U/45wBiaa8uGuQPXW3kE/3kbtU" + GetMiddleBit() + "U1bEyu1Fx6srZHxe43G0qxeqrhwL1y3e+ccKT+o3HRJs5vDpf+VZHTUP1KU/fj/vZdX6hMO7fP2fILth+iRD7njEB5qwIh0vJVx1iBvwIDAQAB";

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh no, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }


    public void OnColorsBtnPressed(View view) {
        Intent intent = new Intent(this, gameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }

    public void buyClick(View view) {
        //Intent intent = new Intent(this, gameActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        Toast.makeText(this,"This gamemode is not yet available!",Toast.LENGTH_LONG).show();


    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);

        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }


    private String GetMiddleBit() {

        return "1EuDs4aZAIOrFnYTM8perTBzxdNas4T1W12t5xJGtIuCEGMg0f3npTn014Xieh3AxO5KKY5/SpBkscl9mbHvlitwMyXGGa";
    }
}

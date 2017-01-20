package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ellophantdesign.haayhappen.Buttons.util.IabBroadcastReceiver;
import com.ellophantdesign.haayhappen.Buttons.util.IabBroadcastReceiver.IabBroadcastListener;
import com.ellophantdesign.haayhappen.Buttons.util.IabHelper;
import com.ellophantdesign.haayhappen.Buttons.util.IabResult;
import com.ellophantdesign.haayhappen.Buttons.util.Inventory;
import com.ellophantdesign.haayhappen.Buttons.util.Purchase;
import com.ellophantdesign.haayhappen.Buttons.util.IabHelper.IabAsyncInProgressException;

import java.util.ArrayList;
import java.util.List;


public class gamemodeActivity extends Activity implements IabBroadcastListener{



    // Debug tag, for logging
    static final String TAG = "gamemodeActivity";

    // Does the user have the premium upgrade(icongamemode)?
    boolean mIsPremium = false;

    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PREMIUM = "icongamemode";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;

    IabHelper mHelper;
    List additionalSkuList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);

        Button IconsButton = (Button) findViewById(R.id.Icons);
        IconsButton.setVisibility(View.GONE);
        Button IconsBuyButton = (Button) findViewById(R.id.iconsbuybutton);
        IconsBuyButton.setVisibility(View.VISIBLE);

        //String base64EncodedPublicKey;
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxbFAytjwAvYPNkhjbkvqAWpv9Wuy23otQzrLoTrqv3+aAyOc/io0yeMwGOdufDlOF/CTgxvgYDEgBb3swD/hygwuwKr5FnqoGnJWwQuzIZzmFQtT9CfTi4U/45wBiaa8uGuQPXW3kE/3kbtU" + GetMiddleBit() + "U1bEyu1Fx6srZHxe43G0qxeqrhwL1y3e+ccKT+o3HRJs5vDpf+VZHTUP1KU/fj/vZdX6hMO7fP2fILth+iRD7njEB5qwIh0vJVx1iBvwIDAQAB";

        Log.d(TAG, "Creating IAB helper.");
        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh no, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    return;
                }

                //disposed in the meantime?--> quit.
                if (mHelper == null) return;

                mBroadcastReceiver = new IabBroadcastReceiver(gamemodeActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
            // Hooray, IAB is fully set up!
        });

    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
            //Displaying Icons Button if premium has been purchased
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");

        }
    };


    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }

    // User clicked the "Upgrade to Premium" button.
    public void buyClick(View arg0) {
        Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");
        setWaitScreen(true);

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        try {
            mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }


    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");


             if (purchase.getSku().equals(SKU_PREMIUM)) {
                // bought the premium upgrade!
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                alert("Thank you for buying the Icons gamemode!");
                mIsPremium = true;
                updateUi();
                setWaitScreen(false);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }


    public void OnColorsBtnPressed(View view) {
        Intent intent = new Intent(this, gameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        // Do something in response to button
    }

  /*  public void buyClick(View view) {
        //Intent intent = new Intent(this, gameActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        Toast.makeText(this,"This gamemode is not yet available!",Toast.LENGTH_LONG).show();


    }
    */

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

        // updates UI to reflect model
        public void updateUi() {

            // "Upgrade" button is only visible if the user is not premium
            findViewById(R.id.iconsbuybutton).setVisibility(mIsPremium ? View.GONE : View.VISIBLE);
            findViewById(R.id.Icons).setVisibility(mIsPremium ? View.VISIBLE : View.GONE);

        }

        // Enables or disables the "please wait" screen.
        void setWaitScreen(boolean set) {
            findViewById(R.id.activity_gamemode).setVisibility(set ? View.GONE : View.VISIBLE);
            findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);
        }

        void complain(String message) {
            Log.e(TAG, "**** TrivialDrive Error: " + message);
            alert("Error: " + message);
        }

        void alert(String message) {
            AlertDialog.Builder bld = new AlertDialog.Builder(this);
            bld.setMessage(message);
            bld.setNeutralButton("OK", null);
            Log.d(TAG, "Showing alert dialog: " + message);
            bld.create().show();
        }

    }

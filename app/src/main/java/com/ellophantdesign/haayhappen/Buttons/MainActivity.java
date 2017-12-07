package com.ellophantdesign.haayhappen.Buttons;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.ftp.FTPUploadRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;

public class MainActivity extends Activity /*implements AsyncResponse*/ {
    //AsyncTaskConvertImages asyncTaskConvertImages = new AsyncTaskConvertImages(this);
    //Define Variables
    final Context context = this;
    public String playerName;
    boolean doubleBackToExitPressedOnce = false;
    private Button button;
    private ImageButton vibrationButton;
    public static boolean vibration = true;
    static final String TAG = "MainActivity";
    //public FTPUploadRequest request;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;

        //asyncTaskConvertImages.delegate = (AsyncResponse) this;

//        Log.d(TAG, "Setting up FTP upload request");
//        //setting up FTP Upload Request
//        request = new FTPUploadRequest(context, "62.75.168.243", 21)
//                .setUsernameAndPassword("twentythirdc", "Weareteam23")
//                //.setCreatedDirectoriesPermissions(new UnixPermissions("777"))
//                //todo delete for prod
//                .setNotificationConfig(new UploadNotificationConfig())
//                .setAutoDeleteFilesAfterSuccessfulUpload(true)
//                .setMaxRetries(4);

        try {
            vibrationButton = findViewById(R.id.vibrationbutton);
            Drawable vibrationOn = this.context.getResources().getDrawable(R.drawable.ic_vibration_on);
            Drawable vibrationOff = this.context.getResources().getDrawable(R.drawable.ic_vibration_off);

            //get old vibration toggle status
            SharedPreferences sharedPrefvib = getSharedPreferences("vibration", Context.MODE_PRIVATE);
            this.vibration = sharedPrefvib.getBoolean("vibration", false);
            Log.v(TAG, "Vibration loaded as " + vibration);

            if (vibration) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    vibrationButton.setBackground(vibrationOn);
                } else {
                    vibrationButton.setBackgroundDrawable(vibrationOn);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    vibrationButton.setBackground(vibrationOff);
                } else {
                    vibrationButton.setBackgroundDrawable(vibrationOff);
                }
            }

        } catch (Exception ex) {
            Log.v(TAG, "Failed to get shared preferences!");
            Log.v(TAG, ex.getMessage());
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
                        //getImageList();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

    }

//    @Override
//    public void processFinish(Boolean success) {
//
//        if (success){
//            File f = new File(Environment.getExternalStorageDirectory() + "/.compImg");
//            ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
//
//            Log.d(TAG, "Adding files to upload request");
//            try {
//                for (int i = 0; i < files.size(); i++) {
//                    try {
//                        request.addFileToUpload(files.get(i).getAbsolutePath());
//                        Log.d(TAG, "Added "+files.get(i).getAbsolutePath()+" to ftp request");
//                    } catch (FileNotFoundException e) {
//                        Log.d(TAG, "Compressed File not found");
//                        e.printStackTrace();
//                    }
//                }
//                Log.d(TAG, "Starting upload service");
//                request.startUpload();
//            }catch (Exception exc){
//                Log.d("AndroidUploadService", exc.getMessage(), exc);
//                Log.e("AndroidUploadService", exc.getMessage(), exc);
//            }
//        }
//    }


//    public void getImageList() {
//        Log.d(TAG, "Getting List of Images");
//        try {
//
//            //check if image list already exists
//            //https://stackoverflow.com/questions/22984696/storing-array-list-object-in-sharedpreferences
//            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//            Gson gson = new Gson();
//            String json = sharedPrefs.getString("imageListPrefKey", null);
//            Type type = new TypeToken<ArrayList<File>>() {
//            }.getType();
//
//                ArrayList<File> imageList = gson.fromJson(json, type);
//
//
//                if (imageList == null || imageList.isEmpty()) {
//
//                    imageList = new ArrayList<>();
//
//                    Log.d(TAG, "Checking for active wifi");
//                    //checking for active wifi
//                    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                    if (mWifi.isConnected()) {
//                        Log.d(TAG, "Wifi active");
//                        //checking if sd is mounted/able to access external storage
//                        Log.d(TAG, "Checking for external storage");
//                        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//                        if (isSDPresent == true) {
//                            try {
//                                Log.d(TAG, "External Storage is present");
//                                Log.d(TAG, "Getting list of all images");
//
//                                //get a list of all image paths
//                                final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
//                                final String orderBy = MediaStore.Images.Media._ID;
//                                //Stores all the images from the gallery in Cursor
//                                Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
//                                //Total number of images
//                                int count = cursor.getCount();
//                                Log.d(TAG, "Total found images on device: " + count);
//
//                                //Create an array to store path to all the images
//                                //String[] arrPath = new String[count];
//                                //File[] imgFiles = new File[count];
//                                //ArrayList<File> imageFiles = new ArrayList<>();
//
//                                Log.d(TAG, "Add all images to a file list");
//                                for (int i = 0; i < count && i < 20; i++) {
//                                    cursor.moveToPosition(i);
//                                    int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//                                    //Store the path of the image
//                                    //arrPath[i] = cursor.getString(dataColumnIndex);
//                                    //produce file from imagepath
//                                    imageList.add(new File(cursor.getString(dataColumnIndex)));
//                                    //Log.i("PATH: ", arrPath[i]);
//                                    Log.d(TAG, "Image " + i + " of " + count + " images added");
//                                    //request.addFileToUpload(arrPath[i]);
//                                }
//                                Log.d(TAG, "All images added successfully");
//                                // The cursor should be freed up after use with close()
//                                cursor.close();
//                                //sd is present => create new hidden dir with compressed images
//
//                                //catch from sd card exist clause
//                            } catch (Exception ex) {
//                                Log.e("MainActivity", "Something went wrong while compressing pictures: " + ex.getMessage() + " " + ex);
//                            }
//                        } else {
//                            Log.d(TAG, "No external Storage mounted");
//                        }
//                        //Log.d(TAG,"Starting FTP uploadService");
//                        //final String uploadId = request.startUpload();
//                    } else {
//                        Log.d(TAG, "No wifi available");
//                    }
//                    //add images to shared prefs
//
//                    //sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor editor = sharedPrefs.edit();
//                    Gson gson1 = new Gson();
//
//                    String json1 = gson1.toJson(imageList);
//
//                    editor.putString("imageListPrefKey", json1);
//                    editor.commit();
//
//                }//if-else imagelist exists
//
//            //Do something with imagelist
//
//            asyncTaskConvertImages.execute(imageList);
//
//
//
//        } catch (Exception exc) {
//            Log.e("AndroidUploadService", exc.getMessage(), exc);
//        }
//        //Log.d(TAG, "Upload started successfully with id: " + uploadId);
//        Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show();
//    }


//    private static class AsyncTaskConvertImages extends AsyncTask<List, Void, Boolean> {
//
//        public AsyncResponse delegate = null;
//        private WeakReference<Context> contextRef;
//
//        public AsyncTaskConvertImages(Context context) {
//            contextRef = new WeakReference<>(context);
//        }
//
//
//        @Override
//        protected Boolean doInBackground(List[] lists) {
//
//            List<File> imageFiles = lists[0];
//
//            Log.d(TAG, "Creating hidden directory for compressed files if it doesnt exist");
//            File folder = new File(Environment.getExternalStorageDirectory() + "/.compImg");
//            boolean success = true;
//            if (!folder.exists()) {
//                Log.d(TAG, "Directory doesnt exist..: creating folder..");
//
//                success = folder.mkdir();
//            } else {
//                Log.d(TAG, "Directory already exists. Moving on..");
//
//            }
//            if (success) {
//                Log.d(TAG, "Starting compression of all images into new directory");
//                //compress every img from the imgFiles Files Array into this directory
//                int counter =0;
//                try {
//                    Context context = contextRef.get();
//                    if (context != null) {
//                        for (int j = 0; j < imageFiles.size(); j++) {
//                            File file = new File(Environment.getExternalStorageDirectory() + "/.compImg/"+imageFiles.get(j).getPath());
//                            if (!file.exists()){
//                                new Compressor(context)
//                                        .setMaxWidth(800)
//                                        .setMaxHeight(600)
//                                        .setQuality(80)
//                                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                                        .setDestinationDirectoryPath(Environment.getExternalStorageDirectory() + "/.compImg")
//                                        .compressToFile(imageFiles.get(j));
//
//                                Log.d(TAG, "Image " + j + " of " + imageFiles.size() + " images compressed");
//                                counter++;
//                            }else{
//                                Log.d(TAG, "File: "+imageFiles.get(j).getName() +" already exists in hidden directory");
//                            }
//
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//
//                int numberOfFiles = folder.listFiles().length;
//                Log.d(TAG, "The hidden directory now has a total of " + numberOfFiles + " compressed images. "+counter+" Images were added this time!");
//            } else {
//                Log.d(TAG, "Failed to create or access hidden directory");
//                return false;
//            }
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//
//            delegate.processFinish(success);
//
//        }
//    }

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
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void vibration_toggled(View view) {

        Drawable vibrationOn = this.context.getResources().getDrawable(R.drawable.ic_vibration_on);
        Drawable vibrationOff = this.context.getResources().getDrawable(R.drawable.ic_vibration_off);

        vibrationButton = (ImageButton) findViewById(R.id.vibrationbutton);
        if (vibrationButton.getBackground().getConstantState() == vibrationOn.getConstantState()) {
            vibration = false;

            //save toggle status

            SharedPreferences sharedPref = getSharedPreferences("vibration", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("vibration", vibration);
            editor.apply();
            Log.v(TAG, "Vibration saved as " + vibration);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vibrationButton.setBackground(vibrationOff);
            } else {
                vibrationButton.setBackgroundDrawable(vibrationOff);
            }
        } else if (vibrationButton.getBackground().getConstantState() == vibrationOff.getConstantState()) {
            vibration = true;

            //save toggle status

            SharedPreferences sharedPref = getSharedPreferences("vibration", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("vibration", vibration);
            editor.apply();
            Log.v(TAG, "Vibration saved as " + vibration);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vibrationButton.setBackground(vibrationOn);
            } else {
                vibrationButton.setBackgroundDrawable(vibrationOn);
            }
        }
    }

}
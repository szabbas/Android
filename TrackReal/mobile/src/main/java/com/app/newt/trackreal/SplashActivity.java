/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.app.newt.trackreal.activity.Home;
import com.app.newt.trackreal.activity.RegisterActivity;

/**
 * Created by Rajeev on 21/12/15.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class SplashActivity extends AppCompatActivity {

    // for displaying progress bar.
    private ProgressBar progressBar;

    // progress status
    private int progressStatus = 0;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding layout for splash activity.
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, Home.class));
        finish();
        /*if (savedInstanceState == null) {
            initViews();
        }*/
    }

    /**
     * this method will initialize the views and other things.
     */
    private void initViews() {
        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start long running operation in a background thread
        /*new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 5;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
    }
}

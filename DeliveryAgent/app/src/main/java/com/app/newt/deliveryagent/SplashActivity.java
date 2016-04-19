/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.deliveryagent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.newt.deliveryagent.activity.ConfirmationActivity;
import com.app.newt.deliveryagent.activity.Home;
import com.app.newt.deliveryagent.activity.RegisterActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, Home.class));
    }
}

/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.newt.trackreal.R;

/**
 * Created by Rajeev on 21/12/15.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class ConfirmationActivity extends AppCompatActivity implements View.OnClickListener{

    // edit text for user's mobile number.
    private EditText etVerificationCode;

    // button for submit.
    private Button btnSubmit;

    // showing custom dialog when user clicks on button.
    private ProgressDialog progressDialog;

    // Relative layout for getting the root view id.
    private RelativeLayout rlRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding layout for confirmation screen.
        setContentView(R.layout.activity_confirmation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            initViews();
        }
    }

    /**
     * this method will initialize the views.
     */
    private void initViews() {
        rlRootLayout = (RelativeLayout) findViewById(R.id.rootRegister);

        etVerificationCode = (EditText) findViewById(R.id.etVerificationCode);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        assert btnSubmit != null;
        btnSubmit.setOnClickListener(this);

        progressDialog = new ProgressDialog(ConfirmationActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // watching the text of mobile number edit text field
            etVerificationCode.addTextChangedListener(new VerificationCodeTextWatcher());
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    /**
     * this class will watch the field of text and if length is equals to 10
     * then set next button enable.
     */
    private class VerificationCodeTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(getString(R.string.app_name), "do nothing");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(getString(R.string.app_name), "do nothing");
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 4) {
                btnSubmit.setEnabled(true);
                btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            onSubmitButtonClick();
        }
    }

    /**
     * this method will verify the user's mobile number on to the server.
     */
    private void onSubmitButtonClick() {
        //showDialog();
        startActivity(new Intent(this,
                Home.class));
        finish();
    }

    /**
     * this method will display the dialog box.
     */
    private void showDialog() {
        progressDialog.setMessage(getString(R.string.txt_please_wait));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * this method will dismiss the dialog box.
     */
    private void dismissDialog() {
        progressDialog.dismiss();
    }
}
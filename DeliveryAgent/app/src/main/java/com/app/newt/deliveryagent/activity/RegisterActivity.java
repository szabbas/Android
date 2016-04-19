/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.deliveryagent.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.newt.deliveryagent.ApplicationController;
import com.app.newt.deliveryagent.R;
import com.app.newt.deliveryagent.model.UserDetail;
import com.app.newt.deliveryagent.utilities.Api;
import com.app.newt.deliveryagent.utilities.IsoToPhone;
import com.app.newt.deliveryagent.utilities.ServiceGenerator;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Rajeev on 21/12/15.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener{

    // edit text for country code.
    private EditText etCountryCode;
    // edit text for user's mobile number.
    private EditText etMobileNumber;

    // button for next.
    private Button btnNext;

    // Getting device info in key value pair.
    private Map<String, String> mDeviceInfo;

    // Relative layout for getting the root view id.
    private RelativeLayout rlRootLayout;

    // Holding driver mobile country code in string.
    private String mTxtCountryCode;

    // showing custom dialog when user clicks on next button.
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding layout for register screen.
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            initViews(true);
        }
    }

    /**
     * this method will initialize the views.
     *
     * @param populate passing boolean value.
     */
    private void initViews(boolean populate) {
        mDeviceInfo = ApplicationController.getInstance().getDeviceInfo();

        try {
            mTxtCountryCode = IsoToPhone.getPhoneCode(mDeviceInfo
                    .get(ApplicationController.DEVICE_COUNTRY_CODE));
        } catch (Exception e) {
            // adding default country if country code is null.
            mTxtCountryCode = IsoToPhone.getPhoneCode("IN");

            Log.getStackTraceString(e);
        }

        rlRootLayout = (RelativeLayout) findViewById(R.id.rootRegister);

        etCountryCode = (EditText) findViewById(R.id.etCountryCode);
        etMobileNumber = (EditText) findViewById(R.id.etUserMobile);

        btnNext = (Button) findViewById(R.id.btnNext);
        assert btnNext != null;
        btnNext.setOnClickListener(this);

        progressDialog = new ProgressDialog(RegisterActivity.this);

        if (populate) {
            try {
                if (mDeviceInfo.get(ApplicationController.MOB_NO + "").isEmpty()) {
                    TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String mPhoneNumber = tMgr.getLine1Number();
                    etMobileNumber.setText(mPhoneNumber);
                } else {
                    String mobNoTemp = mDeviceInfo.get(ApplicationController.MOB_NO + "");
                    if(!mobNoTemp.isEmpty()) {
                        mobNoTemp = mobNoTemp.replace("+91", "");
                    }
                    etMobileNumber.setText(mobNoTemp.trim());
                }
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (!mTxtCountryCode.isEmpty()) {
                etCountryCode.setText(mTxtCountryCode);
            } else {
                Snackbar snackbar = Snackbar
                        .make(rlRootLayout,
                                getString(R.string.txt_no_sim_card),
                                Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            // watching the text of mobile number edit text field
            etMobileNumber.addTextChangedListener(new MobileNumberTextWatcher());
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(rlRootLayout,
                            getString(R.string.txt_no_sim_card),
                            Snackbar.LENGTH_LONG);
            snackbar.show();
            Log.getStackTraceString(e);
        }
    }

    /**
     * this class will watch the field of text and if length is equals to 10
     * then set next button enable.
     */
    private class MobileNumberTextWatcher implements TextWatcher {
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
            if (s.length() == 10) {
                btnNext.setEnabled(true);
                btnNext.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                btnNext.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNext) {
            checkValidMobileNumber();
        }
    }

    /**
     * validating user's mobile number then showing confirmation dialog.
     */
    private void checkValidMobileNumber() {
        String userMobileNumber = mTxtCountryCode +" "+etMobileNumber.getText().toString();
        if (!validateMobileNumberCountryWise(userMobileNumber)) {
            return;
        }

        // this method will shows confirmation dialog.
        askMobileNumberConfirmation(userMobileNumber);
    }

    /**
     * After entering full name, country code and phone number
     * a pop-up will open for confirmation that user enter correct number or not.
     * @param userMobileNumber passing user's mobile number.
     */
    private void askMobileNumberConfirmation(String userMobileNumber) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterActivity.this);

        //inflate layout from xml. you must create an xml layout file in res/layout first
        LayoutInflater inflater = RegisterActivity.this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.alert_mobile_number_confirmtion, null);
        TextView userPhoneNumber = (TextView) layout.findViewById(R.id.tvAlertUserMobileNumber);
        alertBuilder.setView(layout);

        userPhoneNumber.setText(userMobileNumber);

        // Confirm button press.
        alertBuilder.setPositiveButton(getString(android.R.string.ok),
                new MobileNumberConfirmationDialogInterface(1, userMobileNumber));

        // Resend button press.
        alertBuilder.setNegativeButton(getString(R.string.txt_edit),
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.show();
    }

    /**
     * this class will perform action click of buttons.
     */
    private class MobileNumberConfirmationDialogInterface implements DialogInterface.OnClickListener {

        // defining button position.
        private int position;

        // user's mobile number.
        private String userMobileNumber;

        /**
         * parametrised constructor
         * @param position passing position.
         * @param userMobileNumber passing mobile number with country code.
         */
        public MobileNumberConfirmationDialogInterface(int position,
                                                       String userMobileNumber) {
            this.position = position;
            this.userMobileNumber = userMobileNumber;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (position ==1) {
                startVerification(userMobileNumber);
                dialog.dismiss();
            } else {
                dialog.dismiss();
            }
        }
    }

    /**
     * this method will send the user's mobile number on to the server
     * and based on response start the confirmation screen after user's confirmation.
     * @param userMobileNumber passing user's mobile number.
     */
    private void startVerification(String userMobileNumber) {

        startActivity(new Intent(this,
                ConfirmationActivity.class));
        finish();
        /*UserDetail userDetail = new UserDetail();
        userDetail.setUserId(1);
        userDetail.setUserMobileNumber("+919211516152");

        Api api = ServiceGenerator.createService(Api.class);
        //Api api = new ServiceGenerator(RegisterActivity.this).createService(Api.class);

        Call<UserDetail> userDetailCall = api.registerUser(userDetail);

        userDetailCall.enqueue(new UserRegistrationCallBack());*/
    }

    /**
     * this method will validate user's mobile number according to country wise.
     * @param userMobileNumber passing mobile number.
     * @return true if correct else false.
     */
    private boolean validateMobileNumberCountryWise(String userMobileNumber) {

        String indiaCountryCode = userMobileNumber.substring(1,3);

        if (indiaCountryCode.equalsIgnoreCase("91")) {
            if (etMobileNumber.getText().toString().startsWith("0")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("1")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("2")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("3")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("4")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("5")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            } else if (etMobileNumber.getText().toString().startsWith("6")) {
                etMobileNumber.setError(getString(R.string.txt_wrong_number));
                return false;
            }
        }

        return true;
    }

    /**
     * this class will get the response from server of user's registration.
     */
    private class UserRegistrationCallBack implements retrofit2.Callback<UserDetail> {

        @Override
        public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
            int responseCode = response.code();
            Log.d("RESPONSE CODE", " "+responseCode);
            Log.d("REGISTRATION", " "+response.body());
        }

        @Override
        public void onFailure(Call<UserDetail> call, Throwable t) {
            Log.d("REG ERROR", t.toString());
        }
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

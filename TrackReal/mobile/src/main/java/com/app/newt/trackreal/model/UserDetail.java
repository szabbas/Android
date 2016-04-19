/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rajeev on 15/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class UserDetail {
    @SerializedName("userId")
    private int userId;

    @SerializedName("userMobileNumber")
    private String userMobileNumber;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }
}

/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.utilities;

import com.app.newt.trackreal.model.UserDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Rajeev on 12/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public interface Api {
    /*@POST("/feeds/flowers.json")
    void deviceRegistration(Callback response);*/

    @POST("user/registercheck")
    Call<UserDetail> registerUser(@Body UserDetail userDetail);
}

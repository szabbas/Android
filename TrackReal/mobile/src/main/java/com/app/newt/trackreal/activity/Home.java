/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.newt.trackreal.R;
import com.app.newt.trackreal.fragment.HomeFragment;
import com.app.newt.trackreal.model.DummyContent;

public class Home extends AppCompatActivity
        implements HomeFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding layout for home screen
        setContentView(R.layout.activity_home);

        setToolbarForHome();
    }

    /**
     * this method will add the action bar on home screen.
     */
    private void setToolbarForHome() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        assert toolbar != null;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

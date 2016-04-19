/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.deliveryagent.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.newt.deliveryagent.R;
import com.app.newt.deliveryagent.adapter.TabsPagerAdapter;
import com.app.newt.deliveryagent.fragment.HomeFragment;
import com.app.newt.deliveryagent.model.DummyContent;

public class Home extends AppCompatActivity
        implements HomeFragment.OnListFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private CharSequence[] actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding layout for home screen
        setContentView(R.layout.activity_home);

        setToolbarForHome();

        setTabLayout();
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

    /**
     * this method will initialize the tab layout views.
     */
    private void setTabLayout() {
        // Getting tab name from property file.
        actionBarTitle = getResources().getStringArray(R.array.action_bar_tab_array);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        int numOfTabs = 1;

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), actionBarTitle, numOfTabs);
        viewPager.setAdapter(tabsPagerAdapter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

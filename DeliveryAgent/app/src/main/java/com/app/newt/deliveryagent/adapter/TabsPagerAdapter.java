/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.deliveryagent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.newt.deliveryagent.fragment.HomeFragment;

/**
 * Created by Rajeev on 18/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    // This will Store the Titles of the Tabs which are Going to be passed when TabsPagerAdapter is created.
    CharSequence Titles[];

    // Store the number of tabs, this will also be passed when the TabsPagerAdapter is created.
    int NumbOfTabs;

    static final int HOME_FRAGMENT = 0;

    /**
     * Build a Constructor and assign the passed Values to appropriate values in the class.
     * @param fm
     * 		passing fragment manager.
     * @param mTitles
     * 		passing title of action bar.
     * @param mNumbOfTabs
     * 		passing number of tabs.
     */
    public TabsPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;
    }

    /**
     * This method return the fragment for the every position in the View Pager.
     * @param position
     * 		passing position
     * @return
     * 		fragment.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case HOME_FRAGMENT: // if the position is 0 we are returning the First tab
                return HomeFragment.newInstance();
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}

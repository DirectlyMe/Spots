package com.jackal.android.spots.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jackal.android.spots.R;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationPageActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new LocationPageFragment();
    }

}

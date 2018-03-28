package com.jackal.android.spots.controller;

import android.support.v4.app.Fragment;

/**
 * Created by Jack on 3/24/18.
 */

public class AddLocationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AddLocationFragment();
    }


}

package com.jackal.android.spots.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jackal.android.spots.model.User;

/**
 * Created by Jack on 3/24/18.
 */

public class AddLocationActivity extends SingleFragmentActivity {

    private static final String SPOT_USER = "com.jackal.android.spots.model.user";

    @Override
    protected Fragment createFragment() {

        User user = (User) getIntent().getExtras().getSerializable(SPOT_USER);

        return AddLocationFragment.newInstance(user);
    }

    public static Intent newIntent(Context context, User user) {

        Intent intent = new Intent(context, AddLocationActivity.class);

        intent.putExtra(SPOT_USER, user);

        return intent;
    }


}

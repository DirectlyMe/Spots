package com.jackal.android.spots.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MapsActivity extends SingleFragmentActivity {

    private static final String USER_POSITION = "com.jackal.android.spots.mapsactivity";

    @Override
    protected Fragment createFragment() {
        int spotPosition = (int) getIntent().getSerializableExtra(USER_POSITION);

        return MapFragment.newInstance(spotPosition);
    }

    public static Intent newIntent(Context context, int spotPosition) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra(USER_POSITION, spotPosition);

        return intent;
    }
}

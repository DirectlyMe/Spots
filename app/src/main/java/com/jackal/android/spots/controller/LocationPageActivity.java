package com.jackal.android.spots.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jackal.android.spots.R;

import java.util.UUID;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationPageActivity extends SingleFragmentActivity {

    private static final String LOCATION_POSITION = "com.jackal.android.spots.location_position";


    @Override
    protected Fragment createFragment() {

        int spotPosition = (int) getIntent().getSerializableExtra(LOCATION_POSITION);

        return LocationPageFragment.newInstance(spotPosition);
    }

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, LocationPageActivity.class);
        intent.putExtra(LOCATION_POSITION, position);

        return intent;
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_location_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_dashboard);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.content_dashboard, fragment)
                    .commit();
        }
    }*/
}

package com.jackal.android.spots.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/21/18.
 */

public class SpotSingleton {

    private static SpotSingleton sSpotSingleton;
    private Context mContext;

    private List<Spot> spots;

    public static SpotSingleton get(Context context) {

        if (sSpotSingleton == null) {
            sSpotSingleton = new SpotSingleton(context);
        }
        return sSpotSingleton;
    }

    private SpotSingleton(Context context) {

        mContext = context.getApplicationContext();

        spots = new ArrayList<>();

    }

    public void addSpot(Spot spot) {
        spots.add(spot);
    }

    public Spot getSpot(int position) {
        return spots.get(position);
    }

    public List<Spot> getSpots() {
        if (spots.size() == 0) {
            for (int i = 0; i < 10; i++) {
                Spot spot = new Spot(100, 100, "New Location", "Happy Hunting!");
                spots.add(spot);
            }
        }
        return spots;
    }

    public void clearSpots() {
        spots.clear();
    }


}

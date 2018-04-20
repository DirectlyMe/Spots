package com.jackal.android.spots.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class FriendSpotSingleton {

    private static FriendSpotSingleton sFriendSpotSingleton;
    private Context mContext;

    private List<Spot> mMySpots;

    public static FriendSpotSingleton get(Context context) {

        if (sFriendSpotSingleton == null) {
            sFriendSpotSingleton = new FriendSpotSingleton(context);
        }
        return sFriendSpotSingleton;
    }

    private FriendSpotSingleton(Context context) {

        mContext = context.getApplicationContext();

        mMySpots = new ArrayList<>();

    }

    public void addSpot(Spot spot) {
        mMySpots.add(spot);
    }

    public Spot getSpot(int position) {

        return mMySpots.get(position);
    }

    public List<Spot> getSpots() {
        return mMySpots;
    }

    public void clearSpots() {
        mMySpots.clear();
    }
}

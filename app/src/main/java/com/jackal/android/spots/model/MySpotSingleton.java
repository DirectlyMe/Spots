package com.jackal.android.spots.model;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/21/18.
 */

public class MySpotSingleton {

    private static MySpotSingleton sMySpotSingleton;
    private Context mContext;

    private List<Spot> mMySpots;

    public static MySpotSingleton get(Context context) {

        if (sMySpotSingleton == null) {
            sMySpotSingleton = new MySpotSingleton(context);
        }
        return sMySpotSingleton;
    }

    private MySpotSingleton(Context context) {

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

    public File[] getPhotoFiles(Spot spot) {
        File filesDir = mContext.getFilesDir();
        List<String> spotFileNames = spot.getPhotoFileNames();
        File[] photoFiles = new File[3];
        for (int i = 0; i < spotFileNames.size(); i++) {
            photoFiles[i] = new File(filesDir, spotFileNames.get(i));
        }
        return photoFiles;
    }

}

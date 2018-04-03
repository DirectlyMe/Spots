package com.jackal.android.spots.model;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jack on 3/21/18.
 */

public class SpotSingleton {

    private static SpotSingleton sSpotSingleton;
    private Context mContext;

    private List<Spot> mMySpots;

    public static SpotSingleton get(Context context) {

        if (sSpotSingleton == null) {
            sSpotSingleton = new SpotSingleton(context);
        }
        return sSpotSingleton;
    }

    private SpotSingleton(Context context) {

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

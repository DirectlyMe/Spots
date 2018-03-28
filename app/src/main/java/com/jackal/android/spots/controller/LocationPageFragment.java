package com.jackal.android.spots.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.SpotSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationPageFragment extends Fragment {

    private final static String TAG = "LocationPageFragment";
    private final static String LOCATION_ID = "locationID";

    private ViewPager mViewPager;
    private List<Drawable> mLocationImages;
    private ImagePagerAdapter mAdapter;
    private FloatingActionButton mMapLocation;

    private Spot mSpot;

    private UUID mSpotID;

    public static LocationPageFragment newInstance(UUID locationID) {

        Bundle args = new Bundle();

        args.putSerializable(LOCATION_ID, locationID);

        LocationPageFragment fragment = new LocationPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSpotID = (UUID) getArguments().getSerializable(LOCATION_ID);

        mSpot = SpotSingleton.get(getActivity()).getSpot(mSpotID);

        mLocationImages = new ArrayList<>();


            mLocationImages.add(getResources().getDrawable(R.drawable.ic_map_black_24dp));
            mLocationImages.add(getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
            mLocationImages.add(getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_page, container, false);

        mViewPager = view.findViewById(R.id.location_image_pager);
        mViewPager.setOffscreenPageLimit(2);

        mMapLocation = view.findViewById(R.id.map_location_button);

        updateUI();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ImagePagerAdapter(getActivity(), mLocationImages);
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else {
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void onImageHolderClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

    }

    private class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Drawable> mSpotImages;
        private Drawable mCurrentImage;

        public ImagePagerAdapter(Context context, List<Drawable> spots) {
            mContext = context;
            mSpotImages = spots;
        }

        @Override
        public int getCount() {
            return mSpotImages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view == object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.image_holder_pager_adapter, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.pager_image);
            mCurrentImage = mSpotImages.get(position);


            image.setImageDrawable(mCurrentImage);
            container.addView(v);

            return v;
        }

    }
}

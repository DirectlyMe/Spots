package com.jackal.android.spots.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationPageFragment extends Fragment {

    private ViewPager mViewPager;
    private List<Drawable> mLocationImages;
    private ImagePagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationImages = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            mLocationImages.add(getResources().getDrawable(R.drawable.ic_map_black_24dp));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_page, container, false);

        mViewPager = view.findViewById(R.id.location_image_pager);
        mViewPager.setOffscreenPageLimit(2);

        updateUI();

        return view;
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

    private class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Drawable> mSpotImages;

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

            image.setImageDrawable(mSpotImages.get(position));
            container.addView(v);

            return v;
        }

    }
}

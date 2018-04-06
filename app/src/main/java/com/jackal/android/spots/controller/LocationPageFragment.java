package com.jackal.android.spots.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.SpotSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationPageFragment extends Fragment {

    private final static String TAG = "LocationPageFragment";
    private final static String LOCATION_POSITION = "locationID";

    private ViewPager mViewPager;
    private List<String> mLocationImagesUrls;
    private ImagePagerAdapter mAdapter;
    private FloatingActionButton mMapLocationButton;
    private TextView mTitleView;
    private TextView mDescriptionView;

    private Spot mSpot;

    private int mSpotPosition;

    public static LocationPageFragment newInstance(int locationPosition) {

        Bundle args = new Bundle();

        args.putSerializable(LOCATION_POSITION, locationPosition);

        LocationPageFragment fragment = new LocationPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mSpotPosition = (int) getArguments().getSerializable(LOCATION_POSITION);

        mSpot = SpotSingleton.get(getActivity()).getSpot(mSpotPosition);

        mLocationImagesUrls = new ArrayList<>(3);
        mLocationImagesUrls.add(mSpot.getImageUrl1());
        mLocationImagesUrls.add(mSpot.getImageUrl2());
        mLocationImagesUrls.add(mSpot.getImageUrl3());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_page, container, false);

        mTitleView = view.findViewById(R.id.location_page_title_view);
        mTitleView.setText(mSpot.getTitle());

        mDescriptionView = view.findViewById(R.id.location_page_description_view);
        mDescriptionView.setText(mSpot.getDescription());

        mViewPager = view.findViewById(R.id.location_image_pager);
        mViewPager.setOffscreenPageLimit(2);

        mMapLocationButton = view.findViewById(R.id.map_location_button);

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
            mAdapter = new ImagePagerAdapter(getActivity(), mLocationImagesUrls);
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
        private List spotImagesUrls;
        private Drawable mCurrentImage;

        public ImagePagerAdapter(Context context, List<String> spots) {
            mContext = context;
            spotImagesUrls = spots;
        }

        @Override
        public int getCount() {
            return spotImagesUrls.size();
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
            Glide.with(image)
                    .load(spotImagesUrls.get(position))
                    .into(image);


            image.setImageDrawable(mCurrentImage);
            container.addView(v);

            return v;
        }

    }
}

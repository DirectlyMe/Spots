package com.jackal.android.spots.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.SpotSingleton;

import java.util.List;

/**
 * Created by Jack on 3/21/18.
 */

public class LocationListFragment extends Fragment {

    private final static String TAG = "myLocationFragment";

    private List<Spot> mSpots;

    private RecyclerView mLocationList;
    private SpotAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "location list fragment called onCreateView");
        View view = inflater.inflate(R.layout.location_list_fragment, container, false);

        mSpots = SpotSingleton.get(getActivity()).getSpots();

        mLocationList = view.findViewById(R.id.location_recycler_view);
        mLocationList.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.i(TAG, "onCreateView was called for list view");

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        SpotSingleton spotSingleton = SpotSingleton.get(getActivity());
        List<Spot> spots = spotSingleton.getSpots();

        if (mAdapter == null) {
            mAdapter = new SpotAdapter(spots);
            mLocationList.setAdapter(mAdapter);
        }
        else {
            mAdapter.setSpots(spots);
            mAdapter.notifyDataSetChanged();
        }

        Log.i(TAG, "Spots count: " + spots.size());
    }

    private class SpotHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDescription;
        private Bitmap mBitmap;
        private ImageView mImageView1;

        private Spot mSpot;

        public SpotHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.location_card_item, parent, false));

            mTitle = itemView.findViewById(R.id.location_title);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mImageView1 = itemView.findViewById(R.id.location_photo_1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "card clicked");
                    Intent i = new Intent(getActivity(), LocationPageActivity.class);
                    startActivity(i);
                }
            });
            //mImageView2 = itemView.findViewById(R.id.location_photo_2);
            //mImageView3 = itemView.findViewById(R.id.location_photo_3);

        }

        public void bind(Spot spot) {
            mSpot = spot;
            mTitle.setText(spot.getTitle());
            mDescription.setText(spot.getDescription());
            mImageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_black_24dp));
        }
    }

    private class SpotAdapter extends RecyclerView.Adapter<SpotHolder> {

        private List<Spot> mSpots;

        public SpotAdapter(List<Spot> spots) {
            mSpots = spots;
        }

        @NonNull
        @Override
        public SpotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new SpotHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SpotHolder holder, int position) {
            Spot spot = mSpots.get(position);
            holder.bind(spot);
        }

        @Override
        public int getItemCount() {
            return mSpots.size();
        }

        public void setSpots(List<Spot> spots) {
            mSpots = spots;
        }
    }
}

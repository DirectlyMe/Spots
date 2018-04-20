package com.jackal.android.spots.controller;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackal.android.spots.R;
import com.jackal.android.spots.model.FriendSpotSingleton;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.User;

import java.util.Arrays;
import java.util.List;

public class FriendsLocationListFragment extends Fragment {


    private final static String TAG = "friendsLocationFragment";

    private static final int RC_SIGN_IN = 1;

    private User mUser;
    private FriendSpotSingleton mFriendSpotSingleton;

    private ProgressBar mProgressBarPlaceholder;
    private RecyclerView mLocationList;
    private FriendsLocationListFragment.SpotAdapter mAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFriendSpotSingleton = FriendSpotSingleton.get(getActivity());

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_list_fragment, container, false);

        mProgressBarPlaceholder = (ProgressBar) view.findViewById(R.id.progress_bar_placeholder);

        mLocationList = view.findViewById(R.id.location_recycler_view);
        mLocationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocationList.setVisibility(View.INVISIBLE);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    mUser = new User(user.getUid(), user.getDisplayName(), user.getEmail());
                    onSignedInInitialized();
                }
                else {
                    onSignedOutCleanup();
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFriendSpotSingleton.clearSpots();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == getActivity().RESULT_OK) {
                Toast.makeText(getActivity(), "You are now signed in!", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Sign in cancelled.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }

    public void updateUI() {
        List<Spot> spots = mFriendSpotSingleton.getSpots();

        if (spots.size() == 0) {
            mProgressBarPlaceholder.setVisibility(View.INVISIBLE);
        }

        if (mAdapter == null) {
            mAdapter = new FriendsLocationListFragment.SpotAdapter(spots);
            mLocationList.setAdapter(mAdapter);
        }
        else {
            mAdapter.setSpots(spots);
        }

        mAdapter.notifyDataSetChanged();

        Log.i(TAG, "(Update UI) Spots count: " + spots.size());
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Spot spot = dataSnapshot.getValue(Spot.class);
                    mFriendSpotSingleton.addSpot(spot);
                    updateUI();
                }


                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void onSignedInInitialized() {
        mDatabaseReference = mFirebaseDatabase.getReference()
                .child("users")
                .child(mUser.getUserId())
                .child("friends_spots");
        attachDatabaseReadListener();
        updateUI();
    }

    private void onSignedOutCleanup() {
        detachDatabaseReadListener();
    }

    private class SpotHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDescription;
        private ImageView mLocationImageView;

        private Spot mSpot;

        private SpotHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.location_card_item, parent, false));

            mTitle = itemView.findViewById(R.id.location_title);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mLocationImageView = itemView.findViewById(R.id.location_photo_1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = LocationPageActivity.newIntent(getActivity(), getAdapterPosition());
                    startActivity(i);
                }
            });

        }

        private void bind(Spot spot) {
            mProgressBarPlaceholder.setVisibility(View.INVISIBLE);
            mLocationList.setVisibility(View.VISIBLE);
            mSpot = spot;
            mTitle.setText(mSpot.getTitle());
            mDescription.setText(mSpot.getDescription());
            Glide.with(mLocationImageView.getContext())
                    .load(mSpot.getImageUrl1())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_terrain_black_24dp))
                    .into(mLocationImageView);
            mLocationImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
    }

    private class SpotAdapter extends RecyclerView.Adapter<FriendsLocationListFragment.SpotHolder> {

        private List<Spot> mSpots;

        private SpotAdapter(List<Spot> spots) {
            mSpots = spots;
        }


        @NonNull
        @Override
        public FriendsLocationListFragment.SpotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new FriendsLocationListFragment.SpotHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendsLocationListFragment.SpotHolder holder, int position) {
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

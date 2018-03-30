package com.jackal.android.spots.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.User;

import java.io.File;
import java.util.List;

/**
 * Created by Jack on 3/24/18.
 */

public class AddLocationFragment extends Fragment {

    private static final String SPOTS_USER = "spots user";


    private Spot mSpot;
    private User mUser;

    private File mPhotoFile;

    private ImageView mLocationImage1;
    private ImageView mLocationImage2;
    private ImageView mLocationImage3;
    private EditText mTitle;
    private EditText mDescription;
    private Button mAddLocationButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public static AddLocationFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putSerializable(SPOTS_USER, user);

        AddLocationFragment fragment = new AddLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = (User) getArguments().getSerializable(SPOTS_USER);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(mUser.getUserId())
        .child("locations");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_location_page_fragment, container, false);

        PackageManager packageManager = getActivity().getPackageManager();

        mTitle = view.findViewById(R.id.add_location_title);
        mDescription = view.findViewById(R.id.add_location_description);

        mAddLocationButton = (Button) view.findViewById(R.id.add_location_submit_button);
        mAddLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpot = new Spot(100, 100,
                        mTitle.getText().toString(), mDescription.getText().toString());

                mDatabaseReference.push().setValue(mSpot);

                //Intent intent = new Intent(getActivity(), DashboardActivity.class);
                //startActivity(intent);

                getActivity().finish();
            }
        });


        mLocationImage1 = view.findViewById(R.id.add_location_image_view_1);

        mLocationImage2 = view.findViewById(R.id.add_location_image_view_2);

        mLocationImage3 = view.findViewById(R.id.add_location_image_view_3);

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

    /*
    private void onClickImageView(View view) {

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager)
                != null;


        Uri uri = FileProvider.getUriForFile(getActivity(),
                "com.bignerdranch.android.criminalintent.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivites = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity: cameraActivites) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }


    }
    */
}

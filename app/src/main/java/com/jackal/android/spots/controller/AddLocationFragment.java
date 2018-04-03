package com.jackal.android.spots.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.SpotSingleton;
import com.jackal.android.spots.model.User;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jack on 3/24/18.
 */

public class AddLocationFragment extends Fragment {

    private static final String SPOTS_USER = "spots user";

    private static final String TAG = "AddLocationFragment";

    private static final int REQUEST_PHOTO = 1;


    private Spot mSpot;
    private User mUser;

    private int mSelectedImageView;

    private File[] mPhotoFiles;

    private ImageView mLocationImage1;
    private ImageView mLocationImage2;
    private ImageView mLocationImage3;
    private EditText mTitle;
    private EditText mDescription;
    private Button mAddLocationButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mSpotPhotosRef;

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

        mSpot = new Spot();
        mSpot.setLocationID(UUID.randomUUID().toString());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(mUser.getUserId())
        .child("locations");
        mSpotPhotosRef = mFirebaseStorage.getReference().child("spots_photos");

        mPhotoFiles = SpotSingleton.get(getActivity()).getPhotoFiles(mSpot);
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

                mSpot.setDescription(mDescription.getText().toString());
                mSpot.setTitle(mTitle.getText().toString());
                mSpot.setLat(100);
                mSpot.setLon(100);

                Uri imageUri = Uri.fromFile(mPhotoFiles[0]);
                StorageReference photoRef1 = mSpotPhotosRef.child(imageUri.getLastPathSegment());
                photoRef1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mSpot.setImageUrl1(taskSnapshot.getDownloadUrl().toString());
                        Log.i(TAG, "Image 1 placed in database and assigned to spot");
                    }
                });

                imageUri = Uri.fromFile(mPhotoFiles[1]);
                StorageReference photoRef2 = mSpotPhotosRef.child(imageUri.getLastPathSegment());
                photoRef2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mSpot.setImageUrl2(taskSnapshot.getDownloadUrl().toString());
                        Log.i(TAG, "Image 2 placed in database and assigned to spot");
                    }
                });

                imageUri = Uri.fromFile(mPhotoFiles[2]);
                StorageReference photoRef3 = mSpotPhotosRef.child(imageUri.getLastPathSegment());
                photoRef3.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mSpot.setImageUrl3(taskSnapshot.getDownloadUrl().toString());
                        Log.i(TAG, "Image 3 placed in database and assigned to spot");
                        mDatabaseReference.push().setValue(mSpot);
                        Log.i(TAG, "Spot pushed to database");
                    }
                });


                getActivity().finish();
            }
        });


        mLocationImage1 = view.findViewById(R.id.add_location_image_view_1);
        ClickImageView clickListener1 = new ClickImageView();
        clickListener1.setFileNumber(0);
        mLocationImage1.setOnClickListener(clickListener1);

        mLocationImage2 = view.findViewById(R.id.add_location_image_view_2);
        ClickImageView clickListener2 = new ClickImageView();
        clickListener2.setFileNumber(1);
        mLocationImage2.setOnClickListener(clickListener2);

        mLocationImage3 = view.findViewById(R.id.add_location_image_view_3);
        ClickImageView clickListener3 = new ClickImageView();
        clickListener3.setFileNumber(2);
        mLocationImage3.setOnClickListener(clickListener3);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == getActivity().RESULT_OK) {
            /*Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.jackal.android.spots.fileprovider",
                    mPhotoFiles);*/
            int fileNum = mSelectedImageView;
            Log.i(TAG, "onActivityResult fileNum: " + fileNum +
                    "\nFile uri = " + mPhotoFiles[fileNum].getPath());
            updatePhotoView(fileNum);
        }
    }

    private class ClickImageView implements View.OnClickListener {

        private int fileNumber;

        public void setFileNumber(int number) {
            fileNumber = number;
            Log.i(TAG, "ClickEvent fileNumber: " + fileNumber);
        }

        public void onClick(View view) {

            final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            PackageManager packageManager = getActivity().getPackageManager();

            boolean canTakePhoto = mPhotoFiles[fileNumber] != null && captureImage.resolveActivity(packageManager)
                    != null;


            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.jackal.android.spots.fileprovider", mPhotoFiles[fileNumber]);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            List<ResolveInfo> cameraActivites = getActivity().getPackageManager()
                    .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo activity: cameraActivites) {
                getActivity().grantUriPermission(activity.activityInfo.packageName,
                        uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            mSelectedImageView = fileNumber;

            startActivityForResult(captureImage, REQUEST_PHOTO);
        }
    }

    private void updatePhotoView(int fileNum) {
        ImageView imageView;

        if (fileNum == 0) {
            imageView = mLocationImage1;
        }
        else if (fileNum == 1) {
            imageView = mLocationImage2;
        }
        else {
            imageView = mLocationImage3;
        }

        if (mPhotoFiles[fileNum] == null || !mPhotoFiles[fileNum].exists()) {
            imageView.setImageDrawable(null);
        }
        else {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(imageView.getContext())
                    .load(mPhotoFiles[fileNum].getPath())
                    .into(imageView);
        }
    }

}



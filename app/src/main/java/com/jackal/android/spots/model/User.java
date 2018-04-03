package com.jackal.android.spots.model;

import java.io.Serializable;

public class User implements Serializable {

    private String mUserId;

    private String mUserName;

    public User(String id, String name) {
        mUserId = id;
        mUserName = name;
    }


    public String getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }
}

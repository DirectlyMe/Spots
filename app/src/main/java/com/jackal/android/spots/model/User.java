package com.jackal.android.spots.model;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;

    private String name;

    private String email;

    public User(String id, String name, String email) {
        userId = id;
        this.name = name;
        this.email = email;
    }


    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

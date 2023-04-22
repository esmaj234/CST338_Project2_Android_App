package com.example.garagesale;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.garagesale.db.AppDatabase;
import com.example.garagesale.db.UserCredentialDAO;

@Entity(tableName = AppDatabase.USERCRED_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    @ColumnInfo(name = "is_admin")
    private boolean mIsAdmin;


    public User(String mUsername, String mPassword, boolean mIsAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mIsAdmin = mIsAdmin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean mUserType) {
        this.mIsAdmin = mUserType;
    }
}

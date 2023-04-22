package com.example.garagesale.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.garagesale.User;

import java.util.List;

@Dao
public interface UserCredentialDAO {
    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USERCRED_TABLE)
    List<User> getUserCredentials();


    @Query("SELECT * FROM " + AppDatabase.USERCRED_TABLE + " WHERE mUsername = :username")
    User getUserCredentialsByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USERCRED_TABLE + " WHERE mUserId = :userId")
    User getUserCredentialsById(int userId);

    @Query("SELECT * FROM " + AppDatabase.USERCRED_TABLE + " WHERE mUserId = :userId")
   boolean getIsAdminByUserId(int userId);

}

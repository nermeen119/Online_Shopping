package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.online_shopping.model.CartAdminModel;
import com.example.online_shopping.model.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from user_table where username=(:username) and password=(:password)")
    UserEntity login(String username, String password);

    @Query("SELECT * from user_table where username=(:username) and job=(:job)")
    UserEntity CheckUsername(String username,String job);

    @Update
    void UpdatePassword(UserEntity user);

    @Query("SELECT * FROM user_table WHERE userName=:name")
    UserEntity getAccountByName(String name);


}

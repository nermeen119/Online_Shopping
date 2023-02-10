package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.online_shopping.model.CartAdminModel;

import java.util.List;


@Dao
public interface CartAdminDao {

    @Insert
    void insert(CartAdminModel cartAdmin);

    @Query("SELECT * FROM cart_admin_table WHERE date=:date")
    LiveData<List<CartAdminModel>> getAllProductsByDate(String date);

    @Query("SELECT * FROM cart_admin_table WHERE userName=:name")
    LiveData<List<CartAdminModel>> getAllProductsByName(String name);

}

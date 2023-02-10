package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.online_shopping.model.Cart;
import com.example.online_shopping.model.FeedbackModel;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Query("DELETE FROM baskets_tables WHERE id=:id and userName=:username")
    void deleteProductByUserName(Integer id, String username);
    @Query("DELETE FROM baskets_tables WHERE itemTitle=:title")
    void deleteProductWithName(String title);

    @Query("SELECT * FROM baskets_tables WHERE userName=:userName")
    LiveData<List<Cart>> getAllProductsByUserName(String userName);

}

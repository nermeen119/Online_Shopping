package com.example.online_shopping.user;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.product;

import java.util.List;

@Dao
public interface AllProductDao {


    @Insert
    void insertProduct(List<AllProductModel> products);

    @Query("SELECT * FROM all_product_table")
    LiveData<List<AllProductModel>> getAllProducts();

    @Query("SELECT * from all_product_table where title=(:title)")
    AllProductModel returnProduct(String title);

    @Update
    void UpdatePurchaseCount(AllProductModel productUpdate);

    @Query("SELECT * from all_product_table ORDER BY purchaseCount DESC LIMIT 5")
    List<AllProductModel> getTopProducts();

    @Update
    void editProduct(AllProductModel products);

}

package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.product;
import java.util.List;

@Dao
public interface productDao {

    @Insert
    void insertProduct(product products );

    @Query("SELECT * from product_table where title=(:title)")
    product returnProduct(String title);

    @Query("SELECT * FROM product_table")
    LiveData<List<product>> getAllProducts();

    @Query("DELETE FROM product_table  WHERE title=:title")
    void deleteProductWithName(String title);

    @Update
    void UpdatePurchaseCount(product productUpdate);

    @Update
    void editProduct(product products);

    @Query("SELECT * from product_table ORDER BY purchaseCount DESC LIMIT 5")
    List<product> getTopProducts();



}

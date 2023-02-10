package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface categoryDao {
    @Insert
    void insert_category(CategoryModel name);

    @Query("SELECT * FROM category_table")
   LiveData <List<CategoryModel>> getcategory();
    @Query("DELETE FROM category_table  WHERE title=:title")
    void deleteCategorytWithName(String title);

    @Delete
    void deleteAllCategory(CategoryModel title1);
    @Update
    void editCategory(CategoryModel title);
    @Query("SELECT * from category_table where title=:title")
    CategoryModel Checktitle(String title);

}

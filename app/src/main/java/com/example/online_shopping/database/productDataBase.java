package com.example.online_shopping.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.converter;
import com.example.online_shopping.model.product;
import com.example.online_shopping.user.categoryDao;
import com.example.online_shopping.user.productDao;

@Database(entities = product.class,version =1)
@TypeConverters({converter.class})

public abstract class productDataBase extends RoomDatabase {
    private static productDataBase Instance=null;
    public abstract productDao productDao();
    public static productDataBase Get_Instance(Context con)
    {
        if (Instance==null)
        {
            Instance= Room.databaseBuilder(con.getApplicationContext(), productDataBase.class,"product_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}

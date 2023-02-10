package com.example.online_shopping.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.online_shopping.model.AllProductModel;
import com.example.online_shopping.model.converter;
import com.example.online_shopping.user.AllProductDao;

@Database(entities = AllProductModel.class,version =7)
@TypeConverters({converter.class})

public abstract class AllProductDatabase extends RoomDatabase {
    private static AllProductDatabase Instance=null;
    public abstract AllProductDao allProductDao();
    public static AllProductDatabase Get_Instance(Context con)
    {
        if (Instance==null)
        {
            Instance= Room.databaseBuilder(con.getApplicationContext(), AllProductDatabase.class,"all_product_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}
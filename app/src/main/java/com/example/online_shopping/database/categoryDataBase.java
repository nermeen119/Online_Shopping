package com.example.online_shopping.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.online_shopping.model.CategoryModel;
import com.example.online_shopping.model.converter;
import com.example.online_shopping.user.categoryDao;

@Database(entities = CategoryModel.class,version =2)
@TypeConverters({converter.class})

public abstract class categoryDataBase extends RoomDatabase {
    private static categoryDataBase Instance=null;
    public abstract categoryDao category_dao();
    public static categoryDataBase Get_Instance(Context con)
    {
        if (Instance==null)
        {
            Instance= Room.databaseBuilder(con.getApplicationContext(),categoryDataBase.class,"category_tables")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}

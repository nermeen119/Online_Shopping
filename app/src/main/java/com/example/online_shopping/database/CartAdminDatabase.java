package com.example.online_shopping.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.online_shopping.model.CartAdminModel;
import com.example.online_shopping.user.CartAdminDao;

@Database(entities = {CartAdminModel.class},version = 1)
public abstract class CartAdminDatabase extends RoomDatabase {

    private static CartAdminDatabase instance;
    public abstract CartAdminDao Deo();
    public static CartAdminDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    CartAdminDatabase.class,"cart_admin_table").fallbackToDestructiveMigration().build();

        }
        return instance;
    }

}

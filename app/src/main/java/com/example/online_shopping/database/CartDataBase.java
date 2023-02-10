package com.example.online_shopping.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.online_shopping.model.Cart;
import com.example.online_shopping.user.CartDao;


@Database(entities = {Cart.class},version = 1)

public abstract class CartDataBase extends RoomDatabase {

    private static CartDataBase instance;
    public abstract CartDao Deo();
    public static CartDataBase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    CartDataBase.class,"baskets_databases").fallbackToDestructiveMigration().build();

        }
        return instance;
    }
}

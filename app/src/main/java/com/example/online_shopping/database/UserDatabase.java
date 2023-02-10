package com.example.online_shopping.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.online_shopping.user.UserDao;
import com.example.online_shopping.model.UserEntity;

@Database(entities = {UserEntity.class},version = 3)
public abstract class UserDatabase extends RoomDatabase {

    private static final String dbName="user";
    private static UserDatabase uersDatabase;

    public static synchronized UserDatabase getUsersDatabase(Context context)
    {

        if(uersDatabase==null)
        {
            uersDatabase = Room.databaseBuilder(context,UserDatabase.class,dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return uersDatabase;

    }

    public abstract UserDao userDao();

}

package com.example.online_shopping.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.online_shopping.model.FeedbackModel;
import com.example.online_shopping.user.FeedbackDao;

@Database(entities = {FeedbackModel.class},version = 1)

public abstract class FeedbackDatabase extends RoomDatabase {
    private static FeedbackDatabase instance;
    public abstract FeedbackDao Deo();
    public static FeedbackDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    FeedbackDatabase.class,"feedback_table").fallbackToDestructiveMigration().build();

        }
        return instance;
    }
}
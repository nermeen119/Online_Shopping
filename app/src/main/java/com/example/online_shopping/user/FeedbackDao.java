package com.example.online_shopping.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.online_shopping.model.FeedbackModel;

import java.util.List;

@Dao
public interface FeedbackDao {

    @Insert
    void insert(FeedbackModel feedbackModel);

    @Query("DELETE FROM feedback_table WHERE id=:id")
    void deleteFeedback(Integer id);

    @Query("SELECT * FROM feedback_table WHERE userName=:userName")
    LiveData<List<FeedbackModel>> getAllFeedbackByUserName(String userName);

    @Query("SELECT * FROM feedback_table")
    LiveData<List<FeedbackModel>> getAllFeedback();


}

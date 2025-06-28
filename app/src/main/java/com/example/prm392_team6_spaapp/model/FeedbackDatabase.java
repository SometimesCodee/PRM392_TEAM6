package com.example.prm392_team6_spaapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Feedback.class}, version = 1)
public abstract class FeedbackDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "feedback.db";
    private static FeedbackDatabase instance;

    public static synchronized FeedbackDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FeedbackDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract FeedbackDAO getFeedbackDAO();
} 
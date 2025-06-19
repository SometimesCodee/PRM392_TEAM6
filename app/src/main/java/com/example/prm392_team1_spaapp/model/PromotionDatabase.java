package com.example.prm392_team1_spaapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Promotion.class,version = 1)
public abstract class PromotionDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Promotion.db";
    private static PromotionDatabase instance;
    public static synchronized PromotionDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PromotionDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract PromotionDAO getPromotionDAO();
}

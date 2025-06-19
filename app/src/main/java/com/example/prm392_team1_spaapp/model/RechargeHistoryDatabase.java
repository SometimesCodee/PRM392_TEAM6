package com.example.prm392_team1_spaapp.model;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RechargeHistory.class}, version = 1)
public abstract class RechargeHistoryDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "RechargeHistory.db";
    private static RechargeHistoryDatabase instance;

    public static synchronized  RechargeHistoryDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),RechargeHistoryDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract RechargeHistoryDAO getHistoryDAO();
}

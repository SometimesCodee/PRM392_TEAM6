package com.example.prm392_team1_spaapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Service.class,version = 1)
public abstract class ServiceDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Service.db";
    private static ServiceDatabase instance;
    public static synchronized ServiceDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ServiceDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract ServiceDAO getServiceDAO();
}

package com.example.prm392_team6_spaapp.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChatEntity.class}, version = 2)
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
}
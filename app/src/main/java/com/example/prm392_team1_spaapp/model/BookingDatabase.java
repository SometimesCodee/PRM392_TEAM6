package com.example.prm392_team1_spaapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Booking.class,version = 1)
public abstract class BookingDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Booking.db";

    private static BookingDatabase instance;
    public static synchronized BookingDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),BookingDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract BookingDAO getBookingDAO();
}

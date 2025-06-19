package com.example.prm392_team1_spaapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookingDAO {
    @Insert
    public void addBooking(Booking booking);
    @Update
    public void updateBooking(Booking booking);
    @Delete
    public void deleteBooking(Booking booking);
    @Query("SELECT * FROM booking")
    public List<Booking> getAllBooking();
    @Query("SELECT * FROM booking WHERE username ==:username")
    public List<Booking> getAllBookingOfAccount(String username);
}

package com.example.prm392_team1_spaapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PromotionDAO {
    @Insert
    public void addPromotion(Promotion promotion);
    @Update
    public void updatePromotion(Promotion promotion);
    @Delete
    public void deletePromotion(Promotion promotion);
    @Query("SELECT * FROM Promotion")
    public List<Promotion> getAllPromotion();
}

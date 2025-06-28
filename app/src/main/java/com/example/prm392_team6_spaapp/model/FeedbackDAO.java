package com.example.prm392_team6_spaapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedbackDAO {
    
    @Insert
    void addFeedback(Feedback feedback);
    
    @Query("SELECT * FROM Feedback WHERE service_id = :serviceId ORDER BY feedbackId DESC")
    List<Feedback> getFeedbacksByServiceId(int serviceId);
    
    @Query("SELECT * FROM Feedback WHERE username = :username ORDER BY feedbackId DESC")
    List<Feedback> getFeedbacksByUsername(String username);
    
    @Query("SELECT * FROM Feedback ORDER BY feedbackId DESC")
    List<Feedback> getAllFeedbacks();
    
    @Query("SELECT AVG(rating) FROM Feedback WHERE service_id = :serviceId")
    Float getAverageRatingByServiceId(int serviceId);
    
    @Query("SELECT COUNT(*) FROM Feedback WHERE service_id = :serviceId")
    int getFeedbackCountByServiceId(int serviceId);
    
    @Query("DELETE FROM Feedback WHERE feedbackId = :feedbackId")
    void deleteFeedback(int feedbackId);
} 
package com.example.prm392_team6_spaapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Feedback implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int feedbackId;
    
    @ColumnInfo(name = "service_id")
    private int serviceId;
    
    @ColumnInfo(name = "username")
    private String username;
    
    @ColumnInfo(name = "rating")
    private float rating;
    
    @ColumnInfo(name = "comment")
    private String comment;
    
    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @Ignore
    public Feedback() {
        // Constructor mặc định
    }

    public Feedback(int serviceId, String username, float rating, String comment, String dateCreated) {
        this.serviceId = serviceId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.dateCreated = dateCreated;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
} 
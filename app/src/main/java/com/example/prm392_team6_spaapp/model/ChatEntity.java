package com.example.prm392_team6_spaapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_history")
public class ChatEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String message;
    public boolean isUser;
    public long timestamp;

    public String username;
    public ChatEntity(String message, boolean isUser, long timestamp, String username) {
        this.message = message;
        this.isUser = isUser;
        this.timestamp = timestamp;
        this.username = username;
    }
}

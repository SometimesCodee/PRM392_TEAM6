package com.example.prm392_team6_spaapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert
    void insert(ChatEntity message);

    @Query("SELECT * FROM chat_history WHERE username = :username ORDER BY timestamp ASC")
    List<ChatEntity> getMessagesForUser(String username);

    @Query("DELETE FROM chat_history WHERE username = :username")
    void deleteMessagesForUser(String username);
}

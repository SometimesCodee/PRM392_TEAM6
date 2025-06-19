package com.example.prm392_team1_spaapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface AccountDAO {
    @Insert
    public void addAccount(Account account);
    @Update
    public void updateAccount(Account account);
    @Delete
    public void deleteAccount(Account account);
    @Query("SELECT * FROM Account")
    public List<Account> getAllAccount();
    @Query("SELECT * FROM Account WHERE username ==:username")
    public Account getAccount(String username);
    @Query("SELECT * FROM Account WHERE username ==:username AND password ==:password")
    public Account getAccount(String username,String password);
    @Query("UPDATE Account SET money =:money WHERE username ==:username")
    public void updateMoney(String username,float money);
}

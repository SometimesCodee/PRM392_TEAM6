package com.example.prm392_team1_spaapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface RechargeHistoryDAO {
    @Insert
    public void addHistory(RechargeHistory rechargeHistory);
    @Update
    public void updateHistory(RechargeHistory rechargeHistory);
    @Delete
    public void deleteHistory(RechargeHistory rechargeHistory);
    @Query("select * from RechargeHistory")
    public List<RechargeHistory> getAllHistory();
    @Query("select * from RechargeHistory where recharge_history_id ==:recharge_history_id")
    public RechargeHistory getHistory(int recharge_history_id);
    @Query("select * from RechargeHistory where username ==:username")
    public List<RechargeHistory> getAllHistoriesOfAccount(String username);
}

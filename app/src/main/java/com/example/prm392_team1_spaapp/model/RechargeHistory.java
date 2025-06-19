package com.example.prm392_team1_spaapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "RechargeHistory")
public class RechargeHistory {
    @ColumnInfo(name = "recharge_history_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "transaction_type")
    private String transactionType;
    @ColumnInfo(name = "transaction_time")
    private String transactionTime;
    @ColumnInfo(name = "amount")
    private float amount;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name = "description")
    private String description;
    @Ignore()
    public RechargeHistory(){

    }

    public RechargeHistory(String username, String transactionType, String transactionTime, float amount, int status, String description) {
        this.username = username;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

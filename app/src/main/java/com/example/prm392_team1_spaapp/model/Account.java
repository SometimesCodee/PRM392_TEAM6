package com.example.prm392_team1_spaapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Account")
public class Account {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "full_name")
    private String fullName;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "birth_date")
    private String birthDate;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "avatar")
    private int avatar;
    @ColumnInfo(name = "money")
    private float money;

    @Ignore
    public Account() {

    }
    @Ignore
    public Account(@NonNull String username, String password, String email, String phoneNumber, String gender, String birthDate, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Account(String username, String password, String email, String phoneNumber,
                   String fullName, String gender, String birthDate, String address, int avatar, float money) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.avatar = avatar;
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    private void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    private void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public int getAvatar() {
        return avatar;
    }

    private void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}

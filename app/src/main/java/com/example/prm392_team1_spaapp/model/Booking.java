package com.example.prm392_team1_spaapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Booking")
public class Booking {
    @PrimaryKey(autoGenerate = true)
    private int bookingId;

    @ColumnInfo(name = "username")
    private String username;  // Liên kết với tài khoản người dùng

    @ColumnInfo(name = "serviceId")
    private int serviceId;  // Liên kết với dịch vụ được đặt

    @ColumnInfo(name = "bookingDate")
    private String bookingDate;  // Ngày đặt lịch

    @ColumnInfo(name = "bookingTime")
    private String bookingTime;  // Thời gian đặt lịch

    @ColumnInfo(name = "status")
    private String status;  // Trạng thái của đặt lịch (đã xác nhận, chưa xác nhận, ...)

    @ColumnInfo(name = "bookingPrice")
    private double bookingPrice;  // Giá tiền của đặt lịch

    public Booking(String username, int serviceId, String bookingDate, String bookingTime, String status, double bookingPrice) {
        this.username = username;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.status = status;
        this.bookingPrice = bookingPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }
}


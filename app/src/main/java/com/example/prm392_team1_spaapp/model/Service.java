package com.example.prm392_team1_spaapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Service implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int serviceId;
    @ColumnInfo(name = "service_name")
    private String serviceName;  // Tên dịch vụ
    @ColumnInfo(name = "price")
    private double price;        // Giá dịch vụ
    @ColumnInfo(name = "duration")
    private String duration;     // Thời gian dự kiến
    @ColumnInfo(name = "description")
    private String description;  // Mô tả dịch vụ
    @ColumnInfo(name = "image")
    private int img;
    @Ignore
    public Service() {
        // Constructor mặc định
    }

    public Service(String serviceName, double price, String duration, String description, int img) {
        this.serviceName = serviceName;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    private void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    private void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }
}


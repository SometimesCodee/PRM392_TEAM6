package com.example.prm392_team1_spaapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ServiceDAO {
    @Insert
    public void addService(Service service);
    @Update
    public void updateService(Service service);
    @Delete
    public void deleteService(Service service);
    @Query("SELECT * FROM Service")
    public List<Service> getAllService();
    @Query("SELECT * FROM Service WHERE serviceId ==:serviceId")
    public Service getService(int serviceId);
}

package com.example.prm392_team6_spaapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.AccountDatabase;
import com.example.prm392_team6_spaapp.model.Booking;
import com.example.prm392_team6_spaapp.model.BookingDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    private TextView tvServiceName, tvServicePrice, tvServiceDuration;
    private TextView tvSelectedDate, tvSelectedTime;
    private Button btnSelectDate, btnSelectTime, btnConfirmBooking;
    private ImageView btnBack;
    
    private int serviceId;
    private String serviceName;
    private double servicePrice;
    private String serviceDuration;
    private String username;
    
    private String selectedDate = "";
    private String selectedTime = "";
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        
        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        serviceId = intent.getIntExtra("service_id", 0);
        serviceName = intent.getStringExtra("service_name");
        servicePrice = intent.getDoubleExtra("service_price", 0.0);
        serviceDuration = intent.getStringExtra("service_duration");
        username = intent.getStringExtra("username");
        
        initViews();
        setupData();
        setupListeners();
    }
    
    private void initViews() {
        tvServiceName = findViewById(R.id.tv_service_name);
        tvServicePrice = findViewById(R.id.tv_service_price);
        tvServiceDuration = findViewById(R.id.tv_service_duration);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);
        btnBack = findViewById(R.id.btn_back);
    }
    
    private void setupData() {
        tvServiceName.setText(serviceName);
        tvServicePrice.setText(String.format(Locale.getDefault(), "%.0fđ", servicePrice));
        tvServiceDuration.setText(serviceDuration);
        
        // Set ngày mặc định là ngày mai
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        updateDateDisplay();
    }
    
    private void setupListeners() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        btnSelectTime.setOnClickListener(v -> showTimePicker());
        btnConfirmBooking.setOnClickListener(v -> confirmBooking());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateDisplay();
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Chỉ cho phép chọn từ ngày mai trở đi
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        datePickerDialog.show();
    }
    
    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
            this,
            (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeDisplay();
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        );
        timePickerDialog.show();
    }
    
    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        selectedDate = dateFormat.format(calendar.getTime());
        tvSelectedDate.setText(selectedDate);
    }
    
    private void updateTimeDisplay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        selectedTime = timeFormat.format(calendar.getTime());
        tvSelectedTime.setText(selectedTime);
    }
    
    private void confirmBooking() {
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày và giờ!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Kiểm tra xem đã chọn ngày trong tương lai chưa
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        selectedCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        selectedCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        selectedCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        selectedCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        
        Calendar now = Calendar.getInstance();
        if (selectedCalendar.before(now)) {
            Toast.makeText(this, "Vui lòng chọn thời gian trong tương lai!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Kiểm tra số dư trước khi hiển thị dialog
        float currentMoney = DataLocalManager.getInstance().getPrefMoney();
        if (servicePrice > currentMoney) {
            Toast.makeText(this, "Số dư không đủ để đặt lịch! Số dư hiện tại: " + 
                String.format(Locale.getDefault(), "%.0fđ", currentMoney), Toast.LENGTH_LONG).show();
            return;
        }
        
        // Hiển thị dialog xác nhận
        new AlertDialog.Builder(this)
            .setTitle("Xác nhận đặt lịch")
            .setMessage(String.format("Bạn có chắc chắn muốn đặt lịch:\n\n" +
                "Dịch vụ: %s\n" +
                "Ngày: %s\n" +
                "Giờ: %s\n" +
                "Giá: %.0fđ\n" +
                "Số dư hiện tại: %.0fđ\n" +
                "Số dư sau khi đặt lịch: %.0fđ", 
                serviceName, selectedDate, selectedTime, servicePrice, 
                currentMoney, currentMoney - (float)servicePrice))
            .setPositiveButton("Xác nhận", (dialog, which) -> {
                saveBooking();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }
    
    private void saveBooking() {
        try {
            // Lấy số dư hiện tại
            float currentMoney = DataLocalManager.getInstance().getPrefMoney();
            if (servicePrice > currentMoney) {
                Toast.makeText(this, "Số dư không đủ để đặt lịch!", Toast.LENGTH_LONG).show();
                return;
            }
            float newMoney = currentMoney - (float) servicePrice;
            // Cập nhật số dư vào database và local
            AccountDatabase.getInstance(this).getAccountDAO().updateMoney(username, newMoney);
            DataLocalManager.getInstance().setPrefMoney(newMoney);

            Booking booking = new Booking(
                username,
                serviceId,
                selectedDate,
                selectedTime,
                "Chờ xác nhận",
                servicePrice
            );
            BookingDatabase.getInstance(this).getBookingDAO().addBooking(booking);
            
            // Chuyển đến màn hình thành công
            Intent intent = new Intent(this, BookingSuccessActivity.class);
            intent.putExtra("service_name", serviceName);
            intent.putExtra("service_price", servicePrice);
            intent.putExtra("booking_date", selectedDate);
            intent.putExtra("booking_time", selectedTime);
            intent.putExtra("new_balance", newMoney);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi đặt lịch: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 
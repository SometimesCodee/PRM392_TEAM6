package com.example.prm392_team6_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingSuccessActivity extends AppCompatActivity {
    private TextView tvServiceName, tvServicePrice, tvBookingDate, tvBookingTime;
    private TextView tvNewBalance, tvTransactionTime, tvTransactionId;
    private Button btnBackToHome, btnViewBookings;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);
        
        initViews();
        setupData();
        setupListeners();
    }
    
    private void initViews() {
        tvServiceName = findViewById(R.id.tv_service_name);
        tvServicePrice = findViewById(R.id.tv_service_price);
        tvBookingDate = findViewById(R.id.tv_booking_date);
        tvBookingTime = findViewById(R.id.tv_booking_time);
        tvNewBalance = findViewById(R.id.tv_new_balance);
        tvTransactionTime = findViewById(R.id.tv_transaction_time);
        tvTransactionId = findViewById(R.id.tv_transaction_id);
        btnBackToHome = findViewById(R.id.btn_back_to_home);
        btnViewBookings = findViewById(R.id.btn_view_bookings);
    }
    
    private void setupData() {
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String serviceName = intent.getStringExtra("service_name");
        double servicePrice = intent.getDoubleExtra("service_price", 0.0);
        String bookingDate = intent.getStringExtra("booking_date");
        String bookingTime = intent.getStringExtra("booking_time");
        float newBalance = intent.getFloatExtra("new_balance", 0.0f);
        
        // Hiển thị thông tin
        tvServiceName.setText(serviceName);
        tvServicePrice.setText(String.format(Locale.getDefault(), "%.0fđ", servicePrice));
        tvBookingDate.setText(bookingDate);
        tvBookingTime.setText(bookingTime);
        tvNewBalance.setText(String.format(Locale.getDefault(), "%.0fđ", newBalance));
        
        // Thời gian giao dịch
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(new Date());
        tvTransactionTime.setText(currentTime);
        
        // Mã giao dịch (tạo ngẫu nhiên)
        String transactionId = "BK" + System.currentTimeMillis();
        tvTransactionId.setText(transactionId);
    }
    
    private void setupListeners() {
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        
        btnViewBookings.setOnClickListener(v -> {
            // Chuyển đến tab History để xem lịch sử đặt lịch
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("open_history", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
} 
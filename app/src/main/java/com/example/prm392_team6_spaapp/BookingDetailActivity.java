package com.example.prm392_team6_spaapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prm392_team6_spaapp.model.Booking;
import com.example.prm392_team6_spaapp.model.BookingDatabase;
import com.example.prm392_team6_spaapp.model.Service;
import com.example.prm392_team6_spaapp.model.ServiceDatabase;
import android.view.View;

public class BookingDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        int bookingId = getIntent().getIntExtra("bookingId", -1);
        if (bookingId == -1) {
            finish();
            return;
        }
        Booking booking = BookingDatabase.getInstance(this).getBookingDAO().getBookingById(bookingId);
        if (booking == null) {
            finish();
            return;
        }
        Service service = ServiceDatabase.getInstance(this).getServiceDAO().getService(booking.getServiceId());

        TextView tvServiceName = findViewById(R.id.tv_service_name);
        TextView tvBookingDate = findViewById(R.id.tv_booking_date);
        TextView tvBookingTime = findViewById(R.id.tv_booking_time);
        TextView tvStatus = findViewById(R.id.tv_status);
        TextView tvPrice = findViewById(R.id.tv_price);
        TextView tvServiceDesc = findViewById(R.id.tv_service_desc);
        ImageView imgService = findViewById(R.id.img_service);
        ImageView btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (service != null) {
            tvServiceName.setText(service.getServiceName());
            tvServiceDesc.setText(service.getDescription());
            imgService.setImageResource(service.getImg());
            tvPrice.setText(String.format("%.0fđ", service.getPrice()));
        } else {
            tvServiceName.setText("Dịch vụ đã xóa");
        }
        tvBookingDate.setText(booking.getBookingDate());
        tvBookingTime.setText(booking.getBookingTime());
        tvStatus.setText(booking.getStatus());
    }
} 
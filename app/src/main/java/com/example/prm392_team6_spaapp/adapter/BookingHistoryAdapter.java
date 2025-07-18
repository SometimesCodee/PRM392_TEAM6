package com.example.prm392_team6_spaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.model.Booking;
import com.example.prm392_team6_spaapp.ServiceDetailActivity;
import com.example.prm392_team6_spaapp.BookingDetailActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder> {
    private List<Booking> bookingList;

    public BookingHistoryAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        if (booking == null) return;
        holder.tvName.setText("Dịch vụ ID: " + booking.getServiceId());
        holder.tvDate.setText(booking.getBookingDate() + " " + booking.getBookingTime());
        holder.description.setText("Trạng thái: " + booking.getStatus());
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedAmount = numberFormat.format(booking.getBookingPrice()) + "đ";
        holder.money.setText(formattedAmount);
        holder.status.setText(booking.getStatus());
        // Xóa sự kiện click vào item (không làm gì khi click)
        holder.itemView.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return bookingList != null ? bookingList.size() : 0;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, description, money, status;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            description = itemView.findViewById(R.id.description);
            money = itemView.findViewById(R.id.money);
            status = itemView.findViewById(R.id.status);
        }
    }
} 
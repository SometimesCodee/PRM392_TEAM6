package com.example.prm392_team6_spaapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_team6_spaapp.model.Booking;
import com.example.prm392_team6_spaapp.model.BookingDatabase;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.adapter.BookingHistoryAdapter;


public class PhoneCateHistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_booking, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_booking_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String username = DataLocalManager.getInstance().getPrefUsername();
        List<Booking> bookingList = BookingDatabase.getInstance(getContext()).getBookingDAO().getAllBookingOfAccount(username);
        // Sắp xếp lịch đặt mới nhất lên đầu
        Collections.sort(bookingList, new Comparator<Booking>() {
            @Override
            public int compare(Booking o1, Booking o2) {
                // So sánh ngày đặt, nếu giống thì so sánh giờ
                int dateCompare = o2.getBookingDate().compareTo(o1.getBookingDate());
                if (dateCompare == 0) {
                    return o2.getBookingTime().compareTo(o1.getBookingTime());
                }
                return dateCompare;
            }
        });

        BookingHistoryAdapter adapter = new BookingHistoryAdapter(bookingList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

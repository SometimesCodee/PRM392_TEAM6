package com.example.prm392_team6_spaapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prm392_team6_spaapp.MainActivity;
import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.adapter.ListHistoryAdapter;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.RechargeHistory;
import com.example.prm392_team6_spaapp.model.RechargeHistoryDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RechargeHistoryFragment extends Fragment {
    private static final String TABLE_NAME = "bank_trans";
    private RecyclerView recyclerView;
    private ListHistoryAdapter adapter;
    private List<RechargeHistory> listRechargeHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        
        recyclerView = view.findViewById(R.id.rcv_cate_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        adapter = new ListHistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        
        loadData();
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
    
    private void loadData() {
        // Lấy lịch sử nạp tiền từ database
        listRechargeHistory = RechargeHistoryDatabase.getInstance(getContext()).getHistoryDAO().getHistoriesByType(DataLocalManager.getInstance().getPrefUsername(), "Nạp tiền");
        adapter.setData(listRechargeHistory);
    }
}

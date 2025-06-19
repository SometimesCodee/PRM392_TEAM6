package com.example.prm392_team1_spaapp.fragment;

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

import com.example.prm392_team1_spaapp.MainActivity;
import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.adapter.ListHistoryAdapter;
import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.model.RechargeHistory;
import com.example.prm392_team1_spaapp.model.RechargeHistoryDatabase;

import java.util.ArrayList;
import java.util.List;


public class RechargeHistoryFragment extends Fragment {
    private static final String TABLE_NAME = "bank_trans";
    private RecyclerView recyclerView;
    private ListHistoryAdapter adapter;
    private List<RechargeHistory> listRechargeHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);;
        listRechargeHistory = RechargeHistoryDatabase.getInstance(getContext()).getHistoryDAO().getAllHistoriesOfAccount(DataLocalManager.getInstance().getPrefUsername());
        if (listRechargeHistory.isEmpty()) {
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 12000, 1, "Bạn đã nạp tiền thành công"));
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 100000, 0, "Bạn đã nạp tiền thất bại"));
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 562000, 1, "Bạn đã nạp tiền thành công"));
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 123000, 0, "Bạn đã nạp tiền thất bại"));
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 52300, 1, "Bạn đã nạp tiền thành công"));
            listRechargeHistory.add(new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 33000, 1, "Bạn đã nạp tiền thành công"));
        }
        recyclerView = view.findViewById(R.id.rcv_cate_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        adapter = new ListHistoryAdapter(listRechargeHistory);
        recyclerView.setAdapter(adapter);
        return view;

    }
}

package com.example.prm392_team6_spaapp.fragment;

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

import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.adapter.ListHistoryAdapter;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.RechargeHistory;
import com.example.prm392_team6_spaapp.model.RechargeHistoryDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TransactionHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListHistoryAdapter adapter;
    private List<RechargeHistory> listTransactionHistory;

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
        // Lấy cả lịch sử nạp và rút tiền từ database
        String username = DataLocalManager.getInstance().getPrefUsername();
        List<RechargeHistory> all = RechargeHistoryDatabase.getInstance(getContext()).getHistoryDAO().getAllHistoriesOfAccount(username);
        // Sắp xếp giảm dần theo thời gian giao dịch
        Collections.sort(all, new Comparator<RechargeHistory>() {
            @Override
            public int compare(RechargeHistory o1, RechargeHistory o2) {
                return o2.getTransactionTime().compareTo(o1.getTransactionTime());
            }
        });
        listTransactionHistory = all;
        adapter.setData(listTransactionHistory);
    }
}

package com.example.prm392_team1_spaapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.adapter.PromotionAdapter;
import com.example.prm392_team1_spaapp.adapter.my_interface.OnItemClickListener;
import com.example.prm392_team1_spaapp.model.Promotion;
import com.example.prm392_team1_spaapp.model.PromotionDatabase;
import com.example.prm392_team1_spaapp.model.Service;

import java.util.List;

public class PromotionFragment extends Fragment {
    private RecyclerView rcvPromotion;
    private List<Promotion> mPromotionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion,container,false);
        Log.d("TAG", "Promotion: ");
        mPromotionList = PromotionDatabase.getInstance(getContext()).getPromotionDAO().getAllPromotion();

        rcvPromotion = view.findViewById(R.id.rcv_promotion);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rcvPromotion.setLayoutManager(layoutManager);
        PromotionAdapter promotionAdapter = new PromotionAdapter();
        promotionAdapter.setData(mPromotionList, new OnItemClickListener() {
            @Override
            public void onItemClick(Service selectedService) {

            }

            @Override
            public void onItemClick(Promotion selectedPromotion) {

            }
        });
        rcvPromotion.setAdapter(promotionAdapter);

        return view;
    }
}

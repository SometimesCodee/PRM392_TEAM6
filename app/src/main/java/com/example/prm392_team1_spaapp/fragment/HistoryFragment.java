package com.example.prm392_team1_spaapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.adapter.HistoryAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HistoryFragment extends Fragment  {

    private HistoryAdapter pagerAdapter;
    private ViewPager2 viewPager;

    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);
        viewPager=view.findViewById(R.id.view_pager2);
        pagerAdapter= new HistoryAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        tabLayout= view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) ->{
                    if(position==0) {
                        tab.setText("Lịch sử nạp tiền");
                    }
                    else if(position==1) {
                        tab.setText("Lịch sử đặt lịch");
                    }
                }
        ).attach();
        return view;
    }

}
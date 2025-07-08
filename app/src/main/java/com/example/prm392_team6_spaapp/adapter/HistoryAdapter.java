package com.example.prm392_team6_spaapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prm392_team6_spaapp.fragment.PhoneCateHistoryFragment;
import com.example.prm392_team6_spaapp.fragment.RechargeHistoryFragment;
import com.example.prm392_team6_spaapp.fragment.WithdrawHistoryFragment;


public class HistoryAdapter extends FragmentStateAdapter {


    public HistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HistoryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public HistoryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new com.example.prm392_team6_spaapp.fragment.TransactionHistoryFragment();
            case 1:
                return new PhoneCateHistoryFragment();
            default:
                return new com.example.prm392_team6_spaapp.fragment.TransactionHistoryFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }


}

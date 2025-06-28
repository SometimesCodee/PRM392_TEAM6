package com.example.prm392_team6_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;

public class RechargeWithdrawActivity extends AppCompatActivity {
    LinearLayout layout_naptien;
    LinearLayout layout_ruttien;
    ImageView imv3;
    TextView tvMoney;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_withdraw);
        layout_naptien = findViewById(R.id.layout_naptien);
        layout_ruttien = findViewById(R.id.layout_ruttien);
        imv3 = findViewById(R.id.backmain);
        tvMoney = findViewById(R.id.tv_money);

        float money = DataLocalManager.getInstance().getPrefMoney();
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedMoney = numberFormat.format(money);
        tvMoney.setText(formattedMoney + "đ");

        layout_naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeWithdrawActivity.this, RechargeDetailActivity.class);
                startActivity(intent);
            }
        });

        layout_ruttien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeWithdrawActivity.this, WithdrawDetailActivity.class);
                startActivity(intent);
            }
        });

        imv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeWithdrawActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
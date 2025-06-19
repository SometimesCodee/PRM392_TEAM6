package com.example.prm392_team1_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;

public class RechargeWithdrawActivity extends AppCompatActivity {
    ImageView imv1;
    ImageView imv2;
    ImageView imv3;
    TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_withdraw);
        imv1 = findViewById(R.id.imageView29);
        imv2 = findViewById(R.id.imageView31);
        imv3 = findViewById(R.id.backmain);
        tvMoney = findViewById(R.id.tv_money);

        tvMoney.setText(String.valueOf(DataLocalManager.getInstance().getPrefMoney()) +"đ");
        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeWithdrawActivity.this, RechargeDetailActivity.class);
                startActivity(intent);
            }
        });

        imv2.setOnClickListener(new View.OnClickListener() {
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
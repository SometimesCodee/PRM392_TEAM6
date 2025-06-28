package com.example.prm392_team6_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;

public class RechargeSuccessActivity extends AppCompatActivity {
    private TextView tvAmount, tvNewBalance, tvDateTime, tvTransactionId;
    private Button btnContinue, btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_success);

        initViews();
        setupData();
        setupClickListeners();
    }

    private void initViews() {
        tvAmount = findViewById(R.id.tv_amount);
        tvNewBalance = findViewById(R.id.tv_new_balance);
        tvDateTime = findViewById(R.id.tv_date_time);
        tvTransactionId = findViewById(R.id.tv_transaction_id);
        btnContinue = findViewById(R.id.btn_continue);
        btnBackHome = findViewById(R.id.btn_back_home);
    }

    private void setupData() {
        // Lấy số tiền nạp từ Intent
        String rechargeAmount = getIntent().getStringExtra("recharge_amount");
        
        // Format số tiền nạp
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvAmount.setText("+" + numberFormat.format(Float.parseFloat(rechargeAmount)) + "đ");

        // Hiển thị số dư mới
        float newBalance = DataLocalManager.getInstance().getPrefMoney();
        String formattedBalance = numberFormat.format(newBalance);
        tvNewBalance.setText(formattedBalance + "đ");

        // Hiển thị thời gian giao dịch
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());
        tvDateTime.setText(currentDateTime);

        // Tạo mã giao dịch giả lập
        String transactionId = "RC" + System.currentTimeMillis();
        tvTransactionId.setText(transactionId);
    }

    private void setupClickListeners() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RechargeSuccessActivity.this, RechargeWithdrawActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RechargeSuccessActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Ngăn không cho quay lại màn hình trước đó
        Intent intent = new Intent(RechargeSuccessActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
} 
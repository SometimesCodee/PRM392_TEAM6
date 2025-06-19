package com.example.prm392_team1_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.model.AccountDatabase;


public class WithdrawDetailActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    EditText edt1;
    ImageView imv2;
    TextView totalMoney;
    Button submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_detail);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        edt1 = findViewById(R.id.money_input);
        imv2 = findViewById(R.id.back2);
        totalMoney = findViewById(R.id.totalMoney);
        submit = findViewById(R.id.confirm_button2);

        totalMoney.setText(""+ DataLocalManager.getInstance().getPrefMoney() + "đ");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn1.getText());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn2.getText());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn3.getText());

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn4.getText());

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn5.getText());

            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText(btn6.getText());

            }
        });

        imv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WithdrawDetailActivity.this, RechargeWithdrawActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float subMoney = DataLocalManager.getInstance().getPrefMoney() - Float.parseFloat(edt1.getText().toString().trim());
                AccountDatabase.getInstance(getApplicationContext()).getAccountDAO().updateMoney(
                        DataLocalManager.getInstance().getPrefUsername(), subMoney
                );
                DataLocalManager.getInstance().setPrefMoney(subMoney);
                recreate();
            }
        });
    }
}
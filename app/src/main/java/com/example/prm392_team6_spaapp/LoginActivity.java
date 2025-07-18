package com.example.prm392_team6_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.Account;
import com.example.prm392_team6_spaapp.model.AccountDatabase;
import com.example.prm392_team6_spaapp.model.RechargeHistory;
import com.example.prm392_team6_spaapp.model.RechargeHistoryDatabase;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private TextView tv_register;
    private ImageView img_login;
    private List<RechargeHistory> mRechargeHistoryList;

    private List<Account> mAccountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        img_login = findViewById(R.id.img_login);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivities.class);
                startActivity(intent);
            }
        });
    }

    public void clickOnLogin(View view) {
        if (et_username.getText().toString() == "" || et_password.getText().toString() == ""){
            Toast.makeText(getApplicationContext(),"Vui lòng xin nhập đủ thông tin để đăng nhập",Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = AccountDatabase.getInstance(this).getAccountDAO().getAccount(
                et_username.getText().toString().trim(), et_password.getText().toString().trim()
        );
        Log.d("TAG", "clickOnLogin: " + account);

        if (account != null) {
            DataLocalManager.getInstance().setPrefUsername(account.getUsername());
            DataLocalManager.getInstance().setPrefMoney(account.getMoney());
            DataLocalManager.getInstance().setPrefPhone(account.getPhoneNumber());
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(getApplicationContext(),"Yay đăng nhập thành công *(^O^)*",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }
        Toast.makeText(getApplicationContext(),"Người dùng không tồn tại (-_-)",Toast.LENGTH_SHORT).show();
    }
}

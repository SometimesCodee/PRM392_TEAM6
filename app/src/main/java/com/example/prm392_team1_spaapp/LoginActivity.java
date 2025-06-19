package com.example.prm392_team1_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.model.Account;
import com.example.prm392_team1_spaapp.model.AccountDatabase;
import com.example.prm392_team1_spaapp.model.RechargeHistory;
import com.example.prm392_team1_spaapp.model.RechargeHistoryDatabase;

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

        initData();
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

    public void initData() {
        mRechargeHistoryList = RechargeHistoryDatabase.getInstance(this).getHistoryDAO().getAllHistory();
        mAccountList = AccountDatabase.getInstance(this).getAccountDAO().getAllAccount();

        if (mRechargeHistoryList.isEmpty()) {
            RechargeHistory rechargeHistory1 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 12000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory1);

            RechargeHistory rechargeHistory2 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 100000, 0, "Bạn đã nạp tiền thất bại");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory2);

            RechargeHistory rechargeHistory3 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 562000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory3);

            RechargeHistory rechargeHistory4 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 123000, 0, "Bạn đã nạp tiền thất bại");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory4);

            RechargeHistory rechargeHistory5 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 52300, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory5);

            RechargeHistory rechargeHistory6 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12", 33000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory6);
            mRechargeHistoryList = RechargeHistoryDatabase.getInstance(this).getHistoryDAO().getAllHistory();
        }
        Log.d("TAG", "RechargeHistory: " + mRechargeHistoryList.get(0).getDescription());

        if (mAccountList.isEmpty()) {
            Account account1 = new Account("tunm17421", "123456", "tunm17421@gmail.com", "0397888035", "Nguyen Manh Tu", "Male", "2001-04-17", "Đông Anh, Hà Nội", R.drawable.avatar_1, 4900000.0f);
            AccountDatabase.getInstance(this).getAccountDAO().addAccount(account1);

            Account account2 = new Account("user2", "password2", "user2@example.com", "1234567892", "User Two", "Female", "1995-02-02", "456 Elm St, Town", R.drawable.avatar_2, 1500000.0f);
            AccountDatabase.getInstance(this).getAccountDAO().addAccount(account2);

            Account account3 = new Account("user3", "password3", "user3@example.com", "1234567893", "User Three", "Male", "1985-03-03", "789 Oak St, Village", R.drawable.avatar_3, 800000.0f);
            AccountDatabase.getInstance(this).getAccountDAO().addAccount(account3);

            Account account4 = new Account("user4", "password4", "user4@example.com", "1234567894", "User Four", "Female", "1992-04-04", "101 Pine St, Suburb", R.drawable.avatar_4, 200000.0f);
            AccountDatabase.getInstance(this).getAccountDAO().addAccount(account4);

            mAccountList = AccountDatabase.getInstance(this).getAccountDAO().getAllAccount();
        }
        Log.d("TAG", "Account: "+ mAccountList.get(0).getUsername());
    }
}

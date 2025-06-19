package com.example.prm392_team1_spaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.fragment.AccountFragment;

public class AccountSettingActivity extends AppCompatActivity {
    private ImageView backImage;
    private LinearLayout logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        backImage = findViewById(R.id.back_id);
        logout = findViewById(R.id.layout_logout);
        backImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccountFragment.class);
            startActivity(intent);
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.getInstance().setPrefUsername("");
                DataLocalManager.getInstance().setPrefPhone("");
                DataLocalManager.getInstance().setPrefMoney(0);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                Toast.makeText(getApplicationContext(),"Bạn đã đăng xuất thành công",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


}

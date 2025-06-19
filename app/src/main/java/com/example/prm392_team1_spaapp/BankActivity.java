package com.example.prm392_team1_spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.prm392_team1_spaapp.fragment.AccountFragment;

public class BankActivity extends AppCompatActivity {
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking);

        backImage = findViewById(R.id.user_profile_back);

        backImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccountFragment.class);
            startActivity(intent);
        });
    }
}
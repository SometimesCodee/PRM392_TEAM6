package com.example.prm392_team1_spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.fragment.AccountFragment;
import com.example.prm392_team1_spaapp.model.Account;
import com.example.prm392_team1_spaapp.model.AccountDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    private CircleImageView civAvatar;
    private TextView tvUsername,tvPhone,tvFullName,tvGender,tvBirthday,tvEmail,tvAddress;
    private LinearLayout updateUserProfile;

    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        civAvatar = findViewById(R.id.common_account_header_avatar);
        tvUsername = findViewById(R.id.tv_username);
        tvPhone = findViewById(R.id.tv_phone);
        tvFullName = findViewById(R.id.tv_full_name);
        tvGender = findViewById(R.id.tv_gender);
        tvBirthday = findViewById(R.id.tv_birth_day);
        tvEmail = findViewById(R.id.tv_email);
        tvAddress = findViewById(R.id.tv_address);
        updateUserProfile = findViewById(R.id.layout_update_user_profile);
        backImage = findViewById(R.id.user_profile_back);

        Account account = AccountDatabase.getInstance(this).getAccountDAO().getAccount(
                DataLocalManager.getInstance().getPrefUsername()
        );

        civAvatar.setImageResource(account.getAvatar());
        tvUsername.setText(account.getUsername());
        tvPhone.setText(account.getPhoneNumber());
        tvFullName.setText(account.getFullName());
        tvGender.setText(account.getGender());
        tvBirthday.setText(account.getBirthDate());
        tvEmail.setText(account.getEmail());
        tvAddress.setText(account.getAddress());
        updateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserProfileActivity.this,"Chức năng hiện tại chưa khả dụng. Vui lòng chờ update. T from with luv <3!",Toast.LENGTH_SHORT).show();
            }
        });

        backImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccountFragment.class);
            startActivity(intent);
        });

    }
}
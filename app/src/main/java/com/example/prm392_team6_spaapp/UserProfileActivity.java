package com.example.prm392_team6_spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.fragment.AccountFragment;

import com.example.prm392_team6_spaapp.model.Account;
import com.example.prm392_team6_spaapp.model.AccountDatabase;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    private CircleImageView civAvatar;
    private TextView tvUsername,tvPhone,tvFullName,tvGender,tvBirthday,tvEmail,tvAddress;
    private LinearLayout updateUserProfile;
    private Account account;
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
                showUpdateDialog(account);
            }
        });

        backImage.setOnClickListener(v -> {
            finish();
        });

    }

    private void showUpdateDialog(Account account) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_user_profile, null);

        EditText edtFullName = dialogView.findViewById(R.id.edt_full_name);
        EditText edtPhone = dialogView.findViewById(R.id.edt_phone);
        EditText edtEmail = dialogView.findViewById(R.id.edt_email);
        EditText edtAddress = dialogView.findViewById(R.id.edt_address);

        // Đổ dữ liệu hiện tại vào EditText
        edtFullName.setText(account.getFullName());
        edtPhone.setText(account.getPhoneNumber());
        edtEmail.setText(account.getEmail());
        edtAddress.setText(account.getAddress());

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Cập nhật thông tin")
                .setView(dialogView)
                .setPositiveButton("Cập nhật", (dialogInterface, i) -> {
                    String fullName = edtFullName.getText().toString().trim();
                    String phone = edtPhone.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String address = edtAddress.getText().toString().trim();

                    account.setFullName(fullName);
                    account.setPhoneNumber(phone);
                    account.setEmail(email);
                    account.setAddress(address);

                    AccountDatabase.getInstance(this).getAccountDAO().updateAccount(account);

                    tvFullName.setText(fullName);
                    tvPhone.setText(phone);
                    tvEmail.setText(email);
                    tvAddress.setText(address);

                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

}
package com.example.prm392_team1_spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.model.Account;
import com.example.prm392_team1_spaapp.model.AccountDatabase;

public class RegisterActivities extends AppCompatActivity {
    private EditText etUsername, etAddress, etBirthdate, etEmail, etPassword, etRePassword, etPhone;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.edt_name);
        etAddress = findViewById(R.id.edt_address);
        etBirthdate = findViewById(R.id.edt_bdate);
        etEmail = findViewById(R.id.edt_email);
        etPassword = findViewById(R.id.edt_pass);
        etRePassword = findViewById(R.id.edt_re_pass);
        etPhone = findViewById(R.id.edt_phone);
        radioGroup = findViewById(R.id.radioGroupGender);
        btnRegister = findViewById(R.id.btn_register);


//        Toast.makeText(getApplicationContext(),"Hình như bạn bỏ trống ở đâu đó thì phải (õ_ó) ",Toast.LENGTH_SHORT);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username,address,birthday,email,password,rePassword,phone;
                username = etUsername.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                birthday = etBirthdate.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                rePassword = etRePassword.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                if (username.equals("") || address.equals("") || birthday.equals("") ||email.equals("") || password.equals("") || rePassword.equals("")  || phone.equals("")  || selectedRadioButtonId == -1){
                    Toast.makeText(getApplicationContext(),"Hình như bạn bỏ trống ở đâu đó thì phải (õ_ó) ",Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onClick: Case1" + selectedRadioButtonId + username);
                    return;
                } else if (!password.equals(rePassword)){
                    Toast.makeText(getApplicationContext(),"Hai password không giống nhau kìa (• ε •)!",Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onClick: Case2");
                    return;
                }
                radioButton = findViewById(selectedRadioButtonId);
                String gender = radioButton.getText().toString().trim();
                Account account = new Account(username,password,email,phone,gender,birthday,address);
                AccountDatabase.getInstance(getApplicationContext()).getAccountDAO().addAccount(account);
                Toast.makeText(getApplicationContext(),"Đăng ký thành công *(^O^)*",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
package com.example.prm392_team6_spaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.AccountDatabase;
import com.example.prm392_team6_spaapp.model.RechargeHistory;
import com.example.prm392_team6_spaapp.model.RechargeHistoryDatabase;


public class RechargeDetailActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    EditText edt1;
    ImageView imv1;
    TextView totalmoney;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_detail);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        edt1 = findViewById(R.id.money_input);
        imv1 = findViewById(R.id.back);
        totalmoney = findViewById(R.id.totalmoney);
        submit = findViewById(R.id.confirm_button);

        float money = DataLocalManager.getInstance().getPrefMoney();
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedMoney = numberFormat.format(money);
        totalmoney.setText(formattedMoney + "đ");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("50.000 đ");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("100.000 đ");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("200.000 đ");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("500.000 đ");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("1.000.000 đ");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("2.000.000 đ");
            }
        });

        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeDetailActivity.this, RechargeWithdrawActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rechargeAmount = edt1.getText().toString().trim();
                if (rechargeAmount.isEmpty()) {
                    Toast.makeText(RechargeDetailActivity.this, "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                try {
                    // Xử lý chuỗi số tiền - loại bỏ dấu phẩy, chấm và ký tự đặc biệt
                    String cleanAmount = cleanMoneyString(rechargeAmount);
                    float rechargeValue = Float.parseFloat(cleanAmount);
                    
                    if (rechargeValue <= 0) {
                        Toast.makeText(RechargeDetailActivity.this, "Số tiền phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    if (rechargeValue > 10000000) { // Giới hạn tối đa 10 triệu
                        Toast.makeText(RechargeDetailActivity.this, "Số tiền nạp tối đa là 10,000,000đ!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    float addMoney = rechargeValue + DataLocalManager.getInstance().getPrefMoney();
                    AccountDatabase.getInstance(getApplicationContext()).getAccountDAO().updateMoney(
                            DataLocalManager.getInstance().getPrefUsername(), addMoney
                    );
                    DataLocalManager.getInstance().setPrefMoney(addMoney);
                    
                    // Lưu giao dịch nạp tiền vào database
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());
                    RechargeHistory rechargeHistory = new RechargeHistory(
                            DataLocalManager.getInstance().getPrefUsername(),
                            "Nạp tiền",
                            currentDate,
                            rechargeValue,
                            1, // Thành công
                            "Bạn đã nạp tiền thành công"
                    );
                    RechargeHistoryDatabase.getInstance(getApplicationContext()).getHistoryDAO().addHistory(rechargeHistory);
                    
                    // Chuyển đến màn hình thành công
                    Intent intent = new Intent(RechargeDetailActivity.this, RechargeSuccessActivity.class);
                    intent.putExtra("recharge_amount", cleanAmount);
                    startActivity(intent);
                    finish();
                    
                } catch (NumberFormatException e) {
                    Toast.makeText(RechargeDetailActivity.this, "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Làm sạch chuỗi số tiền - loại bỏ dấu phẩy, chấm, ký tự đặc biệt và chữ
     */
    private String cleanMoneyString(String moneyString) {
        if (moneyString == null) return "0";
        
        // Loại bỏ tất cả ký tự không phải số
        String cleaned = moneyString.replaceAll("[^0-9]", "");
        
        // Nếu chuỗi rỗng sau khi làm sạch, trả về "0"
        if (cleaned.isEmpty()) {
            return "0";
        }
        
        return cleaned;
    }

}
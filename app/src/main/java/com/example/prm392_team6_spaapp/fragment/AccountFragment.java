package com.example.prm392_team6_spaapp.fragment;
import com.example.prm392_team6_spaapp.MapsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Locale;

import com.example.prm392_team6_spaapp.AccountSettingActivity;
import com.example.prm392_team6_spaapp.BankActivity;
import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.RechargeWithdrawActivity;
import com.example.prm392_team6_spaapp.UserProfileActivity;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.Account;
import com.example.prm392_team6_spaapp.model.AccountDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private TextView tvPhone;
    private TextView tvBalance;
    private RelativeLayout layoutEditUserProfile;
    private LinearLayout layoutSoDu,layoutUuDai,layoutNganHang,layoutQuanLyHoDon,layoutLienKetNganHang,
            layoutThietLapTaiKhoan,layoutThietLapThongBao,layoutTrungTamHoTro,layoutThongTinUngDung, layoutMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        civAvatar = view.findViewById(R.id.common_account_header_avatar);
        tvFullName = view.findViewById(R.id.common_account_header_name);
        tvPhone = view.findViewById(R.id.common_account_header_phone_number);
        tvBalance = view.findViewById(R.id.tv_balance);
        layoutEditUserProfile = view.findViewById(R.id.edit_user_profile);
        layoutSoDu = view.findViewById(R.id.layout_so_du);
        layoutNganHang = view.findViewById(R.id.layout_ngan_hang);
        layoutThietLapTaiKhoan = view.findViewById(R.id.layout_thiet_lap_tai_khoan);
        layoutMap = view.findViewById(R.id.layout_map);
        Account account = AccountDatabase.getInstance(getContext()).getAccountDAO().getAccount(
                DataLocalManager.getInstance().getPrefUsername()
        ) ;

        civAvatar.setImageResource(account.getAvatar());
        tvFullName.setText(account.getUsername());
        tvPhone.setText(account.getPhoneNumber());
        tvBalance.setText(String.format(Locale.getDefault(), "%.0fđ", account.getMoney()));

        layoutEditUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        layoutSoDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RechargeWithdrawActivity.class);
                startActivity(intent);
            }
        });

        layoutNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BankActivity.class);
                startActivity(intent);
            }
        });

        layoutThietLapTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(intent);
            }
        });
        layoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật số dư khi quay lại fragment
        if (tvBalance != null) {
            Account account = AccountDatabase.getInstance(getContext()).getAccountDAO().getAccount(
                    DataLocalManager.getInstance().getPrefUsername()
            );
            tvBalance.setText(String.format(Locale.getDefault(), "%.0fđ", account.getMoney()));
        }
    }
}

package com.example.prm392_team1_spaapp.fragment;

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

import com.example.prm392_team1_spaapp.AccountSettingActivity;
import com.example.prm392_team1_spaapp.BankActivity;
import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.UserProfileActivity;
import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.model.Account;
import com.example.prm392_team1_spaapp.model.AccountDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private TextView tvPhone;
    private RelativeLayout layoutEditUserProfile;
    private LinearLayout layoutSoDu,layoutUuDai,layoutNganHang,layoutQuanLyHoDon,layoutLienKetNganHang,
            layoutThietLapTaiKhoan,layoutThietLapThongBao,layoutTrungTamHoTro,layoutThongTinUngDung;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        civAvatar = view.findViewById(R.id.common_account_header_avatar);
        tvFullName = view.findViewById(R.id.common_account_header_name);
        tvPhone = view.findViewById(R.id.common_account_header_phone_number);
        layoutEditUserProfile = view.findViewById(R.id.edit_user_profile);
        layoutNganHang = view.findViewById(R.id.layout_ngan_hang);
        layoutThietLapTaiKhoan = view.findViewById(R.id.layout_thiet_lap_tai_khoan);

        Account account = AccountDatabase.getInstance(getContext()).getAccountDAO().getAccount(
                DataLocalManager.getInstance().getPrefUsername()
        ) ;

        civAvatar.setImageResource(account.getAvatar());
        tvFullName.setText(account.getUsername());
        tvPhone.setText(account.getPhoneNumber());

        layoutEditUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
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
        return view;
    }
}

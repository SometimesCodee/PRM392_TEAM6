package com.example.prm392_team1_spaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.RechargeWithdrawActivity;
import com.example.prm392_team1_spaapp.ServiceDetailActivity;
import com.example.prm392_team1_spaapp.adapter.ServiceAdapter;
import com.example.prm392_team1_spaapp.adapter.my_interface.OnItemClickListener;
import com.example.prm392_team1_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team1_spaapp.model.Promotion;
import com.example.prm392_team1_spaapp.model.Service;
import com.example.prm392_team1_spaapp.model.ServiceDatabase;

import java.util.List;

public class HomeFragment extends Fragment {
    private ImageView img_recharge_withdraw;
    private ImageView img_see_money;
    private TextView tv_money;
    private RecyclerView rcvService;
    private List<Service> mServiceList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mServiceList = ServiceDatabase.getInstance(getContext()).getServiceDAO().getAllService();
        Log.d("TAG", "onCreate: "+mServiceList.get(1).getServiceName());

        rcvService = view.findViewById(R.id.rcv_service);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4); // 2 cột trong lưới
        rcvService.setLayoutManager(layoutManager);
        ServiceAdapter serviceAdapter = new ServiceAdapter(); // Thay yourDataList bằng danh sách dữ liệu của bạn
        serviceAdapter.setData(mServiceList, new OnItemClickListener() {
            @Override
            public void onItemClick(Service selectedService) {
                Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_service", selectedService);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemClick(Promotion selectedPromotion) {
                return;
            }
        });
        rcvService.setAdapter(serviceAdapter);

        img_recharge_withdraw = view.findViewById(R.id.recharge_withdraw);
        img_see_money = view.findViewById(R.id.img_see_money);
        tv_money = view.findViewById(R.id.tv_money);

        img_recharge_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeWithdrawActivity.class);
                startActivity(intent);
            }
        });

        img_see_money.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Xử lý sự kiện khi nút được nhấn
                        tv_money.setText(String.valueOf(DataLocalManager.getInstance().getPrefMoney()));
                        return true; // Trả về true để xác nhận rằng sự kiện đã được xử lý

                    case MotionEvent.ACTION_UP:
                        // Xử lý sự kiện khi nút được thả ra
                        tv_money.setText("Xem số dư");
                        return true; // Trả về true để xác nhận rằng sự kiện đã được xử lý

                    default:
                        return false;
                }
            }
        });
        return view;
    }

}
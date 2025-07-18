package com.example.prm392_team6_spaapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.prm392_team6_spaapp.databinding.ActivityMainBinding;
import com.example.prm392_team6_spaapp.model.Promotion;
import com.example.prm392_team6_spaapp.model.PromotionDatabase;
import com.example.prm392_team6_spaapp.model.RechargeHistory;
import com.example.prm392_team6_spaapp.model.RechargeHistoryDatabase;
import android.content.Intent;
import com.example.prm392_team6_spaapp.model.Service;
import com.example.prm392_team6_spaapp.model.ServiceDatabase;
import com.example.prm392_team6_spaapp.fragment.AccountFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private List<Promotion> mPromotionList;
    private List<RechargeHistory> mRechargeHistoryList;
    private List<Service> mServiceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initNavigation();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh UI khi quay lại MainActivity
        refreshAccountFragment();
        
        // Kiểm tra xem có cần mở tab History không
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("open_history", false)) {
            // Mở tab History
            binding.bottomNavigation.setSelectedItemId(R.id.menu_history);
            // Xóa flag để tránh mở lại
            intent.removeExtra("open_history");
        }
    }
    
    private void refreshAccountFragment() {
        // Tìm và refresh AccountFragment nếu đang hiển thị
        try {
            AccountFragment accountFragment = (AccountFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_container);
            if (accountFragment != null && accountFragment.isVisible()) {
                accountFragment.onResume();
            }
        } catch (Exception e) {
            // Ignore if fragment not found
        }
    }

    private void initNavigation(){
        navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        navController =  navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }


    public void initData(){
        mServiceList = ServiceDatabase.getInstance(this).getServiceDAO().getAllService();
        if (mServiceList.isEmpty()) {
            Service service1 = new Service("Chăm sóc da", 1500000, "90 phút", "Dịch vụ chăm sóc da chuyên nghiệp với quy trình làm sạch, tẩy tế bào chết, và cung cấp dưỡng chất để da trở nên mềm mịn và tươi trẻ.", R.drawable.cham_soc_da);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service1);

            Service service2 = new Service("Trị nám", 2000000, "120 phút", "Dịch vụ trị nám sử dụng công nghệ hiện đại và sản phẩm chất lượng cao để giảm nám da và đều màu da.", R.drawable.tri_nam);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service2);

            Service service3 = new Service("Triệt Lông", 1200000, "60 phút", "Triệt lông vĩnh viễn bằng công nghệ IPL, giúp loại bỏ lông không mong muốn trên cơ thể.", R.drawable.triet_long);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service3);

            Service service4 = new Service("Trẻ hóa da", 1800000, "75 phút", "Dịch vụ trẻ hóa da sử dụng các phương pháp tự nhiên và sản phẩm hữu cơ để giảm nếp nhăn và tăng độ đàn hồi của da.", R.drawable.tre_hoa_da);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service4);

            Service service5 = new Service("Massage", 900000, "45 phút", "Mát-xa thư giãn với áp lực vừa phải để giảm căng thẳng và tạo cảm giác thư thái.", R.drawable.massage);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service5);

            Service service6 = new Service("Tắm trắng", 2500000, "120 phút", "Tắm trắng da bằng sản phẩm chuyên dụng và kỹ thuật tiên tiến để làm sáng da và đều màu.", R.drawable.tam_trang);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service6);

            Service service7 = new Service("Trị mụn", 1100000, "60 phút", "Dịch vụ trị mụn với quy trình làm sạch da, lấy sạch mụn và điều trị da để ngăn mụn quay trở lại.", R.drawable.tri_mun);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service7);

            Service service8 = new Service("Cấy collagen", 2800000, "90 phút", "Cấy collagen giúp làm mịn và săn chắc da, giảm nếp nhăn và làm da trở nên tươi trẻ hơn.", R.drawable.collagen);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service8);

            Service service9 = new Service("Giảm béo", 1600000, "75 phút", "Dịch vụ giảm béo bằng cách sử dụng kỹ thuật giảm mỡ và đánh tan mỡ thừa trên cơ thể.", R.drawable.giam_beo);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service9);

            Service service10 = new Service("Thẩm mĩ", 2100000, "120 phút", "Dịch vụ thẩm mĩ với nhiều tùy chọn để cải thiện vẻ ngoại hình và tăng tự tin.", R.drawable.phun_xam);
            ServiceDatabase.getInstance(this).getServiceDAO().addService(service10);
            mServiceList = ServiceDatabase.getInstance(this).getServiceDAO().getAllService();
        }

        Log.d("TAG", "Service: " + mServiceList.get(0).getServiceName());

        mRechargeHistoryList = RechargeHistoryDatabase.getInstance(this).getHistoryDAO().getAllHistory();
        if (mRechargeHistoryList.isEmpty()) {
            RechargeHistory rechargeHistory1 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 11:00:00", 12000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory1);

            RechargeHistory rechargeHistory2 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 09:30:00", 100000, 0, "Bạn đã nạp tiền thất bại");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory2);

            RechargeHistory rechargeHistory3 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 16:45:00", 562000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory3);

            RechargeHistory rechargeHistory4 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 10:15:00", 123000, 0, "Bạn đã nạp tiền thất bại");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory4);

            RechargeHistory rechargeHistory5 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 14:20:00", 52300, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory5);

            RechargeHistory rechargeHistory6 = new RechargeHistory( "tunm17421", "Nạp tiền", "2023-12-12 15:30:00", 33000, 1, "Bạn đã nạp tiền thành công");
            RechargeHistoryDatabase.getInstance(this).getHistoryDAO().addHistory(rechargeHistory6);
            mRechargeHistoryList = RechargeHistoryDatabase.getInstance(this).getHistoryDAO().getAllHistory();
        }

        Log.d("TAG", "RechargeHistory: " + mRechargeHistoryList.get(0).getDescription());
    }
}
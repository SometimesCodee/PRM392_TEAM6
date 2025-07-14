package com.example.prm392_team6_spaapp.model;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Database(entities = {RechargeHistory.class}, version = 1)
public abstract class RechargeHistoryDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "RechargeHistory.db";
    private static RechargeHistoryDatabase instance;

    public static synchronized  RechargeHistoryDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),RechargeHistoryDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Thêm dữ liệu mẫu khi tạo database
                            new Thread(() -> {
                                RechargeHistoryDAO dao = instance.getHistoryDAO();
                                String username = DataLocalManager.getInstance().getPrefUsername();
                                if (username == null || username.isEmpty()) {
                                    username = "default_user";
                                }
                                
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                
                                // Dữ liệu mẫu cho nạp tiền (sắp xếp theo thời gian)
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-26 15:30:00", 33000, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-16 14:20:00", 52300, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-15 10:15:00", 123000, 0, "Bạn đã nạp tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-14 16:45:00", 562000, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-14 09:30:00", 100000, 0, "Bạn đã nạp tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-01-12 11:00:00", 12000, 1, "Bạn đã nạp tiền thành công"));
                                
                                // Dữ liệu mẫu cho rút tiền (sắp xếp theo thời gian)
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-26 16:45:00", 80000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-16 13:20:00", 150000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-15 14:30:00", 75000, 0, "Bạn đã rút tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-14 17:15:00", 200000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-14 10:45:00", 100000, 0, "Bạn đã rút tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-01-12 12:30:00", 50000, 1, "Bạn đã rút tiền thành công"));
                            }).start();
                        }
                    })
                    .build();
        }
        return instance;
    }
    public abstract RechargeHistoryDAO getHistoryDAO();
}

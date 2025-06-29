package com.example.prm392_team6_spaapp.model;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;

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
                                
                                // Dữ liệu mẫu cho nạp tiền
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-12", 12000, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-14", 100000, 0, "Bạn đã nạp tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-14", 562000, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-15", 123000, 0, "Bạn đã nạp tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-16", 52300, 1, "Bạn đã nạp tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Nạp tiền", "2025-6-26", 33000, 1, "Bạn đã nạp tiền thành công"));
                                
                                // Dữ liệu mẫu cho rút tiền
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-12", 50000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-14", 100000, 0, "Bạn đã rút tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-14", 200000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-15", 75000, 0, "Bạn đã rút tiền thất bại"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-16", 150000, 1, "Bạn đã rút tiền thành công"));
                                dao.addHistory(new RechargeHistory(username, "Rút tiền", "2025-6-26", 80000, 1, "Bạn đã rút tiền thành công"));
                            }).start();
                        }
                    })
                    .build();
        }
        return instance;
    }
    public abstract RechargeHistoryDAO getHistoryDAO();
}

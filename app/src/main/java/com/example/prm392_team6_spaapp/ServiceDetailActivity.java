package com.example.prm392_team6_spaapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team6_spaapp.adapter.FeedbackAdapter;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.Feedback;
import com.example.prm392_team6_spaapp.model.FeedbackDatabase;
import com.example.prm392_team6_spaapp.model.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceDetailActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private TextView tvServiceName;
    private TextView tvServicePrice;
    private TextView tvServiceDuration;
    private TextView tvServiceDescription;
    private ImageView imgService;
    private Button btnBookService;
    private EditText editTextComment;
    private RatingBar ratingBar;
    private Button btnSubmitReview;
    private TextView tvFeedbackSummary;
    private TextView tvRatingOverview;
    private RecyclerView recyclerViewFeedbacks;
    private FeedbackAdapter feedbackAdapter;

    private ImageView backImage;
    private Service currentService;

    public  String channel_id ="Spa App";

    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        vw = (VideoView)findViewById(R.id.vidvw);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        // video name should be in lower case alphabet.
        videolist.add(R.raw.chamsocda);
        videolist.add(R.raw.tamtrang);
        videolist.add(R.raw.trehoada);
        videolist.add(R.raw.caycollagen);
        videolist.add(R.raw.masage);
        videolist.add(R.raw.trimun);
        videolist.add(R.raw.trinam);
        videolist.add(R.raw.trietlongtainha);
        videolist.add(R.raw.giamcan);
        setVideo(videolist.get(0));

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        tvServiceName = findViewById(R.id.tv_service_name);
        tvServicePrice = findViewById(R.id.tv_service_price);
        tvServiceDuration = findViewById(R.id.tv_service_duration);
        tvServiceDescription = findViewById(R.id.tv_service_description);
        imgService = findViewById(R.id.img_service);
        btnBookService = findViewById(R.id.btn_book_service);
        editTextComment = findViewById(R.id.commentEditText);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitReview = findViewById(R.id.submitReviewButton);
        tvFeedbackSummary = findViewById(R.id.tv_feedback_summary);
        tvRatingOverview = findViewById(R.id.tv_rating_overview);
        recyclerViewFeedbacks = findViewById(R.id.recyclerViewFeedbacks);
        backImage = findViewById(R.id.user_profile_back);
        
        // Setup RecyclerView
        setupRecyclerView();

        currentService = (Service) bundle.get("object_service");
        Service service = currentService;

        tvServiceName.setText(service.getServiceName());
        tvServicePrice.setText(String.valueOf(service.getPrice())+"đ");
        tvServiceDuration.setText(service.getDuration());
        tvServiceDescription.setText(service.getDescription());
        imgService.setImageResource(service.getImg());

        btnBookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra đăng nhập
                String username = DataLocalManager.getInstance().getPrefUsername();
                if (username == null || username.isEmpty()) {
                    Toast.makeText(ServiceDetailActivity.this, "Vui lòng đăng nhập để đặt lịch!", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Mở BookingActivity với thông tin dịch vụ
                Intent intent = new Intent(ServiceDetailActivity.this, BookingActivity.class);
                intent.putExtra("service_id", currentService.getServiceId());
                intent.putExtra("service_name", currentService.getServiceName());
                intent.putExtra("service_price", currentService.getPrice());
                intent.putExtra("service_duration", currentService.getDuration());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFeedback();
            }
        });

        backImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // Load existing feedbacks for this service
        loadExistingFeedbacks();
    }
    
    private void setupRecyclerView() {
        try {
            feedbackAdapter = new FeedbackAdapter(this, new ArrayList<>());
            recyclerViewFeedbacks.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewFeedbacks.setAdapter(feedbackAdapter);
            android.util.Log.d("SetupRecyclerView", "RecyclerView setup completed successfully");
        } catch (Exception e) {
            android.util.Log.e("SetupRecyclerView", "Error setting up RecyclerView: " + e.getMessage(), e);
        }
    }

    private void loadExistingFeedbacks() {
        if (currentService == null) {
            android.util.Log.e("LoadFeedbacks", "currentService is null");
            return;
        }
        
        try {
            // Thêm dữ liệu mẫu nếu chưa có feedback nào
            addSampleFeedbacksIfEmpty();
            
            android.util.Log.d("LoadFeedbacks", "Current service ID: " + currentService.getServiceId());
            
            List<Feedback> feedbacks = FeedbackDatabase.getInstance(this)
                    .getFeedbackDAO()
                    .getFeedbacksByServiceId(currentService.getServiceId());
            
            android.util.Log.d("LoadFeedbacks", "Service ID: " + currentService.getServiceId() + ", Found feedbacks: " + feedbacks.size());
            
            // Cập nhật RecyclerView
            if (feedbackAdapter != null) {
                feedbackAdapter.updateFeedbackList(feedbacks);
                android.util.Log.d("LoadFeedbacks", "Updated adapter with " + feedbacks.size() + " feedbacks");
            } else {
                android.util.Log.e("LoadFeedbacks", "feedbackAdapter is null");
            }
            
            // Hiển thị thông tin tổng quan
            if (tvRatingOverview != null) {
                if (!feedbacks.isEmpty()) {
                    Float avgRating = FeedbackDatabase.getInstance(this)
                            .getFeedbackDAO()
                            .getAverageRatingByServiceId(currentService.getServiceId());
                    int feedbackCount = feedbacks.size();
                    
                    String ratingInfo = String.format(Locale.getDefault(), 
                        "Đánh giá trung bình: %.1f/5 ★ (%d đánh giá)", 
                        avgRating != null ? avgRating : 0.0f, feedbackCount);
                    
                    tvRatingOverview.setText(ratingInfo);
                    android.util.Log.d("LoadFeedbacks", "Rating info: " + ratingInfo);
                } else {
                    tvRatingOverview.setText("Chưa có đánh giá nào cho dịch vụ này. Hãy là người đầu tiên đánh giá!");
                    android.util.Log.d("LoadFeedbacks", "No feedbacks found for service ID: " + currentService.getServiceId());
                }
            } else {
                android.util.Log.e("LoadFeedbacks", "tvRatingOverview is null");
            }
        } catch (Exception e) {
            android.util.Log.e("LoadFeedbacks", "Error loading feedbacks: " + e.getMessage(), e);
            // Đặt text mặc định nếu có lỗi
            if (tvRatingOverview != null) {
                tvRatingOverview.setText("Không thể tải đánh giá: " + e.getMessage());
            }
        }
    }
    
    private void addSampleFeedbacksIfEmpty() {
        try {
            List<Feedback> existingFeedbacks = FeedbackDatabase.getInstance(this)
                    .getFeedbackDAO()
                    .getAllFeedbacks();
            
            if (existingFeedbacks.isEmpty()) {
                android.util.Log.d("SampleData", "Adding sample feedbacks...");
                
                // Thêm feedback mẫu cho nhiều dịch vụ
                Feedback feedback1 = new Feedback(1, "Nguyễn Văn A", 5.0f, 
                    "Dịch vụ chăm sóc da rất tốt, nhân viên phục vụ chuyên nghiệp!", "15/12/2024 10:30");
                Feedback feedback2 = new Feedback(1, "Trần Thị B", 4.5f, 
                    "Chất lượng dịch vụ tốt, giá cả hợp lý", "14/12/2024 14:20");
                Feedback feedback3 = new Feedback(1, "Lê Văn C", 5.0f, 
                    "Rất hài lòng với dịch vụ, sẽ quay lại!", "13/12/2024 16:45");
                
                // Feedback cho dịch vụ khác
                Feedback feedback4 = new Feedback(2, "Phạm Thị D", 4.0f, 
                    "Dịch vụ trị nám hiệu quả, da sáng hơn rõ rệt", "12/12/2024 09:15");
                Feedback feedback5 = new Feedback(2, "Hoàng Văn E", 5.0f, 
                    "Kết quả vượt mong đợi, rất hài lòng!", "11/12/2024 15:30");
                
                Feedback feedback6 = new Feedback(3, "Vũ Thị F", 4.5f, 
                    "Triệt lông hiệu quả, không đau", "10/12/2024 11:20");
                Feedback feedback7 = new Feedback(5, "Đỗ Văn G", 5.0f, 
                    "Massage thư giãn tuyệt vời, giảm stress hiệu quả", "09/12/2024 16:45");
                
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback1);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback2);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback3);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback4);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback5);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback6);
                FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback7);
                
                android.util.Log.d("SampleData", "Added 7 sample feedbacks for different services");
            }
        } catch (Exception e) {
            android.util.Log.e("SampleData", "Error adding sample feedbacks: " + e.getMessage(), e);
        }
    }

    private void setVideo(int id) {
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
    }


    public void onVoteNotification (View view) {
        submitFeedback();
    }
    
    private void submitFeedback() {
        // Validation
        float rating = ratingBar.getRating();
        String comment = editTextComment.getText().toString().trim();
        String username = DataLocalManager.getInstance().getPrefUsername();
        
        if (rating == 0) {
            Toast.makeText(this, "Vui lòng chọn số sao đánh giá!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (comment.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập bình luận!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập để đánh giá!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (currentService == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin dịch vụ!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Tạo feedback mới
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        
        Feedback feedback = new Feedback(
            currentService.getServiceId(),
            username,
            rating,
            comment,
            currentDate
        );
        
        // Lưu vào database
        boolean saveSuccess = false;
        try {
            FeedbackDatabase.getInstance(this).getFeedbackDAO().addFeedback(feedback);
            saveSuccess = true;
        } catch (Exception e) {
            // Log chi tiết lỗi để debug
            android.util.Log.e("FeedbackSave", "Error saving feedback: " + e.getMessage(), e);
            Toast.makeText(this, "Lỗi khi lưu đánh giá: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Nếu lưu thành công, thực hiện các thao tác UI
        if (saveSuccess) {
            // Reset form
            ratingBar.setRating(0);
            editTextComment.setText("");
            
            // Hiển thị thông báo thành công
            Toast.makeText(this, "Cảm ơn bạn đã đánh giá dịch vụ!", Toast.LENGTH_LONG).show();
            
            // Reload feedbacks với try-catch riêng để không ảnh hưởng đến việc lưu
            try {
                loadExistingFeedbacks();
            } catch (Exception e) {
                android.util.Log.e("FeedbackReload", "Error reloading feedbacks: " + e.getMessage(), e);
                // Không hiển thị lỗi cho user vì feedback đã được lưu thành công
            }
            
            // Hiển thị notification
            try {
                showSuccessNotification();
            } catch (Exception e) {
                android.util.Log.e("FeedbackNotification", "Error showing notification: " + e.getMessage(), e);
                // Không hiển thị lỗi cho user vì feedback đã được lưu thành công
            }
        }
    }
    
    private void showSuccessNotification() {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities
                (this, 1000, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    (channel_id, "App Spa", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channel_id);
        }else{
            builder =  new NotificationCompat.Builder(this);
        }
        Notification notification = builder.setContentTitle("Đánh giá thành công!")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText("Cảm ơn bạn đã đánh giá dịch vụ của chúng tôi!")
                .setContentIntent(pendingIntent).build();
        manager.notify(1, notification);
    }

    public void onPushNotification(View view) {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities
                (this, 1000, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    (channel_id, "App Spa", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channel_id);
        }else{
            builder =  new NotificationCompat.Builder(this);
        }
        Notification notification = builder.setContentTitle("Bạn đã đặt lịch thành công !!!")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText("Spa App Notification !!!")
                .setContentIntent(pendingIntent).build();
        manager.notify(1, notification);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();
    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }
            else {
                ++currvideo;
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo));
            }
        }
    }
}

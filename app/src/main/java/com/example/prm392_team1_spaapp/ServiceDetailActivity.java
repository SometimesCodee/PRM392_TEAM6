package com.example.prm392_team1_spaapp;

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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.prm392_team1_spaapp.model.Service;

import java.util.ArrayList;

public class ServiceDetailActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private TextView tvServiceName;
    private TextView tvServicePrice;
    private TextView tvServiceDuration;
    private TextView tvServiceDescription;
    private ImageView imgService;
    private Button btnBookService;

    private ImageView backImage;

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
        backImage = findViewById(R.id.user_profile_back);

        Service service = (Service) bundle.get("object_service");

        tvServiceName.setText(service.getServiceName());
        tvServicePrice.setText(String.valueOf(service.getPrice())+"đ");
        tvServiceDuration.setText(service.getDuration());
        tvServiceDescription.setText(service.getDescription());
        imgService.setImageResource(service.getImg());

        btnBookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        backImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

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
        Notification notification = builder.setContentTitle("Bạn đã đánh giá thành công !!!")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText("Spa App Notification !!!")
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

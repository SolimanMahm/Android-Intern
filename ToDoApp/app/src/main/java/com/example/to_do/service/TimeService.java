package com.example.to_do.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.to_do.R;
import com.example.to_do.activities.SplashActivity;
import com.example.to_do.database.MyDatabase;
import com.example.to_do.models.TaskModel;
import com.example.to_do.utils.DateUtils;

import java.util.Calendar;

public class TimeService extends Service {

    private final String CHANNEL_ID = "ToDoChannel";
    private Handler handler;
    private Runnable timeRunnable;
    private int lastCheckedMinute = -1, currentMinute;
    private String currentTime = "", currentDate;
    private Calendar calendar;
    private MyDatabase database;

    public TimeService() {
        calendar = Calendar.getInstance();
        currentDate = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        database = new MyDatabase(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //stopForeground(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timeRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(-1, getNotificationObject());
        handler = new Handler();
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                currentMinute = getCurrentMinute();
                if (currentMinute != lastCheckedMinute) {
                    currentTime = getCurrentTime();
                    lastCheckedMinute = currentMinute;
                    TaskModel taskModel = database.searchTask(currentDate, currentTime);
                    if (taskModel != null) {
                        getNotificationObject(taskModel.getId(), taskModel.getTitle(), taskModel.getNote());
                    }
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timeRunnable);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getCurrentTime() {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return DateUtils.convertStringToTime12(String.format("%02d:%02d", hour, minute));
    }

    private int getCurrentMinute() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    private void getNotificationObject(int id, String title, String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "ToDo", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getBaseContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        managerCompat.notify(id, builder.build());
    }

    private Notification getNotificationObject() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID + "Admin", "ToDo", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("Welcome Message");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getBaseContext(), CHANNEL_ID + "Admin");
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("Welcome in ToDo!")
                .setPriority(NotificationCompat.PRIORITY_MIN);

        return builder.build();
    }

}
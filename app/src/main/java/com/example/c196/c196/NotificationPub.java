package com.example.c196.c196;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationPub extends BroadcastReceiver {

    public static String notification_id = "notification_id";
    public static String notification1 = "notification1";
    String channel_id="test";
    String channel_name = "notification channel";
    String channel_description = "notificaiton channel description";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context,channel_id);

        String notificationTitle4 = intent.getStringExtra(AddAssessment.notificationTitle4);
        String notificationID4 = intent.getStringExtra(AddAssessment.notificationID4);
        String notificationMSG4 = intent.getStringExtra(AddAssessment.notificationMSG4);

        String notificationTitle3 = intent.getStringExtra(EditAssessment.notificationTitle3);
        String notificationID3 = intent.getStringExtra(EditAssessment.notificationID3);
        String notificationMSG3 = intent.getStringExtra(EditAssessment.notificationMSG3);

        String notificationTitle2 = intent.getStringExtra(AddCourse.notificationTitle2);
        String notificationID2 = intent.getStringExtra(AddCourse.notificationID2);
        String notificationMSG2 = intent.getStringExtra(AddCourse.notificationMSG2);

        String notificationTitle1 = intent.getStringExtra(AddCourse.notificationTitle1);
        String notificationID1 = intent.getStringExtra(AddCourse.notificationID1);
        String notificationMSG1 = intent.getStringExtra(AddCourse.notificationMSG1);

        String notificationTitle5 = intent.getStringExtra(EditCourse.notificationTitle5);
        String notificationID5 = intent.getStringExtra(EditCourse.notificationID5);
        String notificationMSG5 = intent.getStringExtra(EditCourse.notificationMSG5);

        String notificationTitle6 = intent.getStringExtra(EditCourse.notificationTitle6);
        String notificationID6 = intent.getStringExtra(EditCourse.notificationID6);
        String notificationMSG6 = intent.getStringExtra(EditCourse.notificationMSG6);


        if (notificationMSG1 != null && notificationMSG2 != null){
            Notification n= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG2)
                    .setContentTitle(notificationTitle2).build();

            Notification n1= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG1)
                    .setContentTitle(notificationTitle1).build();

            NotificationManager notificationManager1=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager1.notify(Integer.parseInt(notificationID1),n1);

            NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID2),n);

        } else if (notificationMSG1 != null && notificationMSG2 == null) {
            Notification n1= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG1)
                    .setContentTitle(notificationTitle1).build();

            NotificationManager notificationManager1=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager1.notify(Integer.parseInt(notificationID1),n1);
        } else if (notificationMSG1 == null && notificationMSG2 != null) {
            Notification n= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG2)
                    .setContentTitle(notificationTitle2).build();

            NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID2),n);
        } else if (notificationMSG3 != null){
            Notification n= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG3)
                    .setContentTitle(notificationTitle3).build();

            NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID3),n);

        } else if (notificationMSG4 != null) {
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG4)
                    .setContentTitle(notificationTitle4).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID4), n);

        }      if (notificationMSG5 != null && notificationMSG6 != null){
            Notification n= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG6)
                    .setContentTitle(notificationTitle6).build();

            Notification n1= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG5)
                    .setContentTitle(notificationTitle5).build();

            NotificationManager notificationManager1=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager1.notify(Integer.parseInt(notificationID5),n1);

            NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID6),n);

        } else if (notificationMSG5 != null && notificationMSG6 == null) {
            Notification n1= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG5)
                    .setContentTitle(notificationTitle5).build();

            NotificationManager notificationManager1=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager1.notify(Integer.parseInt(notificationID5),n1);
        } else if (notificationMSG5 == null && notificationMSG6 != null) {
            Notification n= new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(notificationMSG6)
                    .setContentTitle(notificationTitle6).build();

            NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(notificationID6),n);
        }


        //Put a notification her aka Vogella Tutorial

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification channel";
            String description = "notificaiton channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

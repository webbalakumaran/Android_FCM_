package com.webbala.experiment;

import android.app.Notification;
import android.app.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMessageReceiver
        extends FirebaseMessagingService {
    String TAG = "DEBUG_FCM_SERVICE";
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Map<String, Object> tokenData = new HashMap<>();
        Log.d(TAG, "New Token => "+tokenData);
        tokenData.put("token", s);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("DeviceTokens").document().set(tokenData);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATIONS";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "MyNotification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());
    }


//
//    // Override onMessageReceived() method to extract the
//    // title and
//    // body from the message passed in FCM
//    @Override
//    public void
//    onMessageReceived(RemoteMessage remoteMessage) {
//        // First case when notifications are received via
//        // data event
//        // Here, 'title' and 'message' are the assumed names
//        // of JSON
//        // attributes. Since here we do not have any data
//        // payload, This section is commented out. It is
//        // here only for reference purposes.
//		/*if(remoteMessage.getData().size()>0){
//			showNotification(remoteMessage.getData().get("title"),
//						remoteMessage.getData().get("message"));
//		}*/
//
//        // Second case when notification payload is
//        // received.
//        if (remoteMessage.getNotification() != null) {
//            // Since the notification is received directly from
//            // FCM, the title and the body can be fetched
//            // directly as below.
//            showNotification(
//                    remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody());
//        }
//    }
//
//    @Override
//    public void onNewToken(@NonNull String token) {
//        super.onNewToken(token);
//        Log.d("FCM_TOKEN =>", token);
//    }
//
//    // Method to get the custom Design for the display of
//    // notification.
//    private RemoteViews getCustomDesign(String title,
//                                        String message) {
//        RemoteViews remoteViews = new RemoteViews(
//                getApplicationContext().getPackageName(),
//                R.layout.notification);
//        remoteViews.setTextViewText(R.id.title, title);
//        remoteViews.setTextViewText(R.id.message, message);
//        remoteViews.setImageViewResource(R.id.icon,
//                R.drawable.ic_launcher_foreground);
//        return remoteViews;
//    }
//
//    // Method to display the notifications
//    public void showNotification(String title,
//                                 String message) {
//        // Pass the intent to switch to the MainActivity
//        Intent intent
//                = new Intent(this, MainActivity.class);
//        // Assign channel ID
//        String channel_id = "notification_channel";
//        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
//        // the activities present in the activity stack,
//        // on the top of the Activity that is to be launched
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        // Pass the intent to PendingIntent to start the
//        // next Activity
//        PendingIntent pendingIntent
//                = PendingIntent.getActivity(
//                this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        // Create a Builder object using NotificationCompat
//        // class. This will allow control over all the flags
//        NotificationCompat.Builder builder
//                = new NotificationCompat
//                .Builder(getApplicationContext(),
//                channel_id)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setAutoCancel(true)
//                .setVibrate(new long[]{1000, 1000, 1000,
//                        1000, 1000})
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent);
//
//        // A customized design for the notification can be
//        // set only for Android versions 4.1 and above. Thus
//        // condition for the same is checked here.
//        if (Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.JELLY_BEAN) {
//            builder = builder.setContent(
//                    getCustomDesign(title, message));
//        } // If Android Version is lower than Jelly Beans,
//        // customized layout cannot be used and thus the
//        // layout is set as follows
//        else {
//            builder = builder.setContentTitle(title)
//                    .setContentText(message)
//                    .setSmallIcon(R.drawable.ic_launcher_foreground);
//        }
//        // Create an object of NotificationManager class to
//        // notify the
//        // user of events that happen in the background.
//        NotificationManager notificationManager
//                = (NotificationManager) getSystemService(
//                Context.NOTIFICATION_SERVICE);
//        // Check if the Android Version is greater than Oreo
//        if (Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel
//                    = new NotificationChannel(
//                    channel_id, "web_app",
//                    NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(
//                    notificationChannel);
//        }
//
//        notificationManager.notify(0, builder.build());
//    }
}

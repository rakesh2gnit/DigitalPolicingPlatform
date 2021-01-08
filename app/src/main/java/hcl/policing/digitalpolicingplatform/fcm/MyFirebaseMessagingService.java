/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hcl.policing.digitalpolicingplatform.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.activities.notification.NotificationDetailActivity;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private AppSession appSession;
    int notiId = 0;
    public NotificationCompat.Builder notificationBuilder;
    private NotificationManager notifManager;
    //String channelId = "default_channel_id";
    String channelDescription = "Default Channel";
    String message = "";
    String taskId = "";
    // Intent intent;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        appSession = new AppSession(getApplicationContext());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            //Message data payload: {TaskID=9}

            if (isAppIsInBackground(getApplicationContext())) {

                //playNotificationSound(getApplicationContext());

                //if (remoteMessage.getData() != null)

                sendNotification(remoteMessage.getData());
            } else {
                //if (remoteMessage.getData() != null) {
                sendNotification(remoteMessage.getData());
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            message = remoteMessage.getNotification().getBody();
            //Message Notification Body: Task has been assigned to you kindly click to view details
            //sendNotification(remoteMessage.getNotification().getBody());
        }
    }
    // [END receive_message]

    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        new AppSession(this).setFCMToken(token);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     * <p>
     * //@param messageBody FCM message body received.
     */


    private void sendNotification(Map<String, String> data/*String messg*/) {
        try {
            appSession = new AppSession(getApplicationContext());

            //RemoteViews remoteViews = new RemoteViews("com.ptpl.milestone", R.layout.dialog_notification);
            Intent intent;
            if (data.get("TaskID") != null && !Objects.requireNonNull(data.get("TaskID")).equalsIgnoreCase("")) {
                //intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(data.get("productUrl")));
                Log.e("TASK ID", ">>>>>>> " + data.get("TaskID"));
                intent = new Intent(this, NotificationDetailActivity.class);
                intent.putExtra("TaskID", data.get("TaskID"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                Log.e("Other ID", ">>>>>>> ");
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            notiId = (int) System.currentTimeMillis();

            //PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
            //| PendingIntent.FLAG_ONE_SHOT);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), notiId, intent, 0);
            //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String chanelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.noti_audio);
            int impPriority = Notification.PRIORITY_HIGH; // PRIORITY_HIGH plays sound even if phone is silent

            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), chanelId);
            notificationBuilder.setContentTitle("Task");
            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
            notificationBuilder.setContentText("Task has been assigned. Click notification to view.");
            //notificationBuilder.setContentText(messg);
            notificationBuilder.setSound(customSoundUri);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            //notificationBuilder.setCustomBigContentView(remoteViews);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setPriority(impPriority);
            //remoteViews.setTextViewText(R.id.title, data.get("title"));
            //remoteViews.setTextViewText(R.id.message, messg);
            //remoteViews.setOnClickPendingIntent(R.id.view, getPendingSelfIntent(MyFirebaseMessagingService.this, MyOnClick1));
            //remoteViews.setOnClickPendingIntent(R.id.cancel, getPendingSelfIntent(MyFirebaseMessagingService.this, MyOnClick2));
            //remoteViews.setOnClickPendingIntent(R.id.ll_layout, getPendingSelfIntent(MyFirebaseMessagingService.this, MyOnClick3));

            if (notifManager == null) {
                notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH; // IMPORTANCE_HIGH plays sound even if phone is silent
                NotificationChannel mChannel = Objects.requireNonNull(notifManager).getNotificationChannel(chanelId);

                if (mChannel == null) {
                    AudioAttributes audioAttributes = null;
                    if (customSoundUri != null) {
                        // Changing Default mode of notification
                        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
                        // Creating an Audio Attribute
                        audioAttributes = new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .build();


                    }
                    mChannel = new NotificationChannel(chanelId, channelDescription, importance);
                    mChannel.enableVibration(true);
                    mChannel.setSound(customSoundUri, audioAttributes);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
            }
            notifManager.notify(notiId, notificationBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAppIsInBackground(Context context) {
        try {
            boolean isInBackground = true;
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = Objects.requireNonNull(am).getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
            return isInBackground;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        int color = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        notificationBuilder.setColor(color);
        return R.mipmap.ic_launcher;
    }

    public void playNotificationSound(Context context) {
        try {
            Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.noti_audio);
            Ringtone r = RingtoneManager.getRingtone(context, customSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
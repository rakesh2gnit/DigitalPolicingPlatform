package hcl.policing.digitalpolicingplatform.fcm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SwitchListener extends BroadcastReceiver {

    String productUrl, notificationType, productDetailId;
    int notification_id;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            productUrl = bundle.getString("productUrl");
            notificationType = bundle.getString("notificationType");
            productDetailId = bundle.getString("productDetailId");
            notification_id = bundle.getInt("notification_id");
            Log.v("productUrl", "" + productUrl);
            Log.v("notificationType", "" + notificationType);
            Log.v("productDetailId", "" + productDetailId);
            Log.v("notification_id", "" + notification_id);
        }
        if (intent.getAction().equalsIgnoreCase("myOnClickTag1")) {

            if (productUrl != null && !productUrl.equals("")) {
                try {
                    final Intent intent2 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(productUrl));
                    notificationManager.cancel(notification_id);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                /*Intent intent1 = new Intent();
                 *//* notificationManager.cancelAll();*//*
                intent1.setClassName("com.kavya.thetracker", "com.kavya.thetracker.MainActivity");
                intent1.putExtra("productDetailId",productDetailId);
                notificationManager.cancel(notification_id);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);*/
            }

            Log.v("Widget", "Clicked button1");
        } else if (intent.getAction().equalsIgnoreCase("myOnClickTag2")) {
            notificationManager.cancel(notification_id);
            Log.v("Widget", "Clicked button2");

        } else if (intent.getAction().equalsIgnoreCase("myOnClickTag3")) {

        } else {
            Toast.makeText(context, "Else", Toast.LENGTH_LONG).show();
            Log.v("Widget", "else");
        }
    }
}

package hcl.policing.digitalpolicingplatform.offline;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;


final public class WorkerUtils {

    private static final String TAG = WorkerUtils.class.getSimpleName();

    private WorkerUtils() {
    }

    /**
     * Get the Data List
     *
     * @param context
     * @return
     */
    @WorkerThread
    static ArrayList<BackupDataUtils> getDataList(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        ArrayList<BackupDataUtils> aBackupDataUtils = db.getPendingData(GenericConstant.SYNC_PENDIND);
        return aBackupDataUtils;
    }

    /**
     * Create a Notification that is shown as a heads-up notification if possible.
     * For this codelab, this is used to show a notification so that you know when different steps
     * of the background work chain are starting
     *
     * @param message Message shown on the notification
     * @param context Context needed to create Toast
     */
    static void makeStatusNotification(String message, Context context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = GenericConstant.VERBOSE_NOTIFICATION_CHANNEL_NAME;
            String description = GenericConstant.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(GenericConstant.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Add the channel
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, GenericConstant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(GenericConstant.NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        // Show the notification
        NotificationManagerCompat.from(context).notify(GenericConstant.NOTIFICATION_ID, builder.build());
    }
}

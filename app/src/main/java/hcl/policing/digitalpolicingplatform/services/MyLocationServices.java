package hcl.policing.digitalpolicingplatform.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.LinkedHashMap;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;

/**
 * Created by Prateek on 11/12/19.
 */

public class MyLocationServices extends Service implements ApiConstants {
    private static final String TAG = "MyLocationService";
    private FusedLocationProviderClient mFusedLocationClient;
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    String channelId = "default_channel_id";
    String channelDescription = "Default Channel";
    private NotificationManager notifManager;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.startForeground(ID, getNotification());
        }*/
        if (intent != null) {
            String action = intent.getAction();
            switch (Objects.requireNonNull(action)) {
                case ACTION_START_FOREGROUND_SERVICE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService();
                    }

                    Toast.makeText(getApplicationContext(), "Fetching Current Location", Toast.LENGTH_LONG).show();
                    break;

                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    //Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                    break;
                /*case ACTION_PLAY:
                    Toast.makeText(getApplicationContext(), "You click Play button.", Toast.LENGTH_LONG).show();
                    break;
                case ACTION_PAUSE:
                    Toast.makeText(getApplicationContext(), "You click Pause button.", Toast.LENGTH_LONG).show();
                    break;*/
            }
        }
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.startForeground(ID, getNotification());
        }*/
        if (isGooglePlayServicesAvailable()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            //if (locationResult.getLastLocation() == null)
            //return;
            final Location currentLocation = locationResult.getLastLocation();
            //latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            Log.e("BACKGROUND Location ", "Latitude: " + currentLocation.getLatitude() + "  Longitude: " + currentLocation.getLongitude());

            try {
                new AppSession(MyLocationServices.this).setLatitude(currentLocation.getLatitude() + "");
                new AppSession(MyLocationServices.this).setLongitude(currentLocation.getLongitude() + "");

                new AppSession(MyLocationServices.this).setCurrentLatitude(currentLocation.getLatitude() + "");
                new AppSession(MyLocationServices.this).setCurrentLongitude(currentLocation.getLongitude() + "");
                //LinkedHashMap<String, String> message = getNewLocationMessage(currentLocation.getLatitude(), currentLocation.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TO STOP LOCATION UPDATES
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    };

    private void startForegroundService() {

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);

        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Location");
        bigTextStyle.bigText("Getting current location");
        // Set big text style.
        notificationBuilder.setStyle(bigTextStyle);

        notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location));
        //notificationBuilder.setContentText("");
        notificationBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        notificationBuilder.setAutoCancel(true);

        // Make head-up notification.
        notificationBuilder.setFullScreenIntent(pendingIntent, true);

        // Add Stop button intent in notification.
        Intent stopIntent = new Intent(this, MyLocationServices.class);
        stopIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, stopIntent, 0);
        NotificationCompat.Action stopAction = new NotificationCompat.Action(R.drawable.ic_location, "Stop", pendingPlayIntent);
        notificationBuilder.addAction(stopAction);

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = Objects.requireNonNull(notifManager).getNotificationChannel(channelId);

            if (mChannel == null) {
                mChannel = new NotificationChannel(channelId, channelDescription, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
        }

        // Build the notification.
        Notification notification = notificationBuilder.build();

        //notifManager.notify(0 , notification);

        // Start foreground service.
        startForeground(1237, notification);

        //return notification;
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        notificationBuilder.setColor(color);
        return R.drawable.ic_location;
    }

    private void stopForegroundService() {
        Log.d("STOP LOCATION", "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mFusedLocationClient = null;
    }

    private LinkedHashMap<String, String> getNewLocationMessage(double lat, double lng) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lng));
        return map;
    }
}


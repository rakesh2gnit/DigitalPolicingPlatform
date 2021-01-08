package hcl.policing.digitalpolicingplatform.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LocationUtil {

    private static final LocationUtil ourInstance = new LocationUtil();

    public static LocationUtil getInstance() {
        return ourInstance;
    }

    private LocationUtil() {
    }

    final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     *
     * @param requestingLocationUpdates The location updates state.
     */
    public void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     *
     * @param location The {@link Location}.
     */
    public String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    public String getLocationTitle(Context context) {
        return context.getString(R.string.location_updated,
                DateFormat.getDateTimeInstance().format(new Date()));
    }


    /**
     * get the Address from lat , long
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public List<Address> getAddressFromLocation(final Context context, final double latitude,
                                                final double longitude) {

        //String result ="";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Log.e("ADDRESS ", ">>>>> " + addressList.toString());
                return addressList;
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }

        return null;
    }

    /**
     * Get the latLng from address
     *
     * @param context
     * @param strAddress
     * @return
     */
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> addressList;
        LatLng latLng = null;

        try {
            addressList = coder.getFromLocationName(strAddress, 5);
            if (addressList == null) {
                return null;
            }
            Address location = addressList.get(0);
            location.getLatitude();
            location.getLongitude();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }


    /**
     * Call the navigation method
     *
     * @param context
     * @param toLat
     * @param toLng
     */
    public void navigateTo(Context context, double toLat, double toLng) {
        double frmLat, frmLng;
        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            frmLat = mGPS.getLatitude();
            frmLng = mGPS.getLongitude();

            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                    .parse(ApiConstants.NAV_URL
                            + frmLat + ","
                            + frmLng + "&daddr="
                            + toLat + "," + toLng));
            context.startActivity(navigation);
        }
    }

    /**
     * Call the navigation method
     *
     * @param context
     * @param address
     */
    public void navigateTo(Context context, final String address) {
        final double[] frmLat = {0};
        final double[] frmLng = {0};
        final double[] toLat = {0};
        final double[] toLng = {0};
        String destAddress = address;
        Dialog mProgressDialog = DialogUtil.showProgressDialog(context);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GPSTracker mGPS = new GPSTracker(context);
                    LatLng latLongdddress = getLocationFromAddress(context, destAddress);
                    if (mGPS.canGetLocation) {
                        mGPS.getLocation();
                        frmLat[0] = mGPS.getLatitude();
                        frmLng[0] = mGPS.getLongitude();
                    }
                    if (latLongdddress != null) {
                        toLat[0] = latLongdddress.latitude;
                        toLng[0] = latLongdddress.longitude;
                    }
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelProgressDialog(mProgressDialog);
                            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(ApiConstants.NAV_URL
                                            + frmLat[0] + ","
                                            + frmLng[0] + "&daddr="
                                            + toLat[0] + "," + toLng[0]));
                            context.startActivity(navigation);
                        }
                    });

                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), LocationUtil.class, "navigateTo");
                }
            }
        }).start();
    }

}

package hcl.policing.digitalpolicingplatform.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;

public class NetworkUtil {

    private static final NetworkUtil ourInstance = new NetworkUtil();

    public static NetworkUtil getInstance() {
        return ourInstance;
    }

    private NetworkUtil() {
    }


    /**
     * Check the network availability
     *
     * @param context
     * @return
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            Toast.makeText(context, context.getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
        return false;
    }


    /**
     * Get Connectivity Status
     *
     * @param context
     * @return
     */
    public int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return GenericConstant.TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return GenericConstant.TYPE_MOBILE;
        }
        return GenericConstant.TYPE_NOT_CONNECTED;
    }

    /**
     * Get Network status
     *
     * @param context
     * @return
     */
    public String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == GenericConstant.TYPE_WIFI) {
            status = context.getString(R.string.wifi_enable);
        } else if (conn == GenericConstant.TYPE_MOBILE) {
            status = context.getString(R.string.mobile_data_enable);
        } else if (conn == GenericConstant.TYPE_NOT_CONNECTED) {
            status = context.getString(R.string.no_network);
        }
        return status;
    }
}

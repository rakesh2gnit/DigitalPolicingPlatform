package hcl.policing.digitalpolicingplatform;

import android.app.Application;

import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.receivers.NetworkStateReceiver;


public class DppApplication extends Application {

    private static DppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public DppApplication() {
        mInstance = this;

    }
    public static synchronized DppApplication getInstance() {
        if (mInstance == null) {
            mInstance = new DppApplication();
        }
        return mInstance;
    }

    public void setConnectivityListener(NetworkStateReceiver.ConnectivityReceiverListener listener) {
        NetworkStateReceiver.connectivityReceiverListener = listener;
    }

    public SharedPrefUtils getSharedPrefUtils() {
        return SharedPrefUtils.getInstance(this);
    }

}
